package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.api.PubuseAreaDTO;
import kr.com.pkh.batch.dto.api.PubuseAreaPageDTO;
import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import kr.com.pkh.batch.openAPI.data.service.BldRgstHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.batch.item.ItemProcessor;
import java.util.ArrayList;
import java.util.List;

/**
 * [특이사항] 아파트 단지내에 전용면적이 동일하더라도 공용면적이 다른 경우가 있음, 최초 수집된 데이터를 기준으로 공용면적을 계산함
 */
@Slf4j
@Component
public class AreaTypeProcessor implements ItemProcessor<PubuseAreaPageDTO, List<AptBuildingDTO>> {

    @Override
    public List<AptBuildingDTO> process(PubuseAreaPageDTO pubuseAreaPageDTO){

        List<AptBuildingDTO> aptBuildingList = new ArrayList<AptBuildingDTO>();

        List<PubuseAreaDTO> pubuseAreaList = pubuseAreaPageDTO.getPubuseAreaDTOList();

        float privateArea = 0;
        float publicArea=0;
        float supplyArea=0;

        try{
            log.info("process START");

            log.info("[test log] process 작업전 데이터 START");

            // test log
            for(PubuseAreaDTO pubuseAreaDTO2:pubuseAreaPageDTO.getPubuseAreaDTOList()){

                log.info("아파트명: {}, 관리건축대장 : {} , 전용면적 : {}, 공용면적 : {} ",
                        pubuseAreaDTO2.getBldNm(),
                        pubuseAreaDTO2.getMgmBldrgstPk(),
                        pubuseAreaDTO2.getPrivateArea(),
                        pubuseAreaDTO2.getPublicArea()
                );
            }
            log.info("[test log] process 작업전 데이터 END ");

            for(int loop=0;loop<pubuseAreaList.size();loop++){

                AptBuildingDTO aptBuildingDTO = new AptBuildingDTO();

                privateArea=pubuseAreaList.get(loop).getPrivateArea();
                publicArea=pubuseAreaList.get(loop).getPublicArea();
                supplyArea=privateArea+publicArea;                      // 공급면적 연산 = 전용면적 + 공용면적

                aptBuildingDTO.setPnu(pubuseAreaList.get(loop).getPnu());
                aptBuildingDTO.setName(pubuseAreaList.get(loop).getBldNm());
                aptBuildingDTO.setAddress(pubuseAreaList.get(loop).getPlatPlc());
                aptBuildingDTO.setRoadAddress(pubuseAreaList.get(loop).getNewPlatPlc());

                aptBuildingDTO.setPrivateArea(privateArea);
                aptBuildingDTO.setPublicArea(publicArea);
                aptBuildingDTO.setSupplyArea(supplyArea);
                aptBuildingDTO.setAbPnu(pubuseAreaList.get(loop).getPnu());

                // 중복된 아파트 면적 타입 필터링 // 
                // 존재하는 경우 true
                boolean exists = aptBuildingList.stream().anyMatch(obj -> Float.compare(obj.getPrivateArea(), aptBuildingDTO.getPrivateArea() ) == 0);


                // aptBuildingList 에 private area 가 존재하지 않는 경우 추가
                if(!exists){
                    aptBuildingList.add(aptBuildingDTO);

                }
            }


            log.info("process END");

        }catch(Exception e){
            e.printStackTrace();
        }

        return aptBuildingList;






    }

}
