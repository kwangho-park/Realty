package kr.com.pkh.batch.extend.job.DbDataReadWrite;

import kr.com.pkh.batch.extend.domain.accounts.AccountEntity;
import kr.com.pkh.batch.extend.domain.accounts.AccountRepository;
import kr.com.pkh.batch.extend.domain.orders.OrderEntity;
import kr.com.pkh.batch.extend.domain.orders.OrderRepository;
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
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.rmi.AccessException;
import java.util.Arrays;
import java.util.Collection;
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
        return jobBuilderFactory.get("trMigrationJob")
                .incrementer(new RunIdIncrementer())
                .start(trMigrationStep)
                .build();
    }

    @JobScope
    @Bean
    public Step trMigrationStep(ItemReader trOrdersReader, ItemProcessor trOrderProcessor, ItemWriter trOrderWriter){         // reader, processor 주입
        return stepBuilderFactory.get("trMigrationStep")
                .<OrderEntity, AccountEntity>chunk(5)      // 어떤데이터로 읽어서 어떤데이터로 쓸껀지 + 몇개단위로 사용할건지 : 5개 (트랜잭션의 단위)
                .reader(trOrdersReader)                             // reader 지정
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
                .pageSize(5)                        // 조회 할 row 수 단위 : 보편적으로는 chunk 와 pageSize 를 동일하게함
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
