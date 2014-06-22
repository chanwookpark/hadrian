package restclient;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class ApiConfigInitializingException extends RuntimeException {
    public ApiConfigInitializingException(String msg, Exception e) {
        super(msg, e);
    }

    public ApiConfigInitializingException(String msg) {
        super(msg);
    }
}
