FROM openjdk:17
ADD ./target/controlpage-backend-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 42000
ENTRYPOINT ["java", "-jar", "backend.jar"]
