package restclient.operation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import restclient.meta.GET;
import restclient.meta.HttpMethod;
import restclient.meta.POST;
import restclient.model.ApiParam;
import restclient.model.WebServiceBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class ApiBeanMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        if (isJavaObjectOperation(invocation.getMethod().getName())) {
            return invocation.proceed();
        }

        if (invocation.getThis() instanceof WebServiceBean) {
            WebServiceBean wsb = (WebServiceBean) invocation.getThis();
            ApiParam param = new ApiParam(invocation.getMethod().getName());

            resolveHttpMethod(param, invocation.getMethod());

            param.arguments(invocation.getArguments());

            param.returnType(invocation.getMethod().getReturnType());

            Object response = wsb.execute(param);

            return response;
        }

        return invocation.proceed();
    }

    private void resolveHttpMethod(ApiParam param, Method method) {
        for (Annotation a : method.getDeclaredAnnotations()) {
            if (a instanceof GET) {
                GET get = (GET) a;
                param.url(get.url());
                param.method(HttpMethod.GET);
            } else if (a instanceof POST) {
                POST post = (POST) a;
                param.url(post.url());
                param.method(HttpMethod.POST);
            }
        }
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