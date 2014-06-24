package restclient.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 23..
 */
public class ApiSpecificationMeta {
    private Map<String, Map<String, String>> namedPathMap;

    public void setNamedPathMap(Map<String, Map<String, String>> namedPathMap) {
        this.namedPathMap = namedPathMap;
    }

    public Map<String, Map<String, String>> getNamedPathMap() {
        return namedPathMap;
    }

    public Map<String, String> getNamedPathMap(String javaMethodName) {
        if (namedPathMap.containsKey(javaMethodName)) {
            return namedPathMap.get(javaMethodName);
        }
        // Meta 데이터가 없을 수도 있음
        return new HashMap<String, String>();
    }
}
