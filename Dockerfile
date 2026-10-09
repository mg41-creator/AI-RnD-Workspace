FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY backend/pom.xml backend/pom.xml
COPY backend/src backend/src

RUN mvn -f backend/pom.xml clean package

FROM tomcat:10.1-jdk21-temurin

RUN sed -i 's/port="8080"/port="10000"/' "$CATALINA_HOME/conf/server.xml"

COPY --from=build /app/backend/target/AI-RnD-Workspace.war "$CATALINA_HOME/webapps/AI-RnD-Workspace.war"

EXPOSE 10000
