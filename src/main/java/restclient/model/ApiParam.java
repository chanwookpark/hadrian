package restclient.model;

import restclient.meta.http.HttpMethod;

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
    private Map<String, Integer> namedPathMap;
    private Object entity;
    private Map<String, Integer> urlParameters;
    private ApiHost apiHost;

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

    public void host(ApiHost apiHost) {
        this.apiHost = apiHost;
    }

    public ApiHost getApiHost() {
        return apiHost;
    }

    public String getJavaMethodName() {
        return javaMethodName;
    }

    public void setJavaMethodName(String javaMethodName) {
        this.javaMethodName = javaMethodName;
    }

    public void namedPathMap(Map<String, Integer> namedPathMap) {
        this.namedPathMap = namedPathMap;
    }

    public Map<String, Integer> getNamedPathMap() {
        return namedPathMap;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public void urlParameters(Map<String, Integer> parameters) {
        this.urlParameters = parameters;
    }

    public Map<String, Integer> getUrlParameters() {
        return urlParameters;
    }
}
