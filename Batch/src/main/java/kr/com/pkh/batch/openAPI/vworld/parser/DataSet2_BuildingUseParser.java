package kr.com.pkh.batch.openAPI.vworld.parser;

import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataSet2_BuildingUseParser {

    public AptBuildingDTO parseBuildingUse(JSONObject jsonObject) throws Exception {
        AptBuildingDTO aptBuildingDTO = new AptBuildingDTO();
        // "field" 키에 해당하는 JSONArray 가져오기
        JSONArray fieldArray = jsonObject.getJSONArray("field");

        // 첫 번째 요소인 JSONObject 가져오기
        org.json.JSONObject firstField = fieldArray.getJSONObject(0);
        // "mnnmSlno" 키에 해당하는 값 가져오기
        String mnnmSlnoValue = firstField.getString("mnnmSlno");

        String ldCodeNmValue = firstField.getString("ldCodeNm");

        aptBuildingDTO.setAddress( ldCodeNmValue + " " + mnnmSlnoValue);

        log.info("getAddress :: {} ", aptBuildingDTO.getAddress());
        return aptBuildingDTO;
    }

}
