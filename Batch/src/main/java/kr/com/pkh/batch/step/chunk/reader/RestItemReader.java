package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dto.AptTradeDTO;
import kr.com.pkh.batch.openAPI.data.RTMSOBJSvc;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


public class RestItemReader implements ItemReader<List<String>> {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    private boolean dataRead = false;


    private RTMSOBJSvc RTMSOBJSvc;

    public RestItemReader(RTMSOBJSvc RTMSOBJSvc) {
        this.RTMSOBJSvc = RTMSOBJSvc;
    }

    @Override
    public List<String> read() throws Exception {

        // reader 가 무한으로 실행되는것을 방지하기 위해 return null 반환 조건 추가
        if(dataRead){
            return null;
        }

        List<String> aptTradeList = new ArrayList<String>();


         aptTradeList = RTMSOBJSvc.getRTMSDataSvcAptTradeDev(apiKey,"","","41192","202312");

        if(aptTradeList ==null || aptTradeList.isEmpty()){
            return null;
        }

        dataRead=true;

        return aptTradeList;

    }
}