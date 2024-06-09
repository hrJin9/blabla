# jdk17 image start
FROM openjdk:17-jdk
# 인자 정리 - JAR
ARG JAR_FILE=build/libs/*.jar
# JAR file copy
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
