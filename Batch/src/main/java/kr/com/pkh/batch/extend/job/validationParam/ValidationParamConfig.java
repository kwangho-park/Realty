package kr.com.pkh.batch.extend.job.validationParam;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


/**
 * batch 실행 시 파라미터를 입력받아서 검증 후 파일명으로 사용
 * batch 실행시 옵션 --spring.batch.job.names=validationParamJob -fileName=test.csv
 */
@Configuration
@RequiredArgsConstructor
public class ValidationParamConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job validationParamJob(Step validationParamStep){
		return jobBuilderFactory.get("validationParamJob")			// job 이름 정의 (객체명)
				.incrementer(new RunIdIncrementer())
				.validator(new FileParamValidator())				// batch 실행 시 입력받는 parameter (csv) 를 검증하는 객체 등록
				// .validator(multipleValidator())
				.start(validationParamStep)
				.build();
	}

	// validationParamJob 에 다수의 validator 등록을 위한 함수
	private CompositeJobParametersValidator mutipleValidator(){
		CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
		validator.setValidators(Arrays.asList(new FileParamValidator(), null, null, null) );

		return validator;
	}


	@JobScope
	@Bean
	public Step validationParamStep(Tasklet validationParamTasklet){
		return stepBuilderFactory.get("validationParamStep")		// step 이름 정의
				.tasklet(validationParamTasklet)
				.build();
	}

	/**
	 * parameter 검증 task
	 *
	 * @param fileName batch 실행 시 입력받은 parameter
	 * @return
	 */
	@StepScope
	@Bean
	public Tasklet validationParamTasklet(@Value("#{jobParameters['fileName']}") String fileName){
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

				System.out.println("[tasklet] validation param !!");
				System.out.println("file name : "+fileName);
				return RepeatStatus.FINISHED;
			}
		};
	}

}
