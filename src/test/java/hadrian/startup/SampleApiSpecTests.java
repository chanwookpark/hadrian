package hadrian.startup;

import hadrian.TestAppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import hadrian.ApiConfigInitializingException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * 참조: http://docs.spring.io/spring/docs/3.2.0.RC1/api/org/springframework/test/web/client/MockRestServiceServer.html
 * <p/>
 * Created by chanwook on 2014. 6. 18..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
//@ActiveProfiles(profiles = {"local"})
public class SampleApiSpecTests {

    @Autowired
    SampleApiSpec spec;

    @Autowired
    RestTemplate springTemplate;

    @Test
    public void testNotAssignedMethod() throws Exception {
        try {
            spec.notAssigned();
            fail();
        } catch (ApiConfigInitializingException e) {
        }
    }

    @Test
    public void basicGet() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":\"0\", \"text1\":\"value\"}", MediaType.APPLICATION_JSON));

        Sample1 r = spec.get();

        assertNotNull(r);
        assertEquals(0, r.getId());
        assertEquals("value", r.getText1());

        mockServer.verify();

    }

    @Test
    public void testPathVar1() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":\"1\", \"text1\":\"value1\"}", MediaType.APPLICATION_JSON));

        Sample1 r = spec.get(1);

        assertNotNull(r);
        assertEquals(1, r.getId());
        assertEquals("value1", r.getText1());

        mockServer.verify();
    }

    @Test
    public void testPathVar2() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        //sample/{2}/u/{3}/c/{1}
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/2/u/d/c/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":\"123\", \"text1\":\"value2\"}", MediaType.APPLICATION_JSON));

        Sample1 r = spec.get(1, "2", "d");

        assertNotNull(r);
        assertEquals(123, r.getId());
        assertEquals("value2", r.getText1());

        mockServer.verify();
    }

    @Test
    public void withMapParam() throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("key1", "value1");
        paramMap.put("key2", "value2");
        paramMap.put("key3", "value3");

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        //sample/{2}/u/{3}/c/{1}
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/path1?key3=value3&key2=value2&key1=value1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":\"123\", \"text1\":\"value2\"}", MediaType.APPLICATION_JSON));

        Sample1 r = spec.getWithMapParam("path1", paramMap);

        assertNotNull(r);
        assertEquals(123, r.getId());
        assertEquals("value2", r.getText1());

        mockServer.verify();
    }

    @Test
    public void testPathVarWithKey() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":\"1\", \"text1\":\"value1\"}", MediaType.APPLICATION_JSON));

        Sample1 r = spec.getWithPath("1");

        assertNotNull(r);
        assertEquals(1, r.getId());
        assertEquals("value1", r.getText1());

        mockServer.verify();
    }

    @Test
    public void getWithUrlParameter() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/path123?param1=param123"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":\"2\", \"text1\":\"value2\"}", MediaType.APPLICATION_JSON));

        Sample1 r = spec.getWithPath2("path123", "param123");

        assertNotNull(r);
        assertEquals(2, r.getId());
        assertEquals("value2", r.getText1());

        mockServer.verify();

    }

    @Test
    public void postWithEntity() throws Exception {
        Sample1 s = new Sample1(1L, "value1");

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.id").value((int) s.getId()))
                .andExpect(jsonPath("$.text1").value(s.getText1()))
                .andRespond(withStatus(HttpStatus.CREATED));

        spec.save(s);

        mockServer.verify();
    }

    @Test
    public void putWithEntity() throws Exception {
        Sample1 s = new Sample1(1L, "value1");

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(jsonPath("$.id").value((int) s.getId()))
                .andExpect(jsonPath("$.text1").value(s.getText1()))
                .andRespond(withStatus(HttpStatus.OK));

        spec.update(s);

        mockServer.verify();
    }

    @Test
    public void deleteByKey() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));

        spec.delete(1);

        mockServer.verify();
    }

    @Test
    public void testGetWithCache() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("http://localhost:9090/sample/sample/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":\"1\", \"text1\":\"value1\"}", MediaType.APPLICATION_JSON));

        Sample1 r = spec.getWithCache("1");
        assertNotNull(r);
        assertEquals(1, r.getId());
        assertEquals("value1", r.getText1());

        mockServer.verify();

        Sample1 cr = spec.getWithCache("1");
        assertNotNull(cr);
        assertEquals(1, cr.getId());
        assertEquals("value1", cr.getText1());

        // 안 가면 통과로 봐도 되나? 보강은 어떻게 해야 하나..
    }
}
