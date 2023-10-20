# Realty
부동산 정보 수집 및 모니터링 앱

# RealtyServer
- spec : java, Spring boot, 

# 설계 
- front-end 는 web app (1차) 과 mobile app (2차) 으로 제공
- 구조 : 부동산 정보 수집 batch , 데이터 가공 및 모니터링 서버

# 기능 
- 부동산 데이터 수집 및 가공 
- JPA 를 활용한 멀티테넌트 기능 (향후 필수) 
- 통계 제공 (차트 등)
- 전세가/매매가 상승하락 알람 등

# openAPI  
- 공공 데이터 포털 : https://www.data.go.kr/data/15099361/openapi.do#/
- 부동산 거래현황 통계조회 서비스 (공공데이터 포털) : https://www.data.go.kr/data/15099361/openapi.do#/API%20%EB%AA%A9%EB%A1%9D/getgetRealEstateTradingAreaDealer
- 부동산 거래현황통계 조회서비스 **'가이드'** 및 **'지역코드'** (한국 부동산원) : https://www.reb.or.kr/reb/na/ntt/selectNttInfo.do?mi=10251&bbsId=1268&nttSn=79542![image](https://github.com/kwangho-park/Realty/assets/44250982/cdff3400-eeb8-4399-a9bc-062227a62c20)
