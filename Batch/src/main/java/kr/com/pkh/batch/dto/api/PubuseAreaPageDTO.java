package kr.com.pkh.batch.dto.api;

import kr.com.pkh.batch.dto.db.PageDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 공공데이터 포털의 건물정보 조회 API를 저장하는 DTO (=xml string 의 page 단위)
 *
 */
@Slf4j
@Data
public class PubuseAreaPageDTO {

    List<PubuseAreaDTO> pubuseAreaDTOList;  // [참고] bldNm, platPlc, newPlatPlc, pnu 는 list 내 요소에서는 모두 동일함

//    String bldNm;           // 아파트명
//    String platPlc;         // 구주소
//    String newPlatPlc;      // 도로명 주소
//    String pnu;

    PageDTO pageDTO;


// pubuseAreaDTOList 내에 건축물대장정보 (mgmBldrgstPk) 존재여부 반환
    public boolean isMgmBidrgstPk(String mgmBldrgstPk){
        boolean result=false;
        for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
            if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){
                result=true;
            }
        }
        return result;
    }


    // pubuseAreaDTOList 에 면적 데이터 업데이트 (기준데이터 : mgmBldrgstPk)
    public void updatePublicArea(String mgmBldrgstPk, String publicArea){
        for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
            if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){

                String updatePublicArea = pubuseAreaDTO.getPublicArea()+publicArea;
                log.info("[아파트 공용면적 업데이트] mgmBldrgstPk : {} , as-is : {}, to-be : {}", mgmBldrgstPk, pubuseAreaDTO.getPublicArea(), updatePublicArea);
                pubuseAreaDTO.setPublicArea(updatePublicArea);

            }
        }
    }

    public void updatePrivateArea(String mgmBldrgstPk, String privateArea){
        for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
            if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){

                String updatePrivateArea = pubuseAreaDTO.getPrivateArea()+privateArea;
                log.info("[아파트 전용면적 업데이트] mgmBldrgstPk : {} , as-is : {}, to-be : {}", mgmBldrgstPk, pubuseAreaDTO.getPublicArea(), updatePrivateArea);
                pubuseAreaDTO.setPrivateArea(updatePrivateArea);

            }
        }
    }


    public String getAreaType(String mgmBldrgstPk){
        String result=null;
        for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
            if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){


                if(pubuseAreaDTO.getPrivateArea().isEmpty()||pubuseAreaDTO.getPrivateArea().equals("")){
                    result = "privateArea";
                }else{
                    result = "publicArea";
                }
            }
        }
        return result;
    }



}
