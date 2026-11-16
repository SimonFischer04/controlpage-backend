FROM eclipse-temurin:17-jre
COPY ./target/controlpage-backend-*.jar backend.jar
EXPOSE 42000
ENTRYPOINT ["java", "-jar", "backend.jar"]
