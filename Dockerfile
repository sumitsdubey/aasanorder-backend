FROM maven:3.9.9-eclipse-temurin-21 AS builder
LABEL authors="sumit"

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk as runner

WORKDIR /app

COPY --from=builder /app/target/aasanorder-backend-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]