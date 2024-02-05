# Batch app  
부동산 정보 수집을 위한 배치 (by openAPI)

# Spec
- SDK : openJDK 12   (* 유의 : openJDK 18,20 는 spring batch 4.0과 비호환)
- Framework : Spring boot 2.5.1
- Framework : Spring batch 4.0  (* 유의 : batch 5.0 은 사용하는 lib 차이가 큼)
- DB : MariaDB 11.1.2
- Build tool : gradle 8.3
- IDEA : IntelliJ

# batch 개발환경 설정 
- [테스트 코드로 추후 제거예정] JVM argument 추가 : --spring.batch.job.names=trMigrationJob