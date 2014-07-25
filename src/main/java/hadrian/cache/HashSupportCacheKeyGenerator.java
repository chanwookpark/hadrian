package hadrian.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by chanwook on 2014. 7. 7..
 */
public class HashSupportCacheKeyGenerator implements CacheKeyGenerator {

    private final Logger logger = LoggerFactory.getLogger(HashSupportCacheKeyGenerator.class);

    @Override
    public int getCacheKey(Class<?> target, String javaMethodName, Object[] arguments) {
        int key = -1;
        if (arguments != null && arguments.length == 0) {
            String defaultKey = target.getName() + "." + javaMethodName;
            key = defaultKey.hashCode();
        } else if (arguments != null && arguments.length == 1) {
            key = arguments[0].hashCode();
        } else if (arguments != null) {
            key = Arrays.deepHashCode(arguments);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(">>Cache 키 발급[target class: " + target + ", method: " + javaMethodName + ", arguments: " + arguments + "]");
        }
        return key;
    }
}
