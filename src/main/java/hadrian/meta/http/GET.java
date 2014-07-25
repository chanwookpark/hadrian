package hadrian.meta.http;

import java.lang.annotation.*;

/**
 * Created by chanwook on 2014. 6. 21..
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GET {
    String url();

}
