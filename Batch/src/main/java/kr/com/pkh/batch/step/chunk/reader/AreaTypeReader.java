package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.AreaTypeDAO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AreaTypeReader implements ItemReader<List<String>> {

    private int count =0;
    private AreaTypeDAO areaTypeStepDAO;

    @Autowired
    public AreaTypeReader(AreaTypeDAO areaTypeStepDAO){
        this.areaTypeStepDAO=areaTypeStepDAO;
    }

    public int getCount() {
        return this.count;
    }
    @Override
    public List<String> read() {


        List<String>  pnuList = new ArrayList<String>();

        try{

            log.info("read START");
            pnuList = areaTypeStepDAO.selectAreaTypeList();

            // job 종료 지점설정 (1회 reader 실행 후 collectAreaTypeJob 을 종료)
            if(count==1){
                return null;
            }

            count++;
            for(String test:pnuList){
                log.info("pnu:"+test);
            }



        }catch(Exception e){
            e.printStackTrace();
        }


        return pnuList;
    }

}
