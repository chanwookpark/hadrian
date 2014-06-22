package restclient.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import restclient.config.XmlConfigurationApiHostMapFactory;
import restclient.operation.ProxySupportWebServiceBeanFactory;
import restclient.operation.WebServiceEnrollBeanFactoryPostProcessor;

/**
 * Created by chanwook on 2014. 6. 19..
 */
@Configuration
public class SampleAppConfig {

    @Bean
    public WebServiceEnrollBeanFactoryPostProcessor getInitBeanFactoryProstProcessor() {
        WebServiceEnrollBeanFactoryPostProcessor bfpp = new WebServiceEnrollBeanFactoryPostProcessor();
        bfpp.setWebServiceBeanFactory(createWebServiceBeanFactory());
        bfpp.setBasePackage("restclient");
        bfpp.setHostMapFactory(createHostMapFactory());
        return bfpp;
    }

    private ProxySupportWebServiceBeanFactory createWebServiceBeanFactory() {
        ProxySupportWebServiceBeanFactory factory = new ProxySupportWebServiceBeanFactory();
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
