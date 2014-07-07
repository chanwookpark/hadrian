package restclient.model;

import restclient.cache.CacheEntryMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 23..
 */
public class ApiSpecificationMeta {
    private Map<String, Map<String, Integer>> namedPathMap;
    private Map<String, Integer> entityMap;
    private Map<String, Map<String, Integer>> paramMap;
    private Map<String, CacheEntryMeta> cacheMap;

    public Map<String, Integer> getNamedPathMap(String javaMethodName) {
        if (namedPathMap != null && namedPathMap.containsKey(javaMethodName)) {
            return namedPathMap.get(javaMethodName);
        }
        // Meta 데이터가 없을 수도 있음
        return new HashMap<String, Integer>();
    }

    public Map<String, Integer> getEntityMap() {
        if (entityMap != null) {
            return entityMap;
        }
        return entityMap;
    }

    public Map<String, Map<String, Integer>> getNamedPathMap() {
        return namedPathMap;
    }

    public int getEntityIndex(String javaMethodName) {
        if (entityMap != null && entityMap.containsKey(javaMethodName)) {
            Integer index = entityMap.get(javaMethodName);
            return index;
        }
        return -1;
    }


    public CacheEntryMeta getCache(String javaMethodName) {
        if (cacheMap != null && cacheMap.containsKey(javaMethodName)) {
            return cacheMap.get(javaMethodName);
        }

        return null;
    }

    public Map<String, Integer> getParameters(String javaMethodName) {
        if (paramMap != null && paramMap.containsKey(javaMethodName)) {
            return paramMap.get(javaMethodName);
        }
        return new HashMap<String, Integer>();
    }

    public void setParamMap(Map<String, Map<String, Integer>> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Map<String, Integer>> getParamMap() {
        return paramMap;
    }

    public void setCacheMap(Map<String, CacheEntryMeta> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public Map<String, CacheEntryMeta> getCacheMap() {
        return cacheMap;
    }

    public void setEntityMap(Map<String, Integer> entityMap) {
        this.entityMap = entityMap;
    }

    public void setNamedPathMap(Map<String, Map<String, Integer>> namedPathMap) {
        this.namedPathMap = namedPathMap;
    }

}

