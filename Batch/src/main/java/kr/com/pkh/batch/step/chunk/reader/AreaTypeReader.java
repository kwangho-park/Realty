package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.AptTradeDAO;
import kr.com.pkh.batch.dao.AreaTypeDAO;
import kr.com.pkh.batch.dto.api.PubuseAreaPageDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import kr.com.pkh.batch.exception.CustomException;
import kr.com.pkh.batch.openAPI.data.service.BldRgstHubService;
import kr.com.pkh.batch.singleton.AreaTypeScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AreaTypeReader implements ItemReader<List<String>> {

    private int count =0;
    private AreaTypeDAO areaTypeStepDAO;

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    private BldRgstHubService bldRgstHubService;

    private AptTradeDAO aptTradeDAO;

    @Autowired
    public AreaTypeReader(AreaTypeDAO areaTypeStepDAO, BldRgstHubService bldRgstHubService, AptTradeDAO aptTradeDAO){
        this.areaTypeStepDAO=areaTypeStepDAO;
        this.bldRgstHubService=bldRgstHubService;
        this.aptTradeDAO=aptTradeDAO;
    }

    public int getCount() {
        return this.count;
    }
    @Override
    public List<String> read() {

        log.info("[read] START");

        String serviceKey = this.apiKey;


        // TEST
        String pageNo="1";
        String numOfRows="100";
        String sigunguCd ="41192";      // 시군구코드(5자리) (필수) : 41192 (경기도 부천시 원미구)
        String bjdongCd ="10800";       // 법정동코드(5자리)  (필수) : 10800 (중동)
        String platGbCd="0";            // 토지구분 (1자리) : 0
        String bun="1051";              // 본번 (4자리) : 1051  (10510000 설악마을아파트 지번)
        String ji="0000";               // 지번 (4자리) : 0000

        // test (제거예정)
        List<String>  pnuList2 = new ArrayList<String>();

        PubuseAreaPageDTO pubuseAreaPageDTO;

        AreaTypeScope areaTypeScope = AreaTypeScope.getInstance();
        AptTradeDTO aptTradeDTO;

        try{



            // validation in properties //
            if(areaTypeScope.isScopeFlag()){
                this.initScope();
            }


            // reader 동작제어 //
            // reader 종료 시점 (=job 종료시점) : 전체 페이지 수집완료 -> 전체 pnu 코드 순환 완료
            if(areaTypeScope.getPnuListSize() < areaTypeScope.getPunSeq()){
                log.info("[read] 부동산 주소 및 면적타입 데이터 수집완료");
                return null;
            }

            // reader 반복 종료 시점 : 전체 xml 페이지 순환 완료
            if(areaTypeScope.getTotalPage() < areaTypeScope.getPageNo() &&
                    !(areaTypeScope.getTotalPage()==0 )){

                // pageNo, totalPage 초기화
                areaTypeScope.initPageNo();
                areaTypeScope.initTotalPage();

                // pnu 시퀀스 증가
                areaTypeScope.incrementPnuSeq();

//                throws new CustomException();
                throw new Exception("test");
            }



            // pnu 기준으로 아파트 주소 및 아파트 면적타입 조회
            // pubuseAreaPageDTO 내의 pageDTO (pageNo, numOfRows, totalCount) 로 배치 job 의 종료시점 제어
            areaTypeScope.getPageNo();
            areaTypeScope.getNumOfRows();

            aptTradeDTO = areaTypeScope.getPnuList().get(areaTypeScope.getPunSeq()-1);

            aptTradeDTO.getSigunCd();
            aptTradeDTO.getBjdCd();
            aptTradeDTO.getPlatCd();
            aptTradeDTO.getBunCd();
            aptTradeDTO.getJiCd();

            pubuseAreaPageDTO = bldRgstHubService.getBrExposPubuseAreaInfo(
                    serviceKey,
                    String.valueOf(areaTypeScope.getPageNo()),
                    String.valueOf(areaTypeScope.getNumOfRows()),
                    aptTradeDTO.getSigunCd(),
                    aptTradeDTO.getBjdCd(),
                    aptTradeDTO.getPlatCd(),
                    aptTradeDTO.getBunCd(),
                    aptTradeDTO.getJiCd());


            areaTypeScope.incrementPageNo();
            areaTypeScope.updateTotalPage(pubuseAreaPageDTO.getPageDTO().getTotalCount(),
                    areaTypeScope.getNumOfRows() );

            log.info("scope page no : "+ areaTypeScope.getPageNo());
            log.info("scope total page : "+ areaTypeScope.getTotalPage() );


        }catch(Exception e){
            e.printStackTrace();
        }

        log.info("[read] END");
        return pnuList2;
    }

    public void initScope() throws Exception {

        AreaTypeScope areaTypeScope = AreaTypeScope.getInstance();
        List<AptTradeDTO> pnuList = new ArrayList<AptTradeDTO>();

        // xml page scope //
        areaTypeScope.setScopeFlag(false);
        areaTypeScope.setPageNo(1);
        areaTypeScope.setNumOfRows(10);
        areaTypeScope.setTotalPage(0);

        // pnu 리스트 scope //
        pnuList = aptTradeDAO.selectPnuList();

        areaTypeScope.setPnuList(pnuList);
    }


}
