FROM java:8-alpine
VOLUME /tmp

RUN   apk update \
     &&   apk add ca-certificates wget \
     &&   update-ca-certificates   

RUN wget https://github.com/gschmutz/event-driven-microservices-demo/releases/download/1.0.0/customer-management-ms-1.0-SNAPSHOT.jar 

RUN mv customer-management-ms-1.0-SNAPSHOT.jar customer-management.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/customer-management.jar"]
