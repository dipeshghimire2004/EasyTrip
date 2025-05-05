FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8021

ENTRYPOINT ["java", "-jar", "app.jar"]