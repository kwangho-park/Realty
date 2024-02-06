-- 테스트용 관리자 데이터 
insert into tb_user_info  (UI_USER_ID, UI_USER_PW, UI_USER_NAME ) values('pkh','pkh','박광호');

-- batch 테스트 데이터 (제거예정)
INSERT INTO orders(order_item, price, order_date) values ('카카오 선물', 15000, '2022-03-01');
INSERT INTO orders(order_item, price, order_date) values ('배달주문', 18000, '2022-03-01');
INSERT INTO orders(order_item, price, order_date) values ('교보문고', 14000, '2022-03-02');
INSERT INTO orders(order_item, price, order_date) values ('아이스크림', 3800, '2022-03-03');
INSERT INTO orders(order_item, price, order_date) values ('치킨', 21000, '2022-03-04');
INSERT INTO orders(order_item, price, order_date) values ('커피', 4000, '2022-03-04');
INSERT INTO orders(order_item, price, order_date) values ('교보문고', 13800, '2022-03-05');
INSERT INTO orders(order_item, price, order_date) values ('카카오 선물', 5500, '2022-03-06')

-- RealtyServer 테스트 데이터
insert into tb_app_info(AI_ID, AI_NAME, AI_PROXY_IP, AI_PROXY_PORT, AI_PROTOCOL, AI_URI, AI_DESCRIPTION) values ('1','realty_app','1.1.1.1','8443','http','https://127.0.0.1:8080/realty','설명');
insert into tb_app_info(AI_ID, AI_NAME, AI_PROXY_IP, AI_PROXY_PORT, AI_PROTOCOL, AI_URI, AI_DESCRIPTION) values ('2','map','1.1.1.2','8443','http','https://127.0.0.1:8080/map','지도앱');
insert into tb_app_info(AI_ID, AI_NAME, AI_PROXY_IP, AI_PROXY_PORT, AI_PROTOCOL, AI_URI, AI_DESCRIPTION) values ('3','batch','1.1.1.3','8443','http','https://127.0.0.1:8080/batch','배치앱');


