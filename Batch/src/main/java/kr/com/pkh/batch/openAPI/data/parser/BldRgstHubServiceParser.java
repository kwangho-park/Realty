package kr.com.pkh.batch.openAPI.data.parser;


import kr.com.pkh.batch.dto.api.PubuseAreaDTO;
import kr.com.pkh.batch.dto.api.PubuseAreaPageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BldRgstHubServiceParser {

    public PubuseAreaPageDTO xmlParsingToObject(String responseXml) throws Exception{

        PubuseAreaPageDTO pubuseAreaPageDTO = new PubuseAreaPageDTO();

        return pubuseAreaPageDTO;
    }



}
