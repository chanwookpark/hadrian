package restclient.operation;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by chanwook on 2014. 6. 22..
 */
public class WebServiceTemplateTests {
    WebServiceTemplate t = new WebServiceTemplate();

    @Test
    public void testPathVar() throws Exception {
        assertEquals("v0", t.createPathParam("/path/{1}", new Object[]{"v0"}, getDefaultNamedPathMap()).get("1"));

        Map<String, Object> v = t.createPathParam("/path/{1}/v/{2}/c/{3}", new Object[]{"v1", "v2", "v3"}, getDefaultNamedPathMap());
        assertEquals("v1", v.get("1"));
        assertEquals("v2", v.get("2"));
        assertEquals("v3", v.get("3"));
    }

    @Test
    public void testFailedIndex() throws Exception {
        try {
            t.createPathParam("/path/{0}", new Object[]{}, getDefaultNamedPathMap());
            fail("1");
        } catch (Exception e) {
        }
        try {
            t.createPathParam("/path/{0}/{1}", new Object[]{"0"}, getDefaultNamedPathMap());
            fail("2");
        } catch (Exception e) {
        }
        try {
            t.createPathParam("/path/{0}/{1}/{2}/{3}", new Object[]{"0", "1"}, getDefaultNamedPathMap());
            fail("3");
        } catch (Exception e) {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotUsingZeroValue() throws Exception {
        t.createPathParam("/pat/{0}", new Object[]{}, getDefaultNamedPathMap());
    }

    @Test
    public void testNamedPath() throws Exception {
        HashMap<String, String> namedPathMap = new HashMap<String, String>();
        namedPathMap.put("name1", "1");
        assertEquals("value1", t.createPathParam("/path/{name1}/v", new Object[]{"value1"}, namedPathMap).get("name1"));

        namedPathMap = new HashMap<String, String>();
        namedPathMap.put("name1", "1");
        namedPathMap.put("key1", "2");

        Map<String, Object> result = t.createPathParam("/path/{name1}/v/{key1}/c", new Object[]{"value1", "value2"}, namedPathMap);
        assertEquals("value1", result.get("name1"));
        assertEquals("value2", result.get("key1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutOfIndexWhenNamedPath() throws Exception {
        HashMap<String, String> namedPathMap = new HashMap<String, String>();
        namedPathMap.put("name1", "1");
        t.createPathParam("/path/{name1}/v", new Object[]{}, namedPathMap).get("name1");
    }

    private Map<String, String> getDefaultNamedPathMap() {
        return new HashMap<String, String>();
    }

}
