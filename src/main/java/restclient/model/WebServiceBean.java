package restclient.model;

import java.io.Serializable;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public interface WebServiceBean extends Serializable {
    Object execute(ApiParam param);

}
