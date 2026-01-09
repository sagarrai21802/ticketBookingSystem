# Use Eclipse Temurin JDK 21 (free, stable)
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy gradle files first for caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Make gradlew executable
RUN chmod +x gradlew

# Copy source code
COPY src src

# Build the application
RUN ./gradlew build --no-daemon

# Runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built jar
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
