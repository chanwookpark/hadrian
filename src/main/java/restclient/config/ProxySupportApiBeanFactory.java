package restclient.config;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.web.client.RestTemplate;
import restclient.meta.API;
import restclient.meta.http.Body;
import restclient.meta.http.Param;
import restclient.meta.http.Path;
import restclient.model.*;
import restclient.operation.ApiBeanMethodInterceptor;
import restclient.operation.ApiTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * JDK Dynamic Proxy 기반 웹서비스 빈 생성 지원
 * <p/>
 * Created by chanwook on 2014. 6. 19..
 */
public class ProxySupportApiBeanFactory implements ApiBeanFactory {

    private RestTemplate springTemplate;

    @Override
    public ApiBean createBean(Class<?> spec, ApiHostMap apiHostMap) {
        API annotation = spec.getAnnotation(API.class);
        String key = annotation.key();
        ApiHost host = apiHostMap.getHost(key);

        SimpleApiBean target = createWebServiceBean(host);

        ApiSpecificationMeta specMeta = createApiSpecMeta(spec);
        target.setApiSpecificationMeta(specMeta);

        ProxyFactory proxy = new ProxyFactory();
        proxy.setTarget(target);
        proxy.setInterfaces(spec, ApiBean.class);

        proxy.addAdvice(new ApiBeanMethodInterceptor());
        return (ApiBean) proxy.getProxy();
    }

    protected ApiSpecificationMeta createApiSpecMeta(Class<?> spec) {
        ApiSpecificationMeta meta = new ApiSpecificationMeta();
        resolveParameterAnnotationMeta(meta, spec.getDeclaredMethods());
        return meta;
    }

    protected void resolveParameterAnnotationMeta(ApiSpecificationMeta meta, Method[] methods) {
        Map<String/*Method Name*/, Map<String/*Named Path*/, Integer/*ParameterIndex*/>> namedPathMap = new HashMap<String, Map<String, Integer>>();
        Map<String/*Method Name*/, Integer/*ParameterIndex*/> entityMap = new HashMap<String, Integer>();
        Map<String/*Method Name*/, Map<String/*Param Key*/, Integer/*ParameterIndex*/>> paramMap = new HashMap<String, Map<String, Integer>>();

        for (Method m : methods) {
            String name = m.getName();
            Class<?>[] paramTypes = m.getParameterTypes();
            Annotation[][] annotationsList = m.getParameterAnnotations();

            for (int paramIndex = 0; paramIndex < paramTypes.length; paramIndex++) {
                Annotation[] annotations = annotationsList[paramIndex];
                for (Annotation a : annotations) {
                    if (a instanceof Path) {
                        String key = ((Path) a).value();
                        if (!namedPathMap.containsKey(name)) {
                            namedPathMap.put(name, new HashMap<String, Integer>());
                        }
                        // 숫자로 직접 매핑하기 때문에 index를 1부터 시작
                        namedPathMap.get(name).put(key, paramIndex + 1);
                    } else if (a instanceof Body) {
                        entityMap.put(name, paramIndex);
                    } else if (a instanceof Param) {
                        String key = ((Param) a).value();
                        if (!paramMap.containsKey(name)) {
                            paramMap.put(name, new HashMap<String, Integer>());
                        }
                        paramMap.get(name).put(key, paramIndex);
                    }
                }
            }
        }
        meta.setNamedPathMap(namedPathMap);
        meta.setEntityMap(entityMap);
        meta.setParamMap(paramMap);
    }

    private SimpleApiBean createWebServiceBean(ApiHost host) {
        ApiTemplate template = createWebServiceTemlpate();
        return new SimpleApiBean(template, host);
    }

    private ApiTemplate createWebServiceTemlpate() {
        RestTemplate springTemplate = createSpringRestTemplate();
        return new ApiTemplate(springTemplate);
    }

    protected RestTemplate createSpringRestTemplate() {
        if (springTemplate != null) {
            return springTemplate;
        }
        RestTemplate t = new RestTemplate();
        return t;
    }

    public void setSpringTemplate(RestTemplate springTemplate) {
        this.springTemplate = springTemplate;
    }

    public RestTemplate getSpringTemplate() {
        return springTemplate;
    }
}
