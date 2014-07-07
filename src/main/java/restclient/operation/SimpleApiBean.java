package restclient.operation;

import restclient.cache.*;
import restclient.model.ApiHost;
import restclient.model.ApiParam;
import restclient.model.ApiSpecificationMeta;

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

    public SimpleApiBean(ApiTemplate template, ApiHost host) {
        this.template = template;
        this.host = host;
    }

    @Override
    public Object execute(ApiParam param) {
        param.host(host);

        Map<String, Integer> namedPathMap = apiSpecificationMeta.getNamedPathMap(param.getJavaMethodName());
        param.namedPathMap(namedPathMap);

        Map<String, Integer> parameterMap = apiSpecificationMeta.getParameters(param.getJavaMethodName());
        param.urlParameters(parameterMap);

        //FIXME 별도로 뽑아내도록 리팩터링 하기..
        // cachemeta가 있으면 cachewrapper를 생성해서 이걸 통해서 처리하도록 하면 되는가?
        CacheEntryMeta cacheMeta = apiSpecificationMeta.getCache(param.getJavaMethodName());
        if (isCacheSupport(cacheMeta)) {

            String cacheKey = cacheMeta.getKey();
            int cacheRowKey = getCacheRowKey(param);

            Object cachedValue = apiCache.get(cacheKey, String.valueOf(cacheRowKey));
            if (cachedValue != null) {
                return cachedValue;
            }
        }

        resolveEntityBody(param);

        Object result = template.execute(param);
        if (result != null && isCacheSupport(cacheMeta)) {
            String cacheKey = cacheMeta.getKey();
            int cacheRowKey = getCacheRowKey(param);

            apiCache.put(cacheKey, String.valueOf(cacheRowKey), result);
        }
        return result;
    }

    private int getCacheRowKey(ApiParam param) {
        return cacheKeyGenerator.getCacheKey(param.getClass(), param.getJavaMethodName(), param.getArguments());
    }

    private boolean isCacheSupport(CacheEntryMeta cacheMeta) {
        return apiCache != null && cacheKeyGenerator != null && cacheMeta != null;
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
}
