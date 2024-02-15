

ALTER TABLE realty.tb_apt_trade CHANGE AT_TRADE_DATETIME AT_TRADE_DATE varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL COMMENT '거래일자';

-- 검색 성능을 위한 index 속성 추가 
alter table realty.tb_apt_trade add index idx(AT_PNU, AT_TRADE_DATE);