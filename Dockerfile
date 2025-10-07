# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Create a smaller final image with only the compiled app
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the JAR file from the 'build' stage
COPY --from=build /app/target/scientific-calculator-1.0-SNAPSHOT.jar scientific-calculator.jar
# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "scientific-calculator.jar"]