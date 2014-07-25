package restclient.startup;

import restclient.meta.API;
import restclient.meta.Cache;
import restclient.meta.http.*;

import java.util.Map;

/**
 * Created by chanwook on 2014. 6. 18..
 */
@API(key = "sample")
public interface SampleApiSpec {

    /**
     * 단순 url 매핑 예제
     * @return
     */
    @GET(url = "/sample")
    Sample1 get();

    /**
     * 1개의 Path 변수를 순서대로 매핑 예제
     * @param i
     * @return
     */
    @GET(url = "/sample/{1}")
    Sample1 get(int i);

    /**
     * 아무런 선언도 안 되어 있는 메서드는 대상이 아님
     */
    void notAssigned();

    /**
     * 다수의 Path 변수를 순서대로 매핑 예제
     * @param v0
     * @param v1
     * @param v2
     * @return
     */
    @GET(url = "/sample/{2}/u/{3}/c/{1}")
    Sample1 get(int v0, String v1, String v2);

    /**
     * name으로 Path 변수 매핑하는 예제
     *
     * @param path
     * @return
     */
    @GET(url = "/sample/{name1}")
    Sample1 getWithPath(@Path("name1") String path);

    /**
     * name Path 매핑과 파라미터 1개 전달 예제
     *
     * @param path
     * @param param
     * @return
     */
    @GET(url = "/sample/{path1}")
    Sample1 getWithPath2(@Path("path1") String path, @Param("param1") String param);

    /**
     * name Path 매핑과 파라미터 2개(Primitive type과 Map type) 전달 예제
     *
     * @param path
     * @param paramMap
     * @return
     */
    @GET(url = "/sample/{path1}")
    Sample1 getWithMapParam(@Path("path1") String path, @Param("param1") Map<String, String> paramMap);

    /**
     * POST 요청 예제
     *
     * @param s
     */
    @POST(url = "/sample/")
    void save(@Body Sample1 s);

    /**
     * PUT+Reuqest Body 요청 예제
     *
     * @param s
     */
    @PUT(url = "/sample/")
    void update(@Body Sample1 s);

    /**
     * DELETE 요청 예제
     * @param key
     */
    @DELETE(url = "/sample/{key}")
    void delete(@Path("key") int key);

    /**
     * Cache 지원 예제
     *
     * @param path
     * @return
     */
    @GET(url = "/sample/{path}")
    @Cache(key = "api.cache.sample", expireTime = 600)
    Sample1 getWithCache(@Path("path") String path);
}
