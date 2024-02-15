[Database 형상관리]

**[개요]** <br>
- 개발환경별 local DB 차이로 인한 side-effect 를 방지하기위해 database 형상관리방법을 추가함 (구조 : init, version, latest)  <br> 
- 예시 : init script + v1.0.0  fatch script = v1.0.0 release 한 DB 형상 <br><br> 


**[directory 별 상세 설명]** <br> 
- init : 초기 형상 (create/insert script, er diagram)  <br>
- latest : 최신버전 형상 (create/insert script, er diagram) <br>
- version : 버전별 형상 (fetch script)  <br>
-- v1.0.0 : release 버전별 형상 (fetch script, create/insert script, er diagram) <br> 

