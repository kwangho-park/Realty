package kr.com.pkh.realty.dto.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class AppInfoDTO {

    private int id;
    private String appName;
    private String appIp;
    private int appPort;
    private String appProtocol;
    private String appURI;
    private String appDescription;
	
}
