FROM docker.repository.esi.adp.com/clientcentral/java:17.0.1-jdk
ARG VER=1.0
ARG JAR_FILE=target/batch1.season2.team4.EMS-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} batch1.season2.team4.EMS-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/batch1.season2.team4.EMS-0.0.1-SNAPSHOT.jar"]
EXPOSE 8085
