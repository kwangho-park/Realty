package kr.com.pkh.batch.extend.job.standard;

import kr.com.pkh.batch.dao2.UserInfoService;
import kr.com.pkh.batch.dto.UserInfoDTO;
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

import java.util.ArrayList;


/**
 * step 처리방식 : task let
 * batch 실행시 옵션 --spring.batch.job.names=helloWorldJob
 *
 * IntelliJ 실행 시 설정 : edit config > program args 설정
 */
@Configuration		// Batch job 실행하기위해 필요한 어노테이션
@RequiredArgsConstructor
public class StandardConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;


	@Autowired
	private UserInfoService userInfoService;

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

				//// mybatis 설정 테스트
				ArrayList<UserInfoDTO> list =  userInfoService.selectUserList();


				for(int loop=0 ; loop<list.size();loop++){
					int id = list.get(loop).getUI_ID();
					String userId = list.get(loop).getUI_USER_ID();
					String userName = list.get(loop).getUI_USER_NAME();
					String userPw = list.get(loop).getUI_USER_PW();

					System.out.println(id+"/"+userId+"/"+userName+"/"+userPw);
				}

				return RepeatStatus.FINISHED;
			}
		};
	}



}
