package kr.com.pkh.batch.extend.job.standard;

import kr.com.pkh.batch.extend.job.BatchTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Batch job 기본 테스트 코드 (정상)
 * 테스트 대상 job : StandardConfig.class
 * 참고 : 하나의 테스트 코드는 하나의 job 테스트 가능
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
class StandardConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void success() throws Exception {

        // given

        // when
        // StandardConfig 의 job 을 실행
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
    }
}