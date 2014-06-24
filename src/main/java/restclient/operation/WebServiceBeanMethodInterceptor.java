package restclient.operation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import restclient.meta.GET;
import restclient.meta.HttpMethod;
import restclient.model.WebServiceBean;
import restclient.model.WebServiceParam;

import java.lang.annotation.Annotation;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class WebServiceBeanMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        if (isJavaObjectOperation(invocation.getMethod().getName())) {
            return invocation.proceed();
        }

        if (invocation.getThis() instanceof WebServiceBean) {
            WebServiceBean wsb = (WebServiceBean) invocation.getThis();
            WebServiceParam param = new WebServiceParam(invocation.getMethod().getName());

            for (Annotation a : invocation.getMethod().getDeclaredAnnotations()) {
                if (a.annotationType().equals(GET.class)) {
                    GET get = (GET) a;
                    param.url(get.url());
                    param.method(HttpMethod.GET);
                }
            }

            param.arguments(invocation.getArguments());

            param.returnType(invocation.getMethod().getReturnType());

            Object response = wsb.execute(param);

            return response;
        }

        throw new UnsupportedOperationException();
    }

    private boolean isJavaObjectOperation(String methodName) {
        if ("toString".equals(methodName) || "equals".equals(methodName) || "clone".equals(methodName)
                || "getClass".equals(methodName) || "hashCode".equals(methodName) || "notify".equals(methodName)
                || "notifyAll".equals(methodName) || "wait".equals(methodName) || "finalize".equals(methodName)) {
            return true;
        }
        return false;
    }
}
