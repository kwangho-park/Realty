package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.AptBuildingDAO;
import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CollectGpsReader implements ItemReader<List<AptBuildingDTO>> {

    private int count =0;
    private AptBuildingDAO aptBuildingDAO;

    @Autowired
    public CollectGpsReader(AptBuildingDAO aptBuildingDAO){
        this.aptBuildingDAO=aptBuildingDAO;
    }

    public int getCount() {
        return this.count;
    }
    @Override
    public List<AptBuildingDTO> read() {


        List<AptBuildingDTO>  aptBuildingList = new ArrayList<AptBuildingDTO>();

        try{

            log.info("read START");
            aptBuildingList = aptBuildingDAO.selectBuildingAddressGpsNull();

            // job 종료 지점설정 (1회 reader 실행 후 collectAreaTypeJob 을 종료)
            if(count==1){
                return null;
            }

            count++;
            for(AptBuildingDTO aptBuild : aptBuildingList){
                log.info("pnu:"+aptBuild.getAddress());
            }



        }catch(Exception e){
            e.printStackTrace();
        }


        return aptBuildingList;
    }

}
