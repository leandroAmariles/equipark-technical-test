FROM gradle:8.7-jdk17 AS builder

WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/build/libs/assessment-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/assessment-0.0.1-SNAPSHOT.jar"]
