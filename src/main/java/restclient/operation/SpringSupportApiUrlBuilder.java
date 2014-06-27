package restclient.operation;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import restclient.ApiConfigInitializingException;
import restclient.model.ApiHost;
import restclient.model.ApiParam;

import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 27..
 */
public class SpringSupportApiUrlBuilder implements ApiUrlBuilder {
    @Override
    public String build(ApiParam param) {
        if (!StringUtils.hasText(param.getUrl()) || param.getApiHost() == null) {
            throw new ApiConfigInitializingException("API URL 정보가 잘 못됐습니다!!");
        }

        ApiHost apiHost = param.getApiHost();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder
                .scheme("http")
                .host(apiHost.getHost())
                .port(apiHost.getPort())
                .path(apiHost.getContextRoot())
                .path(param.getUrl());

        // parameter
        Map<String, Integer> urlParameters = param.getUrlParameters();
        for (Map.Entry<String, Integer> e : urlParameters.entrySet()) {
            String k = e.getKey();
            Object v = param.getArguments()[e.getValue()];
            if (v instanceof Map) {
                putMapTypeParam((Map) v, uriBuilder);
            } else {
                uriBuilder.queryParam(k, v);
            }
        }
        return uriBuilder.build().toUriString();
    }

    private void putMapTypeParam(Map map, UriComponentsBuilder uriBuilder) {
        for (Object o : map.entrySet()) {
            Map.Entry e = (Map.Entry) o;
            uriBuilder.queryParam(String.valueOf(e.getKey()), e.getValue());
        }

    }
}
