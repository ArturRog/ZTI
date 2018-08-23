FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 80:8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]


FROM registry-app.eng.qops.net:5001/base/java8

COPY builder/build/app.jar /opt
EXPOSE 8006

ENTRYPOINT ["java","-jar","/opt/app.jar"]
CMD []