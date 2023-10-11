package kr.com.pkh.realty.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_APP_INFO")
public class AppInfoEntity {

    @Id
    @Column(name="AI_ID", length = 36)
    private int id;

    @Column(name ="AI_NAME" ,length = 45)
    private String appName;

    @Column(name ="AI_PROXY_IP" ,length = 45)
    private String appIp;

    @Column(name ="AI_PROXY_PORT" ,length = 10)
    private int appPort;
	
    @Column(name ="AI_PROTOCOL" ,length = 45)
    private String appProtocol;
    
    @Column(name ="AI_URI" ,length = 1024)
    private String appURI;
    
    @Column(name ="AI_DESCRIPTION" ,length = 45)
    private String appDescription;
	
}
