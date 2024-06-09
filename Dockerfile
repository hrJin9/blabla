# jdk17 image start
FROM openjdk:17-jdk
# 인자 정리 - JAR
ARG JAR_FILE=build/libs/*.jar
# JAR file copy
COPY ${JAR_FILE} app.jar
정
ENTRYPOINT ["java", "-jar", "-Dspring.config.location=classpath:application.yml", "app.jar"]
