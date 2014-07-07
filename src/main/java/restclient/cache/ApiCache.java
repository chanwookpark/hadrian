package restclient.cache;

/**
 * Created by chanwook on 2014. 7. 7..
 */
public interface ApiCache {
    Object get(String cacheKey, String rowKey);

    void put(String cacheKey, String rowKey, Object value);
}
