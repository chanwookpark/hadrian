package restclient.operation;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by chanwook on 2014. 6. 22..
 */
public class WebServiceTemplateTests {
    @Test(expected = IllegalArgumentException.class)
    public void testUrlPathVar() throws Exception {
        WebServiceTemplate t = new WebServiceTemplate();
        t.createPathParam("/path/{not_digit}", new Object[]{});
    }

    @Test
    public void testPathVar() throws Exception {
        WebServiceTemplate t = new WebServiceTemplate();
        assertEquals("v0", t.createPathParam("/path/{1}", new Object[]{"v0"}).get("1"));

        Map<String, Object> v = t.createPathParam("/path/{1}/v/{2}/c/{3}", new Object[]{"v1", "v2", "v3"});
        assertEquals("v1", v.get("1"));
        assertEquals("v2", v.get("2"));
        assertEquals("v3", v.get("3"));
    }

    @Test
    public void testFailedIndex() throws Exception {
        WebServiceTemplate t = new WebServiceTemplate();
        try {
            t.createPathParam("/path/{0}", new Object[]{});
            fail("1");
        } catch (Exception e) {
        }
        try {
            t.createPathParam("/path/{0}/{1}", new Object[]{"0"});
            fail("2");
        } catch (Exception e) {
        }
        try {
            t.createPathParam("/path/{0}/{1}/{2}/{3}", new Object[]{"0", "1"});
            fail("3");
        } catch (Exception e) {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotUsingZeroValue() throws Exception {
        WebServiceTemplate t = new WebServiceTemplate();
        t.createPathParam("/pat/{0}", new Object[]{});
    }
}
