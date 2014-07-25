package hadrian.operation;

import hadrian.cache.*;
import hadrian.model.ApiHost;
import hadrian.model.ApiParam;
import hadrian.model.ApiSpecificationMeta;

import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class SimpleApiBean implements ApiBean {

    private final ApiTemplate template;

    private final ApiHost host;

    private ApiSpecificationMeta apiSpecificationMeta;

    private ApiCache apiCache = new ConcurrentHashMapApiCache();

    private CacheKeyGenerator cacheKeyGenerator = new HashSupportCacheKeyGenerator();

    CacheSupportApiTemplate cachedApiTemplate;

    public SimpleApiBean(ApiTemplate template, ApiHost host) {
        this.template = template;
        this.host = host;

        cachedApiTemplate = new CacheSupportApiTemplate(apiCache, cacheKeyGenerator, template);
    }

    @Override
    public Object execute(ApiParam param) {
        param.host(host);

        Map<String, Integer> namedPathMap = apiSpecificationMeta.getNamedPathMap(param.getJavaMethodName());
        param.namedPathMap(namedPathMap);

        Map<String, Integer> parameterMap = apiSpecificationMeta.getParameters(param.getJavaMethodName());
        param.urlParameters(parameterMap);

        resolveEntityBody(param);

        CacheEntryMeta cacheMeta = apiSpecificationMeta.getCache(param.getJavaMethodName());
        Object result = null;
        if (cacheMeta != null) {
            result = cachedApiTemplate.execute(param, cacheMeta);
        } else {
            result = template.execute(param);
        }
        return result;

    }

    private void resolveEntityBody(ApiParam param) {
        int entityIndex = apiSpecificationMeta.getEntityIndex(param.getJavaMethodName());
        if (entityIndex > -1 && param.getArguments().length > entityIndex) {
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

    public void setApiCache(ApiCache apiCache) {
        this.apiCache = apiCache;
    }

    public void setCacheKeyGenerator(CacheKeyGenerator cacheKeyGenerator) {
        this.cacheKeyGenerator = cacheKeyGenerator;
    }
}
