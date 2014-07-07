package restclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import restclient.ApiConfigInitializingException;
import restclient.meta.API;
import restclient.operation.ApiBean;
import restclient.model.ApiHostMap;

import java.util.Set;

/**
 * Created by chanwook on 2014. 6. 30..
 */
public class SpecificationClassSupportApiConfigurationRegister implements ApiConfigurationRegister {

    private final Logger logger = LoggerFactory.getLogger(SpecificationClassSupportApiConfigurationRegister.class);

    private ApiBeanFactory apiBeanFactory;

    private String basePackage;

    @Override
    public void registerApiConfiguration(ConfigurableListableBeanFactory beanFactory, ApiHostMap hostMap) {
        Set<BeanDefinition> candidateComponents = scanningCandidateBeanDefinition();

        registerApiBeanDefinition(beanFactory, candidateComponents, hostMap);
    }


    private Set<BeanDefinition> scanningCandidateBeanDefinition() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false) {
                    @Override
                    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                        // 인터페이스 타입도 Candidate component로 취급
                        return true;
                    }
                };
        scanner.addIncludeFilter(new AnnotationTypeFilter(API.class, true, true));

        return scanner.findCandidateComponents(basePackage);
    }

    private void registerApiBeanDefinition(ConfigurableListableBeanFactory beanFactory, Set<BeanDefinition> candidateComponents, ApiHostMap apiHostMap) {

        if (candidateComponents == null || candidateComponents.size() == 0) {
            if (logger.isInfoEnabled()) {
                logger.info("검색된 웹서비스 클라이언트 서비스(Spec) 없습니다. 웹서비스 클라이언트 초기화를 진행하지 않습니다.");
            }
            return;
        }

        for (BeanDefinition bd : candidateComponents) {
            if (!isExistBean(beanFactory, bd)) {
                Class<?> spec = loadApiSpecClass(beanFactory, bd);
                String beanName = bd.getBeanClassName();
                ApiBean bean = apiBeanFactory.createBean(spec, apiHostMap);

                if (logger.isDebugEnabled()) {
                    logger.debug("[API Bean] bean name: " + beanName + ", class: " + bean);
                }
                beanFactory.registerSingleton(beanName, bean);
            }
        }
    }

    private Class<?> loadApiSpecClass(ConfigurableListableBeanFactory beanFactory, BeanDefinition bd) {
        try {
            Class<?> interfaceClass = ClassUtils.forName(bd.getBeanClassName(), null);
//            Class<?> interfaceClass = Class.forName(bd.getBeanClassName(), true, beanFactory.getBeanClassLoader());
            return interfaceClass;
        } catch (ClassNotFoundException e) {
            throw new ApiConfigInitializingException("API 인터페이스의 클래스 로딩 중 에러가 발생했습니다.", e);
        }
    }

    private boolean isExistBean(ConfigurableListableBeanFactory beanFactory, BeanDefinition bd) {
        return beanFactory.containsBean(bd.getBeanClassName());
    }

    public void setApiBeanFactory(ApiBeanFactory apiBeanFactory) {
        this.apiBeanFactory = apiBeanFactory;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

}
