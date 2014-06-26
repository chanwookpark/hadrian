package restclient.config;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.web.client.RestTemplate;
import restclient.meta.Body;
import restclient.meta.Path;
import restclient.meta.WebService;
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
public class ProxySupportWebServiceBeanFactory implements ApiBeanFactory {

    private RestTemplate springTemplate;

    @Override
    public WebServiceBean createBean(Class<?> spec, ApiHostMap apiHostMap) {
        WebService annotation = spec.getAnnotation(WebService.class);
        String key = annotation.key();
        ApiHost host = apiHostMap.getHost(key);

        SimpleWebServiceBean target = createWebServiceBean(host);

        ApiSpecificationMeta specMeta = createApiSpecMeta(spec);
        target.setApiSpecificationMeta(specMeta);

        ProxyFactory proxy = new ProxyFactory();
        proxy.setTarget(target);
        proxy.setInterfaces(spec, WebServiceBean.class);

        proxy.addAdvice(new ApiBeanMethodInterceptor());
        return (WebServiceBean) proxy.getProxy();
    }

    protected ApiSpecificationMeta createApiSpecMeta(Class<?> spec) {
        ApiSpecificationMeta meta = new ApiSpecificationMeta();
        resolveParameterAnnotationMeta(meta, spec.getDeclaredMethods());
        return meta;
    }

    protected void resolveParameterAnnotationMeta(ApiSpecificationMeta meta, Method[] methods) {
        Map<String/*Method Name*/, Map<String/*Named Path*/, String/*ParameterIndex*/>> namedPathMap = new HashMap<String, Map<String, String>>();
        Map<String/*Method Name*/, String/*ParameterIndex*/> entityMap = new HashMap<String, String>();
        for (Method m : methods) {
            String name = m.getName();
            Class<?>[] paramTypes = m.getParameterTypes();
            for (int paramIndex = 0; paramIndex < paramTypes.length; paramIndex++) {
                Annotation[] annotations = paramTypes[paramIndex].getAnnotations();
                for (Annotation a : annotations) {
                    //@Path 지원
                    if (a instanceof Path) {
                        if (namedPathMap.containsKey(name)) {
                            // index를 1부터 시작
                            namedPathMap.get(name).put(((Path) a).value(), String.valueOf(paramIndex + 1));
                        }
                    } else if (a instanceof Body) {
                        entityMap.put(name, String.valueOf(paramIndex + 1));
                    }
                }
            }
        }
        meta.setNamedPathMap(namedPathMap);
        meta.setEntityMap(entityMap);
    }

    private SimpleWebServiceBean createWebServiceBean(ApiHost host) {
        ApiTemplate template = createWebServiceTemlpate();
        return new SimpleWebServiceBean(template, host);
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
