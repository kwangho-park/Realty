package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dto.AptTradeDTO;
import kr.com.pkh.batch.openAPI.data.RTMSOBJSvc;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class RestItemReader implements ItemReader<List<String> > {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    RTMSOBJSvc RTMSOBJSvc;

    public RestItemReader(RTMSOBJSvc RTMSOBJSvc) {
        this.RTMSOBJSvc = RTMSOBJSvc;
    }

    @Override
    public List<String> read() throws Exception {

        List<String> aptTradeList = new ArrayList<String>();

        // 상세 파싱로직 구현예정
        aptTradeList = RTMSOBJSvc.getRTMSDataSvcAptTradeDev(apiKey,"","","41192","202312");

        return aptTradeList;
    }
}