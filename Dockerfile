# jdk17 image start
FROM openjdk:17-jdk
# 인자 정리 - JAR
ARG JAR_FILE=build/libs/*.jar
ARG JSON_DIR=src/main/resources/static/docs/openapi3.blabla.json
# JAR file copy
COPY ${JAR_FILE} app.jar

COPY ${JSON_FILE} /config/docs/openapi3.blabla.json
COPY docker-compose.yml /config/docs/docker-compose.yml

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=file:/config/application.yml", "app.jar"]


