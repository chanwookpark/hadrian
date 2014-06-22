package restclient.startup;

import restclient.meta.GET;
import restclient.meta.WebService;
import restclient.model.WebServiceOperationSpec;

/**
 * Created by chanwook on 2014. 6. 18..
 */
@WebService(key = "sample")
public interface SampleApiSpec extends WebServiceOperationSpec {

    @GET(url = "/sample")
    Sample1 get();

    @GET(url = "/sample/{1}")
    Sample1 get(int i);

    void notAssigned();

    @GET(url = "/sample/{2}/u/{3}/c/{1}")
    Sample1 get(int v0, String v1, String v2);
}
