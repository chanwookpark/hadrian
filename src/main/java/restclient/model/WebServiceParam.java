package restclient.model;

import restclient.meta.HttpMethod;

import java.io.Serializable;

/**
 * Created by chanwook on 2014. 6. 21..
 */
public class WebServiceParam implements Serializable {

    private String url;
    private HttpMethod method;
    private Object[] arguments;
    private Class<?> returnType;
    private String hostUrl;

    public void url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void method(HttpMethod method) {
        this.method = method;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void arguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void returnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void hostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getHostUrl() {
        return hostUrl;
    }
}
