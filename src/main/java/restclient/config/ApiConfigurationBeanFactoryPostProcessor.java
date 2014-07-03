package restclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import restclient.model.ApiHostMap;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class ApiConfigurationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(ApiConfigurationBeanFactoryPostProcessor.class);


    private ApiHostMapFactory hostMapFactory;

    private ApiConfigurationRegister apiConfigurationRegister = new SpecificationClassSupportApiConfigurationRegister();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        ApiHostMap hostMap = hostMapFactory.loadHostMap();

        apiConfigurationRegister.registerApiConfiguration(beanFactory, hostMap);
    }

    public void setHostMapFactory(ApiHostMapFactory hostMapFactory) {
        this.hostMapFactory = hostMapFactory;
    }

    public void setApiConfigurationRegister(ApiConfigurationRegister apiConfigurationRegister) {
        this.apiConfigurationRegister = apiConfigurationRegister;
    }
}
