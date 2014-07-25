package hadrian.meta.http;

import java.lang.annotation.*;

/**
 * Created by chanwook on 2014. 6. 26..
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    String value() default "";
}
