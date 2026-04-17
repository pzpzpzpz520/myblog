FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
COPY blog-gateway blog-gateway

RUN chmod +x mvnw \
    && ./mvnw -pl blog-gateway -am package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /workspace/blog-gateway/target/blog-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
