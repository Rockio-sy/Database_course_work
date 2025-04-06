# Use an official OpenJDK runtime as the base image
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/vidstream-0.0.1-SNAPSHOT.jar app.jar

# Create the directory for saving files
RUN mkdir -p /var/project_videos

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
