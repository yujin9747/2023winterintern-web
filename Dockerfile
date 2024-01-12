FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/kepco-web-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /kepco-web.jar

ENTRYPOINT ["java","-jar", "/kepco-web.jar"]