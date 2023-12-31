#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER perfectsquare.com

# Add the application's jar to the image
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]

