package restclient.config;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.web.client.RestTemplate;
import restclient.meta.WebService;
import restclient.model.ApiHost;
import restclient.model.ApiHostMap;
import restclient.model.SimpleWebServiceBean;
import restclient.model.WebServiceBean;
import restclient.operation.WebServiceBeanMethodInterceptor;
import restclient.operation.WebServiceTemplate;

/**
 * JDK Dynamic Proxy 기반 웹서비스 빈 생성 지원
 * <p/>
 * Created by chanwook on 2014. 6. 19..
 */
public class ProxySupportWebServiceBeanFactory implements WebServiceBeanFactory {

    private RestTemplate springTemplate;

    @Override
    public WebServiceBean createBean(Class<?> spec, ApiHostMap apiHostMap) {
        WebService annotation = spec.getAnnotation(WebService.class);
        String key = annotation.key();
        ApiHost host = apiHostMap.getHost(key);

        SimpleWebServiceBean target = createWebServiceBean(host);

        ProxyFactory proxy = new ProxyFactory();
        proxy.setTarget(target);
        proxy.setInterfaces(spec, WebServiceBean.class);

        proxy.addAdvice(new WebServiceBeanMethodInterceptor());
        return (WebServiceBean) proxy.getProxy();
    }

    private SimpleWebServiceBean createWebServiceBean(ApiHost host) {
        WebServiceTemplate template = createWebServiceTemlpate();
        return new SimpleWebServiceBean(template, host);
    }

    private WebServiceTemplate createWebServiceTemlpate() {
        RestTemplate springTemplate = createSpringRestTemplate();
        return new WebServiceTemplate(springTemplate);
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
