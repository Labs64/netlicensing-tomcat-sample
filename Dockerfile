# package project
FROM maven:3-jdk-8-slim AS builder
WORKDIR /opt/netlicensing-tomcat-sample/
COPY ./ /opt/netlicensing-tomcat-sample/
RUN mvn clean package

# start tomcat
FROM tomcat:7-jre8
COPY --from=builder /opt/netlicensing-tomcat-sample/target/netlicensing-tomcat-sample.war /usr/local/tomcat/webapps/

EXPOSE 8080/tcp
