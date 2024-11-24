package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.openAPI.vworld.parser.DataSet2_BuildingUseParser;
import kr.com.pkh.batch.openAPI.vworld.service.DataSet2_BuildingUse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONArray;
import org.json.JSONObject;


@Slf4j
@Component
public class AptAddressProcessor  implements ItemProcessor<AptTradeDTO, AptBuildingDTO> {


    @Autowired
    DataSet2_BuildingUse buildingUse;

    @Autowired
    DataSet2_BuildingUseParser buildingUseParser;

    /**
     *
     * @param item
     * @return
     * @throws Exception
     */
    @Override
    public AptBuildingDTO process(AptTradeDTO item) throws Exception {
        log.info("#### item   {} ", item);
        JSONObject jsonObject = (JSONObject) buildingUse.getBuildingUse(String.valueOf(item.getPnu()));
        AptBuildingDTO aptBuildingDTO = new AptBuildingDTO();
        if(jsonObject == null) {
            log.info("#### 주소 정보 없음 continue");
        } else {
            // "buildingUses" 키에 해당하는 JSONObject 가져오기
            if(jsonObject.isNull("buildingUses")) {
                log.info("## 주소정보 없음 continue 2 ");
            } else {
                aptBuildingDTO = buildingUseParser.parseBuildingUse(jsonObject.getJSONObject("buildingUses"));
                aptBuildingDTO.setPnu(item.getPnu());
            }
        }
        return aptBuildingDTO;
    }
}
