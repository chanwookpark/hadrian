package hadrian.config;

import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import hadrian.ApiConfigInitializingException;
import hadrian.model.ApiHost;
import hadrian.model.ApiHostMap;

/**
 * Created by chanwook on 2014. 6. 21..
 */
public class XmlConfigurationApiHostMapFactory implements ApiHostMapFactory {

    public static final String SPRING_PROFILES_KEY = "spring.profiles.active";
    public static final String DEFAULT_PROFILE = "local";

    private final Logger logger = LoggerFactory.getLogger(XmlConfigurationApiHostMapFactory.class);

    private String filePrefix = "api-host-";
    private String fileExtension = ".xml";

    @Override
    public ApiHostMap loadHostMap() {
        String profile = resolveProfile();

        logger.info(profile + " 프로파일 조건으로 API Configuration 초기화를 진행합니다.");

        try {
            XStream stream = new XStream();
            stream.processAnnotations(ApiHostMap.class);
            stream.processAnnotations(ApiHost.class);

            ClassPathResource file = new ClassPathResource(filePrefix + profile + fileExtension);
            ApiHostMap a = (ApiHostMap) stream.fromXML(file.getFile());
            return a;
        } catch (Exception e) {
            throw new ApiConfigInitializingException("API 호스트 설정 정보 파일이 없거나 호스트 파일 정보 로딩 중 예외가 발생했습니다.", e);
        }
    }

    private String resolveProfile() {
        String profile = System.getProperty(SPRING_PROFILES_KEY, DEFAULT_PROFILE);
        return profile;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFilePrefix() {
        return filePrefix;
    }
}
