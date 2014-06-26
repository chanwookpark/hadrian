package restclient.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import restclient.ApiConfigInitializingException;
import restclient.model.ApiHost;
import restclient.model.ApiParam;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by chanwook on 2014. 6. 19..
 */
public class ApiTemplate {
    private final Logger logger = LoggerFactory.getLogger(ApiTemplate.class);

    private final RestTemplate springTemplate;

    public ApiTemplate() {
        springTemplate = new RestTemplate();
    }

    public ApiTemplate(RestTemplate springTemplate) {
        this.springTemplate = springTemplate;
    }


    public Object execute(ApiParam param) {
        String apiUrl = createApiUrl(param);
        HttpEntity httpEntity = createHttpEntity(param);
        Map<String, Object> pathParam = createPathParam(param.getUrl(), param.getArguments(), param.getNamedPathMap());
        Class<?> returnType = resolveRetunType(param);

        if (logger.isDebugEnabled()) {
            logger.debug("===============================================================================" +
                "\nAPI 호출 상세 정보 [URL: " + apiUrl +
                    ", method: " + param.getMethod().name() +
                    ", arguments: " + param.getArguments() +
                    ", returnType: " + returnType +
                    ", named path: " + param.getNamedPathMap() +
                    ", entity: " + param.getEntity() +
                    ", url parameter: " + param.getUrlParameters() +
                    "]\n===============================================================================");
        }

        ResponseEntity<?> responseEntity =
                springTemplate.exchange(apiUrl, param.getMethod(), httpEntity, returnType, pathParam);

        return responseEntity.getBody();
    }

    private Class<?> resolveRetunType(ApiParam param) {
        Class<?> returnType = param.getReturnType();
        if (returnType.getName().equals("void")) {
            returnType = Void.class;
        }
        return returnType;
    }

    private HttpEntity createHttpEntity(ApiParam param) {
        HttpHeaders headers = new HttpHeaders();
        Object entity = param.getEntity();
        return new HttpEntity(entity, headers);
    }

    protected Map<String, Object> createPathParam(String url, Object[] arguments, Map<String, Integer> namedPathMap) {
        Map<String, Object> pathParam = new HashMap<String, Object>();
        String[] vars = url.split("/");
        for (String v : vars) {
            if (v.startsWith("{") && v.endsWith("}")) {
                String key = v.replace("{", "").replace("}", "");
                if (Pattern.matches("\\d", key)) {
                    int index = Integer.parseInt(key);
                    if (0 == index) {
                        throw new IllegalArgumentException("Path Variable의 index는 1부터 시작으로 '0' 값을 줄 수 없습니다!");
                    } else if (index > arguments.length) {
                        throw new IllegalArgumentException(key + "에 해당하는 순번의 파라미터 인자 값이 전달되지 않았습니다!(index: " + key + ", 파라미터 크기: " + arguments.length + ")");
                    }
                    Object value = arguments[index - 1];
                    pathParam.put(key, value);
                } else/* named path 지원 */ {
                    if (namedPathMap.containsKey(key)) {
                        int paramIndex = namedPathMap.get(key);
                        if (paramIndex > arguments.length) {
                            throw new IllegalArgumentException(key + "에 해당하는 순번의 파라미터 인자 값이 전달되지 않았습니다!(key: " + key + ", index: " + paramIndex + ", 파라미터 크기: " + arguments.length + ")");
                        }
                        pathParam.put(key, arguments[paramIndex - 1]);
                    } else {
                        throw new IllegalArgumentException(key + "에 해당하는 Named Path 값이 없습니다. @Path 애노테이션을 적절히 사용했는지 확인하세요!");
                    }
                }
            }
        }
        return pathParam;
    }

    private String createApiUrl(ApiParam param) {
        if (!StringUtils.hasText(param.getUrl()) || param.getApiHost() == null) {
            throw new ApiConfigInitializingException("API URL 정보가 잘 못됐습니다!!");
        }

        ApiHost apiHost = param.getApiHost();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder
                .scheme("http")
                .host(apiHost.getHost())
                .port(apiHost.getPort())
                .path(apiHost.getContextRoot())
                .path(param.getUrl());

        // parameter
        Map<String, Integer> urlParameters = param.getUrlParameters();
        for (Map.Entry<String, Integer> e : urlParameters.entrySet()) {
            String k = e.getKey();
            //TODO suport multi value
            Object v = param.getArguments()[e.getValue()];
            uriBuilder.queryParam(k, v);
        }
        return uriBuilder.build().toUriString();
    }

    public RestTemplate getSpringTemplate() {
        return springTemplate;
    }
}
