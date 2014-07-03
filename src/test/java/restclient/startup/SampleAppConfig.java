package restclient.startup;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import restclient.config.ApiConfigurationBeanFactoryPostProcessor;
import restclient.config.ProxySupportApiBeanFactory;
import restclient.config.SpecificationClassSupportApiConfigurationRegister;
import restclient.config.XmlConfigurationApiHostMapFactory;

/**
 * Created by chanwook on 2014. 6. 19..
 */
@Configuration
@EnableCaching
public class SampleAppConfig {

    @Bean
    public ApiConfigurationBeanFactoryPostProcessor getInitBeanFactoryProstProcessor() {
        SpecificationClassSupportApiConfigurationRegister scacr = new SpecificationClassSupportApiConfigurationRegister();
        scacr.setApiBeanFactory(createWebServiceBeanFactory());
        scacr.setBasePackage("restclient");

        ApiConfigurationBeanFactoryPostProcessor bfpp = new ApiConfigurationBeanFactoryPostProcessor();
        bfpp.setApiConfigurationRegister(scacr);
        bfpp.setHostMapFactory(createHostMapFactory());
        return bfpp;
    }

    private ProxySupportApiBeanFactory createWebServiceBeanFactory() {
        ProxySupportApiBeanFactory factory = new ProxySupportApiBeanFactory();
        factory.setSpringTemplate(getRestTemlpate());
        return factory;
    }

    private XmlConfigurationApiHostMapFactory createHostMapFactory() {
        XmlConfigurationApiHostMapFactory factory = new XmlConfigurationApiHostMapFactory();
        return factory;
    }

    @Bean
    public RestTemplate getRestTemlpate() {
        RestTemplate t = new RestTemplate();
        return t;
    }

    @Bean
    public CacheManager getCacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
