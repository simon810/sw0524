## Use an official Java runtime as a parent image
#FROM openjdk:17-jdk-slim
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the project's JAR file to the container
#COPY build/libs/tool-rental-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the port the application runs on
#EXPOSE 8080
#
## Run the JAR file
#ENTRYPOINT ["java", "-jar", "app.jar"]
