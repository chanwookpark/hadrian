package restclient.config;

import org.junit.Test;

/**
 * Created by chanwook on 2014. 6. 22..
 */
public class CodeAccurateCheckTests {
    @Test
    public void testUrlSubstring() throws Exception {
        String url = "sample/{0}/abc/{1}/{2}";
        String[] vars = url.split("/");
        for (String v : vars) {
            if (v.startsWith("{") && v.endsWith("}")) {
                System.out.println(v.replace("{", "").replace("}", ""));

            }
        }

    }
}
