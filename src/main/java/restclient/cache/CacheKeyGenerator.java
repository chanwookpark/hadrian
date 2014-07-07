package restclient.cache;

/**
 * Created by chanwook on 2014. 7. 7..
 */
public interface CacheKeyGenerator {
    int getCacheKey(Class<?> target, String javaMethodName, Object[] arguments);
}
