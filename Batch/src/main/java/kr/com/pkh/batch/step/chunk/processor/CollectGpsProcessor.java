package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.openAPI.naver.service.GeoCoder;
import kr.com.pkh.batch.openAPI.vworld.parser.DataSet2_BuildingUseParser;
import kr.com.pkh.batch.openAPI.vworld.service.DataSet2_BuildingUse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class CollectGpsProcessor implements ItemProcessor<List<AptBuildingDTO>, List<AptBuildingDTO>> {


    @Autowired
    GeoCoder geoCoder;

    @Autowired
    DataSet2_BuildingUseParser buildingUseParser;

    /**
     *
     * @param addressList
     * @return
     * @throws Exception
     */
    @Override
    public List<AptBuildingDTO> process(List<AptBuildingDTO> addressList) throws Exception {
        List<AptBuildingDTO> gpsList = new ArrayList<>();
        for(AptBuildingDTO item : addressList) {
            log.info("#### item   {} ", item);

            JSONObject jsonObject = (JSONObject) geoCoder.getGps(item.getAddress());

            AptBuildingDTO aptBuildingDTO = new AptBuildingDTO();
            if(jsonObject == null) {
                log.info("#### gps 정보 없음 continue");
            } else {
                // "addresses" 배열에서 첫 번째 객체 추출
                if(jsonObject.isNull("addresses")) {
                    log.info("gps 주소정보 없음 ");
                    continue;
                }
                JSONArray addressesArray = jsonObject.getJSONArray("addresses");
                if (addressesArray.length() > 0) {
                    JSONObject address = addressesArray.getJSONObject(0);

                    // "x"와 "y" 값 추출
                    String x = address.getString("x");
                    String y = address.getString("y");
                    aptBuildingDTO.setGps(y+ "," + x);
                    aptBuildingDTO.setPnu(item.getPnu());
                    aptBuildingDTO.setAddress(item.getAddress());
                    gpsList.add(aptBuildingDTO);
                } else {
                    log.info("## gps 좌표정보 없음 continue 2 ");
                }
            }

        }
        log.info("### $$$ {} ",gpsList.size() );
        return gpsList;
    }
}
