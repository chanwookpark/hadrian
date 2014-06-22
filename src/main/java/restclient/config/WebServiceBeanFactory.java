package restclient.config;

import restclient.model.ApiHostMap;
import restclient.model.WebServiceBean;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public interface WebServiceBeanFactory {

    WebServiceBean createBean(Class<?> spec, ApiHostMap apiHostMap);
}
