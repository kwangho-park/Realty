
-- 아파트 단지별 평단가 구현용 더미데이터 
insert into tb_apt_building(AB_PNU, AB_NAME, AB_ADDRESS, AB_ROAD_ADDRESS) values('4119210800110510000','설악마을아파트(설악단지)','경기도 부천시 원미구 중동 1051번지','경기도 부천시 원미구 계남로 195');
insert into tb_apt_building(AB_PNU, AB_NAME, AB_ADDRESS, AB_ROAD_ADDRESS) values('4119210800113000000','센트럴파크푸르지오','','부천시 원미구 소향로 181');
insert into tb_apt_building(AB_PNU, AB_NAME, AB_ADDRESS, AB_ROAD_ADDRESS) values('4119210800113010000','힐스테이트중동','','부천시 원미구 길주로 234');
insert into tb_apt_building(AB_PNU, AB_NAME, AB_ADDRESS, AB_ROAD_ADDRESS) values('4119210800110390000','은하마을(주공1)','','부천시 원미구 중동로 301');
insert into tb_apt_building(AB_PNU, AB_NAME, AB_ADDRESS, AB_ROAD_ADDRESS) values('4119210800111700000','포도마을(삼보)','','부천시 원미구 조마루로 135');

-- 설악마을 평형별 면적 
insert into tb_area_type(AT_PRIVATE_AREA, AT_PUBLIC_AREA, AT_SUPPLY_AREA, AT_AB_PNU) values('44.1','16.41','60.51','4119210800110510000');
insert into tb_area_type(AT_PRIVATE_AREA, AT_PUBLIC_AREA, AT_SUPPLY_AREA, AT_AB_PNU) values('49.8','19.65','69.45','4119210800110510000');


	