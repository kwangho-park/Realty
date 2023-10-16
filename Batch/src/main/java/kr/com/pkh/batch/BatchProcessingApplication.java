package kr.com.pkh.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class BatchProcessingApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BatchProcessingApplication.class, args);
	}
}
