FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY backend/pom.xml pom.xml
COPY backend/src src
RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk21-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/AI-RnD-Workspace.war /usr/local/tomcat/webapps/ROOT.war
ENV CATALINA_OPTS="-Dserver.address=0.0.0.0 -Dtomcat.shutdown.port=-1"
EXPOSE 8080
CMD ["catalina.sh", "run"]