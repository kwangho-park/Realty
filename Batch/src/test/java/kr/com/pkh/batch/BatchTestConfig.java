package kr.com.pkh.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 테스트 코드에서 전역으로 사용하는 config
 */
@Configuration      // Batch job 을 실행하기위해 필요한 어노테이션
@EnableAutoConfiguration
@EnableBatchProcessing
public class BatchTestConfig {
}
