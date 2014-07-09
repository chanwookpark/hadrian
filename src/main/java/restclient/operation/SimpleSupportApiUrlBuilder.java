package restclient.operation;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import restclient.ApiConfigInitializingException;
import restclient.model.ApiHost;
import restclient.model.ApiParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 27..
 */
public class SimpleSupportApiUrlBuilder implements ApiUrlBuilder {
    private Map<Class<?>, UrlParameterMapper> paramTypeMapperMap = new HashMap<Class<?>, UrlParameterMapper>();

    public SimpleSupportApiUrlBuilder() {
        paramTypeMapperMap.put(Map.class, new MapTypeSupportUrlParameterMapper());
    }

    @Override
    public String build(ApiParam param) {
        if (!StringUtils.hasText(param.getUrl()) || param.getApiHost() == null) {
            throw new ApiConfigInitializingException("API URL 정보가 잘 못됐습니다!!");
        }

        // URL mapping
        ApiHost apiHost = param.getApiHost();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder
                .scheme("http")
                .host(apiHost.getHost())
                .port(apiHost.getPort())
                .path(apiHost.getContextRoot())
                .path(param.getUrl());

        // parameter mapping
        Map<String, Integer> urlParameters = param.getUrlParameters();
        for (Map.Entry<String, Integer> e : urlParameters.entrySet()) {
            String k = e.getKey();
            Object v = param.getArguments()[e.getValue()];

            addQueryParam(uriBuilder, k, v);
        }
        return uriBuilder.build().toUriString();
    }

    private void addQueryParam(UriComponentsBuilder uriBuilder, String k, Object v) {
        for (Map.Entry<Class<?>, UrlParameterMapper> me : paramTypeMapperMap.entrySet()) {
            if (me.getKey().isAssignableFrom(v.getClass())) {
                me.getValue().mapping(v, uriBuilder);
                return;
            }
        }
        // default
        uriBuilder.queryParam(k, v);
    }

    public void setParamTypeMapper(Map<Class<?>, UrlParameterMapper> paramTypeMapper) {
        this.paramTypeMapperMap = paramTypeMapper;
    }

    public Map<Class<?>, UrlParameterMapper> getParamTypeMapper() {
        return paramTypeMapperMap;
    }
}
