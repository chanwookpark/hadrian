package hadrian.config;

import org.junit.Test;
import hadrian.startup.SampleApiSpec;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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

    @Test
    public void testReflection() throws Exception {
        for (Method m : SampleApiSpec.class.getDeclaredMethods()) {
            System.out.println(m.getName());

            for (Annotation[] annotations : m.getParameterAnnotations()) {
                System.out.println(annotations.length);
                for (Annotation a : annotations) {
                    System.out.println(a.annotationType());
                }
            }

        }


    }
}
