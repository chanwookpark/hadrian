package restclient.model;

import restclient.meta.HttpMethod;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 21..
 */
public class ApiParam implements Serializable {

    private String javaMethodName;

    private String url;
    private HttpMethod method;
    private Object[] arguments;
    private Class<?> returnType;
    private String hostUrl;
    private Map<String, String> namedPathMap;
    private Object entity;

    public ApiParam(String name) {
        this.javaMethodName = name;
    }

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

    public String getJavaMethodName() {
        return javaMethodName;
    }

    public void setJavaMethodName(String javaMethodName) {
        this.javaMethodName = javaMethodName;
    }

    public void namedPathMap(Map<String, String> namedPathMap) {
        this.namedPathMap = namedPathMap;
    }

    public Map<String, String> getNamedPathMap() {
        return namedPathMap;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
