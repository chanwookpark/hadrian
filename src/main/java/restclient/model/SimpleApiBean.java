package restclient.model;

import restclient.operation.ApiTemplate;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class SimpleApiBean implements ApiBean {

    private final ApiTemplate template;

    private final ApiHost host;

    private ApiSpecificationMeta apiSpecificationMeta;

    public SimpleApiBean(ApiTemplate template, ApiHost host) {
        this.template = template;
        this.host = host;
    }

    @Override
    public Object execute(ApiParam param) {
        param.hostUrl(host.getHostUrl());
        param.namedPathMap(apiSpecificationMeta.getNamedPathMap(param.getJavaMethodName()));
        resolveParamEntity(param);

        Object result = template.execute(param);
        return result;
    }

    private void resolveParamEntity(ApiParam param) {
        int entityIndex = apiSpecificationMeta.getEntityIndex(param.getJavaMethodName());
        if (entityIndex > 0 && param.getArguments().length > entityIndex) {
            Object entity = param.getArguments()[entityIndex];
            if (entity != null) {
                param.setEntity(entity);
            }
        }
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
