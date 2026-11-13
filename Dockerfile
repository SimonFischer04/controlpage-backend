# syntax=docker/dockerfile:1

# Builds the backend from source, so the image can be built anywhere with just `docker build .`
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY . .
# (the tests run in the pipeline's test job, not in the image build)
RUN --mount=type=cache,target=/root/.m2 mvn -B -q package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre
COPY --from=build /src/target/controlpage-backend-*.jar /backend.jar
EXPOSE 42000
ENTRYPOINT ["java", "-jar", "/backend.jar"]
