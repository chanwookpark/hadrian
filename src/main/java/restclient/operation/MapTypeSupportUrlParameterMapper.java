package restclient.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 27..
 */
public class MapTypeSupportUrlParameterMapper implements UrlParameterMapper {
    private final Logger logger = LoggerFactory.getLogger(MapTypeSupportUrlParameterMapper.class);

    @Override
    public void mapping(Object param, UriComponentsBuilder uriBuilder) {
        for (Object o : ((Map) param).entrySet()) {
            Map.Entry e = (Map.Entry) o;

            Object k = e.getKey();
            Object v = e.getValue();
            if (logger.isDebugEnabled()) {
                logger.debug(">> Map 타입 파라미터 등록: " + k + ", " + v);
            }
            uriBuilder.queryParam(String.valueOf(k), v);
        }
    }
}
