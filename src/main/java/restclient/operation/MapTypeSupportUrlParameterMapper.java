package restclient.operation;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 27..
 */
public class MapTypeSupportUrlParameterMapper implements UrlParameterMapper {
    @Override
    public void mapping(Object v, UriComponentsBuilder uriBuilder) {
        for (Object o : ((Map) v).entrySet()) {
            Map.Entry e = (Map.Entry) o;
            uriBuilder.queryParam(String.valueOf(e.getKey()), e.getValue());
        }
    }
}
