FROM adoptopenjdk/openjdk8:alpine-slim
ADD target/credits-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8099
EXPOSE 27018
ENTRYPOINT ["java", "-jar","/app.jar"]