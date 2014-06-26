package restclient.meta;

import java.lang.annotation.*;

/**
 * Created by chanwook on 2014. 6. 25..
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface POST {
    String url();
}
