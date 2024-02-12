package kr.com.pkh.batch.job;


import kr.com.pkh.batch.dao.AptTradeRepository;
import kr.com.pkh.batch.dto.AptTradeDTO;
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
import java.util.Objects;

/**
 * 데이터 파싱 job ([HR DB] hrtb_user_info -> [Manager DB] tb_tmp_info )
 * run : --spring.batch.job.names=aptSyncJob
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

    // 무한실행
    @Bean
    public Job aptSyncJob(Step aptTradeStep){
        return jobBuilderFactory.get("aptSyncJob")        // job 이름 정의
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
                .<List<String>, AptTradeEntity>chunk(1)         // AptTradeDTO 조회(reader DTO) 하여, AptTradeEntity (writer DTO) 로 데이터를 추가하며, 5개 row 단위로  parsing step 을 트랜잭션
                .reader(restItemReader)                       // reader 지정
                .processor(aptTradeProcessor)                 // processor 지정
                .writer(aptTradeWriter)                       // writer 지정
                .build();
    }

    // [작업중] reader 에서 processor 로직으로 넘어가지않음
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


    // tasklet 방식 step test
//    @Bean
//    public Job job(Step step){
//        return jobBuilderFactory.get("job")        // job 이름 정의
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//    }
//    @JobScope
//    @Bean
//    public Step step(){
//        return stepBuilderFactory.get("step")
//                .tasklet(taskLet())
//                .build();
//    }
//
//
//    @StepScope
//    @Bean
//    public Tasklet taskLet(){
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//
//                System.out.println("[tasklet] standard task");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }



}
