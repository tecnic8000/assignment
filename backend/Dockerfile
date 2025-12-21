# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Production stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the jar file from build stage
COPY --from=build /app/target/*.jar app.jar

# Copy SSL certificate
COPY src/main/resources/cert ./cert

# Expose the application port
EXPOSE 8011

# Set environment variables (can be overridden at runtime)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/demo1intvw
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres
ENV SPRING_DATA_REDIS_HOST=host.docker.internal
ENV SPRING_DATA_REDIS_PORT=6379

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]