package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.basic.Person;
import kr.com.pkh.batch.openAPI.vworld.DataSet2_BuildingUse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


@Slf4j
@Component
public class AptAddressProcessor  implements ItemProcessor<AptTradeDTO, AptTradeDTO> {


    @Autowired
    DataSet2_BuildingUse buildingUse;


    @Override
    public AptTradeDTO process(AptTradeDTO item) throws Exception {
        log.info("#### item   {} ", item);
        JSONObject jsonObject = (JSONObject) buildingUse.getBuildingUse(String.valueOf(item.getPnu()));

        if(jsonObject == null) {
            log.info("#### 주소 정보 없음 continue");
        } else {
            // "buildingUses" 키에 해당하는 JSONObject 가져오기
            if(jsonObject.isNull("buildingUses")) {
                log.info("## 주소정보 없음 continue 2 ");
            } else {

                // [review] processor 가 아닌 DataSet2_BuildingUse.java 에서 구현하거나 별도로 파싱하는 클래스에 작성해야 코드 관리가 용이할것으로 판단됨
                JSONObject buildingUses = jsonObject.getJSONObject("buildingUses");

                // "field" 키에 해당하는 JSONArray 가져오기
                JSONArray fieldArray = buildingUses.getJSONArray("field");

                // 첫 번째 요소인 JSONObject 가져오기
                JSONObject firstField = fieldArray.getJSONObject(0);
                // "mnnmSlno" 키에 해당하는 값 가져오기
                String mnnmSlnoValue = firstField.getString("mnnmSlno");

                String ldCodeNmValue = firstField.getString("ldCodeNm");
                log.info("jsonObject {} " , jsonObject.get("buildingUses"));
                item.setAddress( ldCodeNmValue + " " + mnnmSlnoValue);
                log.info("getAddress :: {} ", item.getAddress());
            }

            // aptTradeDTO.setId((String) obj[0]);
        }

        return item;
    }
}
