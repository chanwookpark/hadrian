package restclient.operation;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by chanwook on 2014. 6. 27..
 */
public interface UrlParameterMapper {
    void mapping(Object v, UriComponentsBuilder uriBuilder);
}
