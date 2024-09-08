package kr.com.pkh.batch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * test 코드에서 application.properties 의 데이터를 조회하기위한 클래스
 *
 * description : @TestPropertySource, @ActiveProfiles 원인불명의 이유로 application.properties 를 탐색하지못하여,
 *              ClassPathResource 클래스로 properties 를 탐색하고 데이터를 반환하기위한 클래스
 */
@Slf4j
@Component
public class PropertiesUtil {


    /**
     *
     * @param key properties 에 정의된 key 이름
     * @return key에 정의된 value 반환
     */
    public static String getProperty(String key) throws Exception {
        String value="";

        // 리소스 파일을 ClassPathResource로 로드 (src/main/java/resource 경로 탐색)
        Resource resource = new ClassPathResource("application.properties");


        // application.properties 파일 존재유무 확인
        if (!resource.exists()) {
            throw new IOException("Resource not found: " + resource.getFilename());
        }
        // 파일의 절대 경로를 출력
        log.info("application.properties Path: " + resource.getFile().getAbsolutePath());

        // 파일 내용 확인
//        String content = new String(Files.readAllBytes(Paths.get(resource.getFile().getAbsolutePath())));
//        System.out.println("Resource Content: " + content);

        // 파일내용을 properties 객체에 로드
        Properties properties = new Properties();
        try (InputStream inputStream = resource.getInputStream()) {
            properties.load(inputStream);
        }

        value = properties.getProperty(key);


        return value;
    }

}
