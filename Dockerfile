FROM gradle:6.7.0-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build

FROM openjdk:8-jre-slim
EXPOSE 9001
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/userapi.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/userapi.jar"]