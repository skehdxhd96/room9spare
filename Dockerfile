FROM openjdk:11-jre-slim

VOLUME /tmp

ARG JAR_FILE=room9-backend/build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
