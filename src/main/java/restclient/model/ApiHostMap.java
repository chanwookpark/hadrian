package restclient.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import restclient.ApiConfigInitializingException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanwook on 2014. 6. 21..
 */
@XStreamAlias("apiHostMap")
public class ApiHostMap implements Serializable {

    @XStreamImplicit(itemFieldName = "apiHost")
    private List<ApiHost> apiHostList = new ArrayList<ApiHost>();

    public List<ApiHost> getApiHostList() {
        return apiHostList;
    }

    public void setApiHostList(List<ApiHost> apiHostList) {
        this.apiHostList = apiHostList;
    }

    public ApiHost getHost(String key) {
        for (ApiHost h : apiHostList) {
            if (key.equals(h.getKey())) {
                return h;
            }
        }
        throw new ApiConfigInitializingException(key + "에 해당하는 API Host 정보가 없습니다! api-host.xml 설정 파일을 확인하세요!");
    }
}
