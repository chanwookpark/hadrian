package restclient.startup;

import restclient.meta.*;

/**
 * Created by chanwook on 2014. 6. 18..
 */
@WebService(key = "sample")
public interface SampleApiSpec {

    @GET(url = "/sample")
    Sample1 get();

    @GET(url = "/sample/{1}")
    Sample1 get(int i);

    void notAssigned();

    @GET(url = "/sample/{2}/u/{3}/c/{1}")
    Sample1 get(int v0, String v1, String v2);

    @GET(url = "/sample/{name1}")
    Sample1 getWithPath(@Path("name1") String path);

    @GET(url = "/sample/{path1}")
    Sample1 getWithPath2(@Path("path1") String path, @Param("param1") String param);

    @POST(url = "/sample/")
    void save(@Body Sample1 s);

}
