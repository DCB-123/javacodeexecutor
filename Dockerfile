# Use Java 17 base image
FROM eclipse-temurin:17-jdk

# Set working directory inside the container
WORKDIR /app

# Copy everything to the container
COPY . .

# Build the application
RUN ./mvnw clean install

# Set the command to run the JAR
CMD ["java", "-jar", "target/javacodeexecutor-0.0.1-SNAPSHOT.jar"]
