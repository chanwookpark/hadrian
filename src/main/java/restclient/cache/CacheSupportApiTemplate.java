package restclient.cache;

import restclient.model.ApiParam;
import restclient.operation.ApiTemplate;

/**
 * Created by chanwook on 2014. 7. 7..
 */
public class CacheSupportApiTemplate {

    private final ApiCache apiCache;
    private final CacheKeyGenerator cacheKeyGenerator;
    private final ApiTemplate template;

    public CacheSupportApiTemplate(ApiCache apiCache, CacheKeyGenerator cacheKeyGenerator, ApiTemplate template) {
        this.apiCache = apiCache;
        this.cacheKeyGenerator = cacheKeyGenerator;
        this.template = template;
    }

    public Object execute(ApiParam param, CacheEntryMeta cacheMeta) {
        if (cacheMeta != null) {
            String cacheKey = cacheMeta.getKey();
            int cacheRowKey = getCacheRowKey(param);

            Object cachedValue = apiCache.get(cacheKey, String.valueOf(cacheRowKey));
            if (cachedValue != null) {
                return cachedValue;
            }
        }

        Object result = template.execute(param);

        if (result != null) {
            String cacheKey = cacheMeta.getKey();
            int cacheRowKey = getCacheRowKey(param);

            apiCache.put(cacheKey, String.valueOf(cacheRowKey), result);
        }
        return result;
    }

    private int getCacheRowKey(ApiParam param) {
        return cacheKeyGenerator.getCacheKey(param.getClass(), param.getJavaMethodName(), param.getArguments());
    }
}
