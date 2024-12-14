package kr.com.pkh.batch.step.chunk.writer;

import kr.com.pkh.batch.dao.AptBuildingDAO;
import kr.com.pkh.batch.dao.AreaTypeDAO;
import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.singleton.AreaTypeScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.batch.item.ItemWriter;

@Slf4j
@Component
public class AreaTypeWriter implements ItemWriter<List<AptBuildingDTO>> {



    private AreaTypeDAO areaTypeDAO;
    private AptBuildingDAO aptBuildingDAO;

    @Autowired
    public AreaTypeWriter(AreaTypeDAO areaTypeDAO, AptBuildingDAO aptBuildingDAO){
        this.areaTypeDAO=areaTypeDAO;
        this.aptBuildingDAO=aptBuildingDAO;
    }


    @Override
    public void write(List<? extends List<AptBuildingDTO>> aptBuildingDTOList){
        log.info("writer START");
        List<AptBuildingDTO> aptBuildingList = aptBuildingDTOList.get(0);
        AreaTypeScope areaTypeScope = AreaTypeScope.getInstance();

        try{

            // 아파트 주소는 pnu로 조회한 모든 페이지를 통틀어서 1회 인서트해야함 (tb_apt_building.ab_pnu primary key로 중복불가능하기떄문임)
            if(areaTypeScope.isAddressFlag()){
                aptBuildingDAO.insertAptAddress(aptBuildingList.get(0));
                areaTypeScope.setAddressFlag(false);
            }


            for(int loop=0;loop<aptBuildingList.size();loop++){

                log.info("pnu : {} , 아파트명 : {} , privateArea (전용면적) : {}, publicArea (공용면적) : {}, supplyArea (공급면적) : {} ",
                        aptBuildingList.get(loop).getPnu(),
                        aptBuildingList.get(loop).getName(),
                        aptBuildingList.get(loop).getPrivateArea(),
                        aptBuildingList.get(loop).getPublicArea(),
                        aptBuildingList.get(loop).getSupplyArea()
                );

                // 인서트시 중복된 면적타입 필터링 필요
                areaTypeDAO.insertAreaType(aptBuildingList.get(loop));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        log.info("writer END");

    }


}
