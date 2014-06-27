package restclient.config;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;
import restclient.model.ApiHost;
import restclient.model.ApiHostMap;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by chanwook on 2014. 6. 21..
 */
public class XmlConifgurationApiHostMapFactoryTests {

    @Test
    public void testLoadXmlConfig() throws Exception {
        XmlConfigurationApiHostMapFactory f = new XmlConfigurationApiHostMapFactory();
        ApiHostMap map = f.loadHostMap();

        assertNotNull(map);
        assertTrue(2 == map.getApiHostList().size());
        // apiHost 1
        assertEquals("sample", map.getApiHostList().get(0).getKey());
        assertEquals("localhost", map.getApiHostList().get(0).getHost());
        assertEquals(9090, map.getApiHostList().get(0).getPort());
        assertEquals("sample", map.getApiHostList().get(0).getContextRoot());
        //apiHost 2
        assertEquals("test", map.getApiHostList().get(1).getKey());
        assertEquals("localhost", map.getApiHostList().get(1).getHost());
        assertEquals(9090, map.getApiHostList().get(1).getPort());
        assertEquals("test", map.getApiHostList().get(1).getContextRoot());

    }

    @Test
    public void testCreateXml() throws Exception {
        XStream stream = new XStream();
//        stream.alias("apiHostMap", ApiHostMap.class);
//        stream.alias("apiHost", ApiHost.class);
//        stream.addImplicitCollection(ApiHost.class, "apiHostList");
        stream.processAnnotations(ApiHostMap.class);
        stream.processAnnotations(ApiHost.class);

        ApiHostMap m = new ApiHostMap();
        m.setApiHostList(Arrays.asList(new ApiHost("key", "localhost", 8080, "sample"), new ApiHost("key", "localhost", 8080, "sample")));

        String xml = stream.toXML(m);
        System.out.println(xml);
    }
}
