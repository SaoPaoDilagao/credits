FROM adoptopenjdk/openjdk11:alpine-slim
EXPOSE 8099
ADD target/credits.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]