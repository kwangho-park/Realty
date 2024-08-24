package kr.com.pkh.batch.job;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 아파트 투자정보 가공 job <br>
 * run : --spring.batch.job.names=
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class InvestJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job investJobs(){
        return jobBuilderFactory.get("investJobs")
                .incrementer(new RunIdIncrementer())
                .start(investStep())
                .build();
    }

    @JobScope
    @Bean
    public Step investStep(){
        return stepBuilderFactory.get("investStep")
                .tasklet(investTaskLet())
                .build();
    }


    ///// 추후 별도의 tasklet 파일로 리팩토링 예정
    @StepScope
    @Bean
    public Tasklet investTaskLet(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                // step : tasklet
                // db tb_apt_trade 의 row 수 카운트
                // 100개씩 조회하여 리스트에 저장 (sql limit command)
                // 투자정보 가공 (평단가, 전세갭, 전세가율 등)
                // db tb_realty_info 테이블에 가공한 투자정보 insert or update

                System.out.println("[tasklet] invest task");
                return RepeatStatus.FINISHED;
            }
        };
    }


}
