package hadrian.security;

import hadrian.TestAppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

/**
 * Created by chanwook on 2014. 7. 28..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
public class SecurityTest {

    @Autowired
    SecurityTestSpec spec;

    @Autowired
    RestTemplate springTemplate;

    @Test
    public void createResource() throws Exception {
        String token = "_token" + System.currentTimeMillis();
        HashMap<String, String> body = new HashMap<String, String>();
        body.put("key1", "value1");
        body.put("key2", "value2");

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(springTemplate);
        mockServer.expect(requestTo("https://localhost/security/resource/key"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.key1").value("value1"))
                .andExpect(jsonPath("$.key2").value("value2"))
//                .andExpect(header("Authorization", token, "OAUTH-TOKEN"))
                .andRespond(withStatus(HttpStatus.CREATED));

        Map response = spec.createResource("key", body, token);

        mockServer.verify();
    }
}
