package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dto.TradeDTO;
import kr.com.pkh.batch.openAPI.data.RTMSOBJSvc;
import kr.com.pkh.batch.singleton.PageSingleton;
import kr.com.pkh.batch.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.time.LocalDate;



@Slf4j
@Component
public class RestItemReader implements ItemReader<TradeDTO> {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    private boolean dataRead = false;


    private RTMSOBJSvc RTMSOBJSvc;

    public RestItemReader(RTMSOBJSvc RTMSOBJSvc) {
        this.RTMSOBJSvc = RTMSOBJSvc;
    }

    @Override
    public TradeDTO read() {

        TradeDTO tradeDTO = new TradeDTO();

        String LAWD_CD="41192";     // 부천시  (DB 조회예정)
        String DEAL_YMD="";

        PageSingleton pageSingleton = PageSingleton.getInstance();

        try{

            if(!(pageSingleton.getPageNo() <= pageSingleton.getTotalCount()+1)){
                return null;
            }

            // get deal ymd //
            LocalDate date = LocalDate.now();
            String today = StringUtil.removeAllCharsFromString(date.toString(),'-');
            DEAL_YMD = today.substring(0,today.length()-2);      // 일 데이터 제거

            log.info("DEAL_YMD (today month) : "+DEAL_YMD);

            tradeDTO = RTMSOBJSvc.getRTMSDataSvcAptTradeDev(apiKey,
                    String.valueOf(pageSingleton.getPageNo()),
                    String.valueOf(pageSingleton.getNumOfRows()),
                    LAWD_CD,
                    DEAL_YMD);

            pageSingleton.setPageNo(Integer.parseInt(tradeDTO.getPageDTO().getPageNo())+1  );
            pageSingleton.setTotalCount( Integer.parseInt(tradeDTO.getPageDTO().getTotalCount()) / pageSingleton.getNumOfRows() );


        }catch(Exception e){
            e.printStackTrace();
        }

        return tradeDTO;
    }
}