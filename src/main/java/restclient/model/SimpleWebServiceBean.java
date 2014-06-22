package restclient.model;

import restclient.operation.WebServiceTemplate;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class SimpleWebServiceBean implements WebServiceBean {

    private final WebServiceTemplate template;
    private final ApiHost host;

    public SimpleWebServiceBean(WebServiceTemplate template, ApiHost host) {
        this.template = template;
        this.host = host;
    }

    @Override
    public Object execute(WebServiceParam param) {
        param.hostUrl(host.getHostUrl());
        
        Object result = template.execute(param);
        return result;
    }

    public ApiHost getHost() {
        return host;
    }

    public WebServiceTemplate getTemplate() {
        return template;
    }
}
