package hadrian.model;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * Created by chanwook on 2014. 6. 22..
 */
public class ApiHost implements Serializable {

    @XStreamAsAttribute
    private String key;

    @XStreamAsAttribute
    private String host;

    @XStreamAsAttribute
    private int port;

    @XStreamAsAttribute
    private String contextRoot;

    public ApiHost() {
    }

    public ApiHost(String key, String host, int port, String contextRoot) {
        this.key = key;
        this.host = host;
        this.port = port;
        this.contextRoot = contextRoot;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }

    public String getHostUrl() {
        return "http://" + getHost() + ":" + getPort() + "/" + getContextRoot() + "/";
    }
}
