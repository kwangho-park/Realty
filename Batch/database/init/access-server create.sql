

create database access_server;

-- 임시 테이블로써 향후 Manager 에서 인증받은 정보로 로그인 처리하는 것으로 변경예정
CREATE TABLE IF NOT EXISTS access_server.TB_USER_INFO (
    `UI_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `UI_USER_ID` varchar(20) DEFAULT NULL COMMENT '사용자 ID',
    `UI_USER_PW` varchar(20) NOT NULL COMMENT '사용자 PW',
    `UI_USER_NAME` varchar(50) NOT NULL COMMENT '사용자명',
    PRIMARY KEY (`UI_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 정보 테이블 (Manager를 통해 인증 받은 정보)'




CREATE TABLE IF NOT EXISTS access_server.TB_APP_INFO (
  AI_ID VARCHAR(36) NOT NULL,
  AI_NAME VARCHAR(45) NULL,
  AI_PROXY_IP VARCHAR(45) NULL COMMENT '프록시 서버  IP',
  AI_PROXY_PORT INT(10) NULL,
  AI_PROTOCOL VARCHAR(45) NULL,
  AI_URI VARCHAR(1024) NULL COMMENT '어플리케이션 접속 URI (IP/port 제외)',
  AI_DESCRIPTION VARCHAR(45) NULL,
  PRIMARY KEY (AI_ID))
ENGINE = InnoDB;
