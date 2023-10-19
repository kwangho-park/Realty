package kr.com.pkh.batch.extend.job.DbDataReadWrite;

import kr.com.pkh.batch.extend.job.DbDataReadWrite.accounts.AccountEntity;
import kr.com.pkh.batch.extend.job.DbDataReadWrite.accounts.AccountRepository;
import kr.com.pkh.batch.extend.job.DbDataReadWrite.orders.OrderEntity;
import kr.com.pkh.batch.extend.job.DbDataReadWrite.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
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
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 주문 테이블 -> 정산 테이블 데이터 이관
 * run : --spring.batch.job.names=trMigrationJob
 */
@Configuration
@RequiredArgsConstructor
public class TrMigrationConfig {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job trMigrationJob(Step trMigrationStep){
        return jobBuilderFactory.get("trMigrationJob")      // Define job name and return job builder
                .incrementer(new RunIdIncrementer())
                .start(trMigrationStep)
                .build();
    }

    @JobScope
    @Bean
    public Step trMigrationStep(@Qualifier("trOrderReader") ItemReader trOrdersReader,  // reader, processor 주입
                                ItemProcessor trOrderProcessor,
                                ItemWriter trOrderWriter){
        return stepBuilderFactory.get("trMigrationStep")
                .<OrderEntity, AccountEntity>chunk(5)      // [중요] OrderEntity로 조회하여, AccountEntity 로 데이터를 추가하며, 5개 row 단위로 트랜잭션 (chunk ; 덩어리)
                .reader(trOrdersReader)                             // reader 지정

                // Reader 함수 테스트용 출력 : 사용 시 ( SimpleStepBuiler 의 processor(), writer() 객체함수 주석처리
//                .writer(new ItemWriter() {
//                    @Override
//                    public void write(List items) throws Exception {
//                        items.forEach(System.out::println);
//                    }
//                })
                .processor(trOrderProcessor)
                .writer(trOrderWriter)
                .build();
    }


    @StepScope
    @Bean
    public RepositoryItemReader<OrderEntity> trOrderReader(){
        return new RepositoryItemReaderBuilder<OrderEntity>()
                .name("trOrderReader")
                .repository(orderRepository)        // Read 시 사용하는 JPA Repository 지정 (=DAO)
                .methodName("findAll")              // 전체 데이터 조회
                .pageSize(10)                        // view page size (=row count)
                .arguments(Arrays.asList())         // query 의 parameter 지정
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))      // ID 기준으로 정렬
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<OrderEntity, AccountEntity> trOrderProcessor(){
        return new ItemProcessor<OrderEntity, AccountEntity>() {
            @Override
            public AccountEntity process(OrderEntity item) throws Exception {
                return new AccountEntity(item);
            }
        };
    }


    // Sprinb batch 에서 제공하는 JPA 구현체 (RepositoryItemWriter) 사용하여 DB 접근
//    @StepScope
//    @Bean
//    public RepositoryItemWriter<AccountEntity> trOrdersWriter() {
//        return new RepositoryItemWriterBuilder<AccountEntity>()
//                .repository(accountRepository)
//                .methodName("save")
//                .build();
//    }

    // JPA를 사용하여 DB 접근
    @StepScope
    @Bean
    public ItemWriter<AccountEntity> trOrderWriter(){
        return new ItemWriter<AccountEntity>(){
            @Override
            public void write(List<? extends AccountEntity> items) throws Exception{
                items.forEach(item->accountRepository.save(item));
            }
        };
    }
}
