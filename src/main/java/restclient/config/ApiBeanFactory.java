package restclient.config;

import restclient.operation.ApiBean;
import restclient.model.ApiHostMap;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public interface ApiBeanFactory {

    ApiBean createBean(Class<?> spec, ApiHostMap apiHostMap);
}
