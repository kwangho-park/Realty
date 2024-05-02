# Batch app  
부동산 정보 수집을 위한 배치 (by openAPI)

# Spec
- SDK : openJDK 12   (* 유의 : openJDK 18,20 는 spring batch 4.0과 비호환)
- Framework : Spring boot 2.5.1
- Framework : Spring batch 4.0  (* 유의 : batch 5.0 은 사용하는 lib 차이가 큼)
- DB : MariaDB 11.1.2
- Build tool : gradle 8.3
- IDEA : IntelliJ

# Spring batch 설정 
- Spring batch 4 를 실행하기위해서는 batch META-DATA table (6ea) 를 DB에 생성해야 함

# batch 개발환경 설정 
- application.properties 파일 추가 : [project home path]/src/resources 경로
- 실행하려는 job 의 program argument 추가 : --spring.batch.job.names=aptSyncJob
- 
