package restclient.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chanwook on 2014. 7. 7..
 */
public class ConcurrentHashMapApiCache implements ApiCache {
    private final Logger logger = LoggerFactory.getLogger(ConcurrentHashMapApiCache.class);

    private ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> cache = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>();

    @Override
    public Object get(String cacheKey, String rowKey) {
        if (cache.containsKey(cacheKey)) {
            ConcurrentHashMap<String, Object> cacheRowMap = cache.get(cacheKey);
            if (cacheRowMap.containsKey(rowKey)) {
                Object v = cacheRowMap.get(rowKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(">> Cache 값 조회(cachekey: " + cacheKey + ", rowkey: " + rowKey + ", value: " + v);
                }
                return v;
            }
        }
        return null;
    }

    @Override
    public void put(String cacheKey, String rowKey, Object value) {
        ConcurrentHashMap<String, Object> rowMap = cache.get(cacheKey);
        if (rowMap == null) {
            rowMap = new ConcurrentHashMap<String, Object>();
            cache.put(cacheKey, rowMap);
        }

        rowMap.put(rowKey, value);
        if (logger.isDebugEnabled()) {
            logger.debug(">> Cache 값 저장 (cachekey: " + cacheKey + ", rowkey: " + rowKey + ", value: " + value);
        }
    }
}
