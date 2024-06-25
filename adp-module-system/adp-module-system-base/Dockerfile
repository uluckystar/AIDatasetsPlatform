FROM openjdk:17-jdk-slim
LABEL maintainer="jiang_star@outlook.com"
COPY target/adp-module-system-base-0.0.1-SNAPSHOT-JDK17.jar /app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app.jar"]
