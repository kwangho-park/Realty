package kr.com.pkh.batch.extend.jobListener;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * step 처리방식 : task let
 * batch 실행시 옵션 --spring.batch.job.names=jobListenerJob
 *
 * description : 로그 작업
 */
@Configuration
@RequiredArgsConstructor
public class JobListenerConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobListenerJob(){
        return jobBuilderFactory.get("jobListenerJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())            // 리스너 등록 (=interceptor 역할 )
                .start(jobListenerStep())
                .build();
    }

    @JobScope
    @Bean
    public Step jobListenerStep(){
        return stepBuilderFactory.get("jobListenerStep")
                .tasklet(jobListenerTaskLet())
                .build();
    }


    @StepScope
    @Bean
    public Tasklet jobListenerTaskLet(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                System.out.println("[tasklet] job listener");
                return RepeatStatus.FINISHED;
            }
        };
    }

}
