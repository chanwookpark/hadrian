package restclient.model;

import restclient.operation.ApiTemplate;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class SimpleWebServiceBean implements WebServiceBean {

    private final ApiTemplate template;

    private final ApiHost host;

    private ApiSpecificationMeta apiSpecificationMeta;

    public SimpleWebServiceBean(ApiTemplate template, ApiHost host) {
        this.template = template;
        this.host = host;
    }

    @Override
    public Object execute(ApiParam param) {
        param.hostUrl(host.getHostUrl());
        param.namedPathMap(apiSpecificationMeta.getNamedPathMap(param.getJavaMethodName()));
        param.setEntity(apiSpecificationMeta.getEntityMap().get(param.getJavaMethodName()));

        Object result = template.execute(param);
        return result;
    }

    public ApiHost getHost() {
        return host;
    }

    public ApiTemplate getTemplate() {
        return template;
    }

    public void setApiSpecificationMeta(ApiSpecificationMeta apiSpecificationMeta) {
        this.apiSpecificationMeta = apiSpecificationMeta;
    }

    public ApiSpecificationMeta getApiSpecificationMeta() {
        return apiSpecificationMeta;
    }
}
