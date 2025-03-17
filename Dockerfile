FROM amazoncorretto:17

WORKDIR /app

COPY target/crypto-recommender-0.0.1-SNAPSHOT.jar /app/crypto-recommender.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/crypto-recommender.jar"]
