FROM openjdk:12
WORKDIR /app
COPY ./Batch-0.0.1.jar /app/Batch-0.0.1.jar
ENTRYPOINT ["java", "-jar","Batch-0.0.1.jar", "-Dspring-boot.run.arguments=--spring.batch.job.names=${job_name}"]