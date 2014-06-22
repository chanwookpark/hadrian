package restclient.startup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import restclient.ApiConfigInitializingException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * 참조: http://docs.spring.io/spring/docs/3.2.0.RC1/api/org/springframework/test/web/client/MockRestServiceServer.html
 * <p/>
 * Created by chanwook on 2014. 6. 18..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SampleAppConfig.class})
public class SampleApiSpecTests {

    @Autowired
    SampleApiSpec spec;

    @Autowired
    RestTemplate springTemplate;

    @Test
    public void loadWebServiceBean() throws Exception {
        try {
            Sample1 v = spec.get();
            fail();
        } catch (UnsupportedOperationException e) {
        }
    }

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
    public void getWithUrlParameter() throws Exception {

    }
}
