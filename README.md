# Realty
- 설명 : 부동산 레버리지 투자 앱으로써, 개인화된 투자 정보를 제공하여 사용자의 투자 수익실현에 기여하는 서비스를 개발하는것을 목표로 함 
- language : java, HTML/CSS/JS, JSP
- framework / lib : Spring boot, Spring batch, bootstrap, JPA 

# 론칭일정   
- ${\textsf{\color{red} 2024.11월 중 SOFT OPEN (WEB) : }}$ https://realty.gabia.io/login    


# UI 초안 설계  
(by Figma)  
![realty ui 설계](https://github.com/kwangho-park/Realty/assets/44250982/8dba90da-19e9-4f56-8c9e-255808e9fb25)


# Realty app 구성 및 역활 
- front-end 는 web app 과 mobile app 으로 서비스 
- back-end 구조    
  = batch : 부동산 정보 수집 및 가공하는 데몬, openAPI 를 사용하여 데이터 수집        
  = realtyServer : 데이터 가공 및 web view 제공하는 웹 서버 , 사용자경험이 반영된 UX 가 중요하고, 응답속도 보장을 위해서 서버의 부하를 최소화해야할것으로 판단됨    
  = Redis : client 의 세션정보를 공유     
  ![Realty 서비스 구성도](https://github.com/kwangho-park/Realty/assets/44250982/a33e138a-35fd-40e0-a54e-f885ac05d098)   

# 리얼티 앱 개발방향   
## 데이터 수집방안   
1. 부동산 투자정보 (유동적 데이터) : 일배치로 공공데이터 포털 , v-world 등 에서 openAPI 로 데이터를 수집 및 가공하여 저장
- 예시 : 매매/전월세 거래 데이터, 투자정보, 주택 통계
 
2. 부동산 원천 정보 (메타 데이터) : 사용자가 특정 아파트 매물을 선택할때 openAPI 로 데이터를 수집 (단, 론칭이후에 성능, 부하 상황에 따라서 배치성으로 db에 수집 해야할 수도 있음 )
- 참고 : realty server (was) 에서 realty batch 의 job 을 사용하여 데이터를 수집하는것을 고려 
- 예시 : 아파트 건물정보 (주소, 평수, 등등 ) 
  
# 주요 기능 
- 부동산 실거래 데이터 수집 및 통계연산 (전세가율, 평단가 등)
- Naver map 으로 지역별 부동산 매물정보 제공
- 부동산 매물에 대한 상세정보 제공 (건축연도, 용적률, 건폐율, 대지지분 등)
- 사용자의 사용패턴 분석통계 /로그
- 부동산 투자관련 통계차트 제공
- 전세가/매매가 상승하락 알람
  
# openAPI  
- 공공 데이터 포털 : https://www.data.go.kr/data/15099361/openapi.do#/
- 브이월드 : https://www.vworld.kr/dtna/dtna_apiSvcFc_s001.do?apiNum=6
- 부동산 거래현황 통계조회 서비스 (공공데이터 포털) : https://www.data.go.kr/data/15099361/openapi.do#/API%20%EB%AA%A9%EB%A1%9D/getgetRealEstateTradingAreaDealer
- 부동산 거래현황통계 조회서비스 **'가이드'** 및 **'지역코드'** (한국 부동산원) : https://www.reb.or.kr/reb/na/ntt/selectNttInfo.do?mi=10251&bbsId=1268&nttSn=79542![image](https://github.com/kwangho-park/Realty/assets/44250982/cdff3400-eeb8-4399-a9bc-062227a62c20)
- Naver map API : https://guide.ncloud-docs.com/docs/maps-overview

