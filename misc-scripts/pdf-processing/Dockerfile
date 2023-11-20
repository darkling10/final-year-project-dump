FROM openjdk:18
MAINTAINER AbbasPathan
COPY . project/
ENTRYPOINT ["java","-jar","project/target/pdf-processing-0.0.1-SNAPSHOT.jar","--spring.profiles.active=docker"]