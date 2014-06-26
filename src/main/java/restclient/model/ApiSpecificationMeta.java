package restclient.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 23..
 */
public class ApiSpecificationMeta {
    private Map<String, Map<String, Integer>> namedPathMap;
    private Map<String, Integer> entityMap;

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

    public void setEntityMap(Map<String, Integer> entityMap) {
        this.entityMap = entityMap;
    }

    public void setNamedPathMap(Map<String, Map<String, Integer>> namedPathMap) {
        this.namedPathMap = namedPathMap;
    }

    public int getEntityIndex(String javaMethodName) {
        if (entityMap != null && entityMap.containsKey(javaMethodName)) {
            Integer index = entityMap.get(javaMethodName);
            return index;
        }
        return -1;
    }
}

