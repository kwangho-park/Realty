package kr.com.pkh.batch.step.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AreaTypeTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception{


        // ExecutionContext : job, step 의 실행상태를 유지하고 데이터를 저장하는 객체
//        chunkContext.getStepContext().getStepExecution().getExecutionContext().put("pnu", "test");
//        String pnu = chunkContext.getStepContext().getStepExecution().getExecutionContext().getString("pnu");


        // Job Parameters 가져오기
        String pnu = chunkContext.getStepContext()
                .getJobParameters()
                .get("pnu")
                .toString();




        log.info("[AreaTypeTasklet] 아파트 전용/공용면적 수집 START");


        // pnu를 전달받아서 아파트 단지의 전용면적, 공용면적을 구함


        log.info("[AreaTypeTasklet] 아파트 전용/공용면적 수집 END");
        return RepeatStatus.FINISHED;
    }
}
