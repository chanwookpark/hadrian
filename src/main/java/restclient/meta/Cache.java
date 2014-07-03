package restclient.meta;

import java.lang.annotation.*;

/**
 * Created by chanwook on 2014. 7. 1..
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    String key();

    int expireTime();

}
