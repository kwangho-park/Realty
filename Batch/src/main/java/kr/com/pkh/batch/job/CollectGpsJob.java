package kr.com.pkh.batch.job;

import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import kr.com.pkh.batch.step.chunk.reader.CollectGpsReader;
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
 * tb_apt_building 주소 기준으로 GPS 데이터 수집
 *
 *
 * run : --spring.batch.job.names=collectGpsJop
 *
 * 출처 : 네이버 클라우드
 */
@Configuration
@RequiredArgsConstructor
public class CollectGpsJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job collectGpsJop(Step collectGpsStep){
        return jobBuilderFactory.get("collectGpsJop")
                .incrementer(new RunIdIncrementer())
                .start(collectGpsStep)
                .build();
    }

    @JobScope
    @Bean
    public Step collectGpsStep(ItemReader collectGpsReader,
                               ItemProcessor collectGpsProcessor,
                               ItemWriter collectGpsWriter){

        return stepBuilderFactory.get("collectGpsStep")
                .<List<AptBuildingDTO>, List<AptBuildingDTO>>chunk(1)
                .reader(collectGpsReader)
                .processor(collectGpsProcessor)
                .writer(collectGpsWriter)
                .build();
    }

}
