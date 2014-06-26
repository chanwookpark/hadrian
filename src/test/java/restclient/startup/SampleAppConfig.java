package restclient.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import restclient.config.ApiConfigurationBeanFactoryPostProcessor;
import restclient.config.ProxySupportApiBeanFactory;
import restclient.config.XmlConfigurationApiHostMapFactory;

/**
 * Created by chanwook on 2014. 6. 19..
 */
@Configuration
public class SampleAppConfig {

    @Bean
    public ApiConfigurationBeanFactoryPostProcessor getInitBeanFactoryProstProcessor() {
        ApiConfigurationBeanFactoryPostProcessor bfpp = new ApiConfigurationBeanFactoryPostProcessor();
        bfpp.setApiBeanFactory(createWebServiceBeanFactory());
        bfpp.setBasePackage("restclient");
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
}
