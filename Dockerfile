FROM openjdk:17
WORKDIR /app
ARG JAR_FILE=controller-0.0.1-SNAPSHOT.jar
COPY $JAR_FILE /zero-climb.jar
CMD ["java", "-jar", "/zero-climb.jar"]
