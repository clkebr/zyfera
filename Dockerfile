#Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build
#Set the working directory
WORKDIR /app
#Copy the necessary files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Make the Maven Wrapper executable
RUN chmod +x mvnw
RUN apt-get update && apt-get install -y maven
# Download dependencies and offline mode
RUN ./mvnw dependency:go-offline
# Copy the source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests
#Create the runtime image
FROM openjdk:21
# Set the working directory
WORKDIR /app
# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar
# Define the entry point for the application
ENTRYPOINT ["java", "-jar", "app.jar"]
