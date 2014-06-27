package restclient.startup;

import restclient.meta.API;
import restclient.meta.http.*;

import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 18..
 */
@API(key = "sample")
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

    @GET(url = "/sample/{path1}")
    Sample1 getWithMapParam(@Path("path1") String path, @Param("param1") Map<String, String> paramMap);

    @POST(url = "/sample/")
    void save(@Body Sample1 s);

    @PUT(url = "/sample/")
    void update(@Body Sample1 s);

    @DELETE(url = "/sample/{key}")
    void delete(@Path("key") int key);

}
