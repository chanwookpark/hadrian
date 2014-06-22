package restclient.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.core.io.ClassPathResource;
import restclient.ApiConfigInitializingException;
import restclient.model.ApiHost;
import restclient.model.ApiHostMap;

/**
 * Created by chanwook on 2014. 6. 21..
 */
public class XmlConfigurationApiHostMapFactory implements ApiHostMapFactory {

    private String filePrefix = "api-host-";
    private String fileExtension = ".xml";

    @Override
    public ApiHostMap loadHostMap(String profile) {
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
