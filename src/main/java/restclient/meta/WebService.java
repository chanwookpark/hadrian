package restclient.meta;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by chanwook on 2014. 6. 18..
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface WebService {
    String key();
}
