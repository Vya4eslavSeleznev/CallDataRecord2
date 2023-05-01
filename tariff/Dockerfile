FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/tariff-1.0.0.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","app.jar"]
