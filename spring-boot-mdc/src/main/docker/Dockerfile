FROM openjdk:8-jdk-alpine
WORKDIR /springboot-mdc
VOLUME /tmp
ADD spring-boot-mdc-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]