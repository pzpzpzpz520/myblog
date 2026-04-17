FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
COPY blog-common blog-common
COPY blog-api blog-api
COPY blog-service-content blog-service-content

RUN chmod +x mvnw \
    && ./mvnw -pl blog-service-content -am package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /workspace/blog-service-content/target/blog-service-content-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
