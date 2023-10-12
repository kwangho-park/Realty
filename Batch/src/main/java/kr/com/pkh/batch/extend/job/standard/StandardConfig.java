package kr.com.pkh.batch.extend.job.standard;

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
 * batch 실행시 옵션 --spring.batch.job.names=helloWorldJob
 *
 * IntelliJ 실행 시 설정 : edit config > program args 설정
 */
@Configuration
@RequiredArgsConstructor
public class StandardConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job standardJob(){
		return jobBuilderFactory.get("standardJob")
				.incrementer(new RunIdIncrementer())
				.start(standardStep())
				.build();
	}

	@JobScope
	@Bean
	public Step standardStep(){
		return stepBuilderFactory.get("standardStep")
				.tasklet(standardTaskLet())
				.build();
	}


	@StepScope
	@Bean
	public Tasklet standardTaskLet(){
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

				System.out.println("[tasklet] standard task");
				return RepeatStatus.FINISHED;
			}
		};
	}



}
