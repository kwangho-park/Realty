package kr.com.pkh.batch.job;

//import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
@RunWith(SpringRunner.class)
public class CollectAreaTypeJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job  collectBuildingJob;


    @BeforeEach
    public void setUp() {
        // job 및 step 실행 전 동작
        // JobLauncherTestUtils에 Job을 수동으로 설정
        jobLauncherTestUtils.setJob(collectBuildingJob);
    }

    ////////////// 테스트 코드 실행시 에러발생
    @Test
    public void testJob() throws Exception {

        // Job Parameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("pnu", "Test")
                .toJobParameters();

        // Job 실행
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // Job 실행 상태 검증
        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testStep() throws Exception {

        // step 실행
        JobExecution stepExecution = jobLauncherTestUtils.launchStep("areaTypeStep");

        // Step 실행 상태 검증
        assertEquals("COMPLETED", stepExecution.getExitStatus().getExitCode());
    }

}
