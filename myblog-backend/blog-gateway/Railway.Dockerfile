FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY myblog-backend/pom.xml pom.xml
COPY myblog-backend/blog-common blog-common
COPY myblog-backend/blog-api blog-api
COPY myblog-backend/blog-service-content blog-service-content
COPY myblog-backend/blog-gateway blog-gateway

RUN mvn -pl blog-gateway -am package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /workspace/blog-gateway/target/blog-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
