# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml first (layer caching)
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src/ src/
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/astroloop-backend-1.0.0.jar app.jar

# Expose port (Render sets PORT env var)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
