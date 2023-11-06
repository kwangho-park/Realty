package kr.com.pkh.batch.extend.job.DbDataReadWrite;

import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.DbDataReadWrite.accounts.AccountRepository;
import kr.com.pkh.batch.extend.job.DbDataReadWrite.orders.OrderEntity;
import kr.com.pkh.batch.extend.job.DbDataReadWrite.orders.OrderRepository;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Date;


/**
 * DB connection 관련 Batch job 테스트 (비정상)
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, TrMigrationConfig.class})
class TrMigrationConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private OrderRepository orderRepository;        // [해결중] Could not autowire. No beans of 'OrderRepository' type found.

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void cleanUpEach() {
        orderRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test()
    public void success_noData() throws Exception {
        // when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(0, accountRepository.count());
    }

    @Test()
    public void success_existData() throws Exception {
        // given
        OrderEntity orders1 = new OrderEntity(null, "kakao gift", 15000, new Date());
        OrderEntity orders2 = new OrderEntity(null, "naver gift", 15000, new Date());

        orderRepository.save(orders1);
        orderRepository.save(orders2);

        // when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(2, accountRepository.count());
    }
}