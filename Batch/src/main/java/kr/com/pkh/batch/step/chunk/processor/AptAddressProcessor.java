package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.basic.Person;
import kr.com.pkh.batch.dto.AptTradeDTO;
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

    /*@Override
    public AptTradeDTO process(Object[] obj) {

        AptTradeDTO  aptTradeDTO = setParam(obj);
        try{
                log.info("obj :: " );
             JSONObject jsonObject = (JSONObject) buildingUse.getBuildingUse(String.valueOf(aptTradeDTO.getPnu()));
            //JSONObject jsonObject = new JSONObject();


           if(jsonObject == null) {
               log.info("#### 주소 정보 없음 continue");
           } else {
               // "buildingUses" 키에 해당하는 JSONObject 가져오기
               if(jsonObject.isNull("buildingUses")) {
                   log.info("## 주소정보 없음 continue 2 ");
               } else {
                   JSONObject buildingUses = jsonObject.getJSONObject("buildingUses");

                   // "field" 키에 해당하는 JSONArray 가져오기
                   JSONArray fieldArray = buildingUses.getJSONArray("field");

                   // 첫 번째 요소인 JSONObject 가져오기
                   JSONObject firstField = fieldArray.getJSONObject(0);
                   // "mnnmSlno" 키에 해당하는 값 가져오기
                   String mnnmSlnoValue = firstField.getString("mnnmSlno");

                   String ldCodeNmValue = firstField.getString("ldCodeNm");
                   log.info("jsonObject {} " , jsonObject.get("buildingUses"));
                   aptTradeDTO.setAddress( ldCodeNmValue + " " + mnnmSlnoValue);
                    log.info("getAddress :: {} ", aptTradeDTO.getAddress());
               }

              // aptTradeDTO.setId((String) obj[0]);
           }



           /// ??
       *//*     // 결과값이 존재하지 않는
            if(aptTradeList==null){s
                return null;
            }
            for(int loop=0;loop<aptTradeList.size();loop++){

                AptTradeEntity aptTradeEntity = new AptTradeEntity();

                aptTradeEntity.setId(aptTradeList.get(loop).getId());
                aptTradeEntity.setPnu(aptTradeList.get(loop).getPnu());
                aptTradeEntity.setName(aptTradeList.get(loop).getName());
                aptTradeEntity.setTradeAmount(aptTradeList.get(loop).getTradeAmount());
                aptTradeEntity.setTradeDate(aptTradeList.get(loop).getTradeDate());
                aptTradeEntity.setInsertDateTime(LocalDateTime.now());
                aptTradeEntityList.add(aptTradeEntity);*//*


            //}


        }catch(Exception e){
            e.printStackTrace();
        }

        return aptTradeDTO;
        //return null;

    }
*/
    // test
    private AptTradeDTO setParam(Object[] obj) {
        log.info("obj ###" + obj[0]);
        log.info("obj ###" + obj[1]);
        log.info("obj ###" + obj[2]);log.info("obj ###" + obj[3]);log.info("obj ###" + obj[4]);
        log.info("obj ###" + obj[5]);

        AptTradeDTO aptTradeDTO = new AptTradeDTO();

        aptTradeDTO.setId((String) obj[0]);
        aptTradeDTO.setPnu((String) obj[1]);
        aptTradeDTO.setName((String) obj[2]);
        aptTradeDTO.setTradeAmount((int) obj[3]);
        aptTradeDTO.setTradeDate((String) obj[4]);
        aptTradeDTO.setInsertDateTime((String) obj[5]);
        return  aptTradeDTO;
    }

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


    // original
//    private AptTradeEntity setParam(Object[] obj) {
//        log.info("obj ###" + obj[0]);
//        log.info("obj ###" + obj[1]);
//        log.info("obj ###" + obj[2]);log.info("obj ###" + obj[3]);log.info("obj ###" + obj[4]);
//        log.info("obj ###" + obj[5]);
//
//
//        AptTradeEntity  atEntity = new AptTradeEntity();
//        atEntity.setId((String) obj[0]);
//        atEntity.setPnu((long) obj[1]);
//        atEntity.setName((String) obj[2]);
//        atEntity.setTradeAmount((int) obj[3]);
//        atEntity.setTradeDate((String) obj[4]);
//        atEntity.setInsertDateTime((LocalDateTime) obj[5]);
//        return  atEntity;
//    }
}
