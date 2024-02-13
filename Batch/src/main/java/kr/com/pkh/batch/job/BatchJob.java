package kr.com.pkh.batch.job;


import kr.com.pkh.batch.dao.AptTradeRepository;
import kr.com.pkh.batch.dto.AptTradeEntity;
import kr.com.pkh.batch.openAPI.data.RTMSOBJSvc;
import kr.com.pkh.batch.step.chunk.processor.AptTradeProcessor;
import kr.com.pkh.batch.step.chunk.reader.RestItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * run : --spring.batch.job.names=collectRealtyJob
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RTMSOBJSvc RTMSOBJSvc;

    @Autowired
    private AptTradeRepository aptTradeRepository;

    @Bean
    public Job collectRealtyJob(Step aptTradeStep){
        return jobBuilderFactory.get("collectRealtyJob")        // job 이름 정의
                .incrementer(new RunIdIncrementer())
                .start(aptTradeStep)
                .build();
    }


    @JobScope
    @Bean
    public Step aptTradeStep(ItemReader restItemReader,
                             ItemProcessor aptTradeProcessor,
                             ItemWriter aptTradeWriter
        ){
        return stepBuilderFactory.get("aptTradeStep")
                .<List<String>, AptTradeEntity>chunk(1)   // List<String> 조회(reader DTO) 하여, AptTradeEntity (writer DTO) 로 데이터를 추가하며, 5개 row 단위로  parsing step 을 트랜잭션
                .reader(restItemReader)                       // reader 지정
                .processor(aptTradeProcessor)                 // processor 지정
                .writer(aptTradeWriter)                       // writer 지정
                .build();
    }

    @StepScope
    @Bean
    public ItemReader<List<String>> restItemReader() {
        return new RestItemReader(RTMSOBJSvc);
    }

    @StepScope
    @Bean
    public ItemProcessor<List<String>, AptTradeEntity> aptTradeProcessor() {
        return new AptTradeProcessor();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<AptTradeEntity> aptTradeWriter() {
        return new RepositoryItemWriterBuilder<AptTradeEntity>()
                .repository(aptTradeRepository)
                .methodName("save")
                .build();
    }



}
