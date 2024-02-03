FROM gradle:jdk21 as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle.kts /home/gradle/app/
WORKDIR /home/gradle/app
RUN gradle clean build -i --stacktrace

FROM gradle:jdk21 as builder
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/app/
WORKDIR /usr/src/app
RUN gradle bootJar -i --stacktrace

FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
USER root
WORKDIR /usr/src/app
COPY --from=builder /usr/src/app/build/libs/*.jar ./app.jar
ARG HOST_WALLET="http://localhost:8081"
ENV HOST_WALLET ${HOST_WALLET}
ARG HOST_CERTIFIER="http://localhost:8082"
ENV HOST_CERTIFIER ${HOST_CERTIFIER}
ENTRYPOINT ["java", "-jar", "app.jar"]
