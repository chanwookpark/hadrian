package restclient.operation;

import restclient.model.ApiParam;

/**
 * Created by chanwook on 2014. 6. 27..
 */
public interface ApiUrlBuilder {
    String build(ApiParam param);
}
