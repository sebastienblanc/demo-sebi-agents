# multi-stage build: build jar then create runtime image
FROM maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /workspace
COPY pom.xml mvnw .
COPY .mvn .mvn
COPY src src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
COPY --from=builder /workspace/${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
