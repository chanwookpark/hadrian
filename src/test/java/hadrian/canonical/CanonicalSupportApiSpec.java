package hadrian.canonical;

import hadrian.meta.API;
import hadrian.meta.http.GET;

/**
 * Created by chanwook on 2014. 7. 23..
 */
@API(key = "canonicalapi")
public interface CanonicalSupportApiSpec {

    @GET(url = "/canonical-smaple/{1}")
    ApiMessage get(String id);
}
