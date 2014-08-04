package hadrian.security;

import hadrian.meta.API;
import hadrian.meta.AccessToken;
import hadrian.meta.http.Body;
import hadrian.meta.http.POST;

import java.util.Map;

/**
 * Created by chanwook on 2014. 7. 28..
 */
@API(key = "security-test")
public interface SecurityTestSpec {

    @POST(url = "/resource/{1}")
    Map createResource(String key, @Body Map<String, String> body, @AccessToken String token);
}
