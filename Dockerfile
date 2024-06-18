# jdk17 image start
FROM openjdk:17-jdk
# 인자 정리 - JAR
ARG JAR_FILE=build/libs/*.jar
# JAR file copy
COPY ${JAR_FILE} app.jar

COPY docker-compose.yml /config/docs/docker-compose.yml

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=file:/config/application.yml", "app.jar"]


