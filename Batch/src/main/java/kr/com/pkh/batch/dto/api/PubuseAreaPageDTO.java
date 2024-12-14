package kr.com.pkh.batch.dto.api;

import kr.com.pkh.batch.dto.db.PageDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 공공데이터 포털의 건물정보 조회 API를 저장하는 DTO (=xml string 의 page 단위)
 *
 */
@Slf4j
@Data
public class PubuseAreaPageDTO {
    
    // 아파트별 주소/면적 타입을 저장하는 멤버변수 
    // [참고] 수집되는 데이터는 중복된 데이터가 있으며, processor 에서 데이터 가공함
    // [참고] bldNm, platPlc, newPlatPlc, pnu 는 list 내 요소에서는 모두 동일함
    List<PubuseAreaDTO> pubuseAreaDTOList= new ArrayList<PubuseAreaDTO>();


    PageDTO pageDTO;


// pubuseAreaDTOList 내에 건축물대장정보 (mgmBldrgstPk) 존재여부 반환
    public boolean isMgmBidrgstPk(String mgmBldrgstPk){
        boolean result=false;

        try{

            for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
                if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){
                    result=true;
                }
            }
        }catch(NullPointerException e){
            return result;
        }

        return result;
    }


    // pubuseAreaDTOList 에 면적 데이터 업데이트 (기준데이터 : mgmBldrgstPk)
    public void updatePublicArea(String bldNm, String mgmBldrgstPk, float publicArea){
        for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
            if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){
                float updatePublicArea = pubuseAreaDTO.getPublicArea()+publicArea;
                log.info("[아파트 공용면적 업데이트] 아파트명 : {} ,mgmBldrgstPk : {} , as-is : {}, to-be : {}", bldNm, mgmBldrgstPk, pubuseAreaDTO.getPublicArea(), updatePublicArea);
                pubuseAreaDTO.setPublicArea(updatePublicArea);

            }
        }
    }

    public void updatePrivateArea(String bldNm, String mgmBldrgstPk, float privateArea){

        for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
            if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){
                float updatePrivateArea = pubuseAreaDTO.getPrivateArea()+privateArea;
                log.info("[아파트 전용면적 업데이트] 아파트명 : {} , mgmBldrgstPk : {} , as-is : {}, to-be : {}", bldNm, mgmBldrgstPk, pubuseAreaDTO.getPublicArea(), updatePrivateArea);
                pubuseAreaDTO.setPrivateArea(updatePrivateArea);

            }
        }
    }

    /**
     * API : http://apis.data.go.kr/1613000/BldRgstHubService/getBrExposPubuseAreaInfo
     * API 로 조회되는 아파트별 타입에서 '전용면적' 의 item 은 1개만 존재한다는 판단하에 구현한 함수 (검증필요)
     * @param mgmBldrgstPk
     * @return
     */
    public String getSettingAreaType(String mgmBldrgstPk){
        String result=null;
        for(PubuseAreaDTO pubuseAreaDTO:this.pubuseAreaDTOList){
            if(mgmBldrgstPk.equals(pubuseAreaDTO.getMgmBldrgstPk()) ){
                
                if(pubuseAreaDTO.getPrivateArea() == 0){
                    result = "privateArea";     // privateArea 가 존재하지않는 경우 item 에서 조회된 area 를 privateArea 에 설정해야함 
                }else{
                    result = "publicArea";
                }
            }
        }
        return result;
    }



}
