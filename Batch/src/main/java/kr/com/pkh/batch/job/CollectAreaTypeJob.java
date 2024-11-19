package kr.com.pkh.batch.job;

import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import lombok.RequiredArgsConstructor;
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
 * 실거래 데이터의 아파트별 (pnu기준) 면적을 수집하는 job
 * 수집 기준 : 실거래 테이블 (tb_apt_trade)과 건물정보테이블 (tb_apt_building) 에 모두 pnu 가 존재하는 경우
 * 타입 : 전용면적, 공용면적, 공급면적, 계약면적
 *
 * run : --spring.batch.job.names=collectAreaTypeJob
 *
 * 출처 : 공공데이터포털, V-world
 */
@Configuration
@RequiredArgsConstructor
public class CollectAreaTypeJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job collectBuildingJob(Step areaTypeStep){
        return jobBuilderFactory.get("collectAreaTypeJob")
                .incrementer(new RunIdIncrementer())
                .start(areaTypeStep)
                .build();
    }

    @JobScope
    @Bean
    public Step areaTypeStep(ItemReader areaTypeReader,
                             ItemProcessor areaTypeProcessor,
                             ItemWriter areaTypeWriter){

        return stepBuilderFactory.get("areaTypeStep")
                .<List<String>, List<AreaTypeDTO>>chunk(1)
                .reader(areaTypeReader)
                .processor(areaTypeProcessor)
                .writer(areaTypeWriter)
                .build();
    }

}
