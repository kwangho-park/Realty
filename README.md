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

