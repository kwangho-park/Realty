package kr.com.pkh.batch.job;


import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dto.api.TradeDTO;
import kr.com.pkh.batch.openAPI.data.legacy.RTMSOBJSvc;
import kr.com.pkh.batch.step.chunk.processor.AptAddressProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * run : --spring.batch.job.names=collectRealtyJob
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CollectJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RTMSOBJSvc RTMSOBJSvc;

    @Bean
    public Job collectRealtyJob(Step aptTradeStep){
        return jobBuilderFactory.get("collectRealtyJob")        // job 이름 정의
                .incrementer(new RunIdIncrementer())
                .start(aptTradeStep)
               // .next(aptAddressStep)                         // 주소 가져오는 step (github issue #8)
                .build();
    }



    // spring bean 으로 등록해놓은 reader, processor, writer 객체를 호출하여 사용
    // Trade => Trans 로 명칭변경예정 (소스내 함수명 ,db table 명 등 )
    @JobScope
    @Bean
    public Step aptTradeStep(ItemReader restItemReader,
                             ItemProcessor aptTradeProcessor,
                             ItemWriter aptTradeWriter
        ){
        return stepBuilderFactory.get("aptTradeStep")
                .<TradeDTO, List<AptTradeDTO>>chunk(3)   // TradeDTO 조회(reader DTO) 하여, List<AptTradeEntity> (writer DTO) 로 데이터를 추가하며, 5개 row 단위로  step 을 트랜잭션
                .reader(restItemReader)                       // reader 지정
                .processor(aptTradeProcessor)                 // processor 지정
                .writer(aptTradeWriter)                       // writer 지정
                .build();
    }

    
    /**
     * gitHub realty project의 issue #8참고
     * mybatis 변경대상 소스
     * v-world api가 아닌 공공데이터포털의 건축물대상정보 open API로 아파트 주소 데이터를 수집하는것으로 변경 필요
     * 건축물대장정보 API : https://www.data.go.kr/data/15044713/openapi.do
     *
     * @return
     */
    @JobScope
    @Bean
    public Step aptAddressStep(ItemReader aptAddressReader,
                               AptAddressProcessor addressProcessor,
                               ItemWriter aptAddressWriter
    ){
        return stepBuilderFactory.get("aptAddressStep")
                .<List<AptTradeDTO>, AptTradeDTO>chunk(10)
                .reader(aptAddressReader)         // ??
                .processor(addressProcessor)      // ??
                .writer(aptAddressWriter)
                .build();
    }

}
