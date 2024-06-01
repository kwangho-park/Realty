
-- tb_apt_trade--
ALTER TABLE realty.tb_apt_trade CHANGE AT_TRADE_DATETIME AT_TRADE_DATE varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL COMMENT '거래일자';

-- 검색 성능을 위한 index 속성 추가 
alter table realty.tb_apt_trade add index idx(AT_PNU, AT_TRADE_DATE);

-- 아파트 주소 칼럼 추가
-- [고도화] 로그성 데이터에 불필요한 데이터로 판단되어 추후 TB_APT_BUILDING 테이블의 AB_ADDRESS 로 대체 예정임
ALTER TABLE realty.tb_apt_trade ADD AT_ADDRESS varchar(70) NULL COMMENT '아파트 단지 주소';


-- tb_region_code --
ALTER TABLE realty.tb_region_code CHANGE RC_ADPT_DATE RC_INSERT_DATE DATETIME NULL COMMENT '생성일';
-- region code 가 10자리 임으로 int(11) 타입으로 저장 시 Out of range 발생하여 BIGINT 타입으로 변경 
ALTER TABLE realty.tb_region_code MODIFY COLUMN RC_REGION_CODE BIGINT(20) NULL COMMENT '지역코드 (10자리)';
-- [테이블 삭제 및 생성] 디폴트 값 추가 및 index 변경 
drop table `realty`.`TB_REGION_CODE`;
CREATE TABLE IF NOT EXISTS `realty`.`TB_REGION_CODE` (
  `RC_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `RC_LOCATADD_NAME` VARCHAR(45) NULL DEFAULT '' COMMENT '지역주소명',
  `RC_REGION_CODE` BIGINT(20) NULL DEFAULT 0 COMMENT '지역코드 (10자리)',
  `RC_SIDO_CODE` INT(2) UNSIGNED NULL DEFAULT 0 COMMENT '시도코드 (2자리)',
  `RC_SGG_CODE` INT(3) UNSIGNED NULL DEFAULT 0 COMMENT '시군구코드 (3자리) ',
  `RC_UMD_CODE` INT(3) UNSIGNED NULL DEFAULT 0 COMMENT '읍면동 코드 (3자리) ',
  `RC_RI_CODE` INT(2) UNSIGNED NULL DEFAULT 0 COMMENT '리 코드 (2자리)',
  `RC_LOCATJUMIN_CODE` VARCHAR(45) NULL DEFAULT '' COMMENT '지역코드_주민',
  `RC_LOCATJIJUK_CODE` VARCHAR(45) NULL DEFAULT '' COMMENT '지역코드_지적',
  `RC_LOCAT_ORDER` VARCHAR(45) NULL DEFAULT '' COMMENT '서열',
  `RC_LOCATE_RM` VARCHAR(45) NULL DEFAULT '' COMMENT '비고',
  `RC_LOCATHIGH_CODE` VARCHAR(45) NULL DEFAULT '' COMMENT '상위지역코드',
  `RC_LOCALLOW_NAME` VARCHAR(45) NULL DEFAULT '' COMMENT '하위지역명',
  `RC_INSERT_DATETIME` DATETIME NULL COMMENT '데이터 생성일자',
  PRIMARY KEY (`RC_ID`),
  INDEX `REGION_CODE_UNIQUE` (`RC_REGION_CODE` ASC) VISIBLE,
  INDEX `SIDO_CODE_UNIQUE` (`RC_SIDO_CODE` ASC) VISIBLE,
  INDEX `SGG_CODE_UNIQUE` (`RC_SGG_CODE` ASC) VISIBLE,
  INDEX `UMD_CODE_UNIQUE` (`RC_UMD_CODE` ASC) VISIBLE,
  INDEX `RI_CODE_UNIQUE` (`RC_RI_CODE` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = '법정동 코드 테이블';


-- tb_realty_info -- 
ALTER TABLE realty.tb_realty_info CHANGE RI_INSERT_DATE RI_INSERT_DATETIME datetime NULL COMMENT '등록일자';
ALTER TABLE realty.tb_realty_info CHANGE RI_UPDATE_DATE RI_UPDATE_DATETIME datetime NULL COMMENT '수정일자';



-- 사용자 로그를 위한 테이블 추가 
CREATE TABLE IF NOT EXISTS realty.TB_USER_LOG (
  `UL_ID` INT NOT NULL AUTO_INCREMENT,
  `UL_USER_ID` VARCHAR(45) NULL,
  `UL_USER_NAME` VARCHAR(50) NULL COMMENT '사용자명',
  `UL_ACTION` VARCHAR(10) NULL COMMENT '로그인(LOGIN), 로그아웃(LOGOUT), 조회(S), 생성(C), 수정 (U), 삭제 (D)',
  `UL_DATETIME` DATETIME NULL,
  PRIMARY KEY (`UL_ID`))
ENGINE = InnoDB
COMMENT = '사용자 로그 테이블';

-- 테스트용 테이블 제거 
drop table accounts;
drop table orders;


-- 아파트 건물정보 테이블에서 관리하는것으로 변경하여 제거 (TB_APT_BUILDING)
ALTER TABLE realty.tb_realty_info DROP COLUMN RI_GPS;
ALTER TABLE realty.tb_realty_info DROP COLUMN RI_GIS;

