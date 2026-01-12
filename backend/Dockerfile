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

# JVM optimization flags for faster cold starts:
# -XX:TieredStopAtLevel=1: Skip C2 compiler (faster startup, slightly slower runtime)
# -XX:+UseSerialGC: Faster startup than G1GC for small apps
# -Xms128m -Xmx256m: Constrained heap for Render free tier (512MB)
# -XX:+UseContainerSupport: Respect container memory limits
# -Dspring.backgroundpreinitializer.ignore=true: Skip background init
ENTRYPOINT ["java", \
    "-XX:TieredStopAtLevel=1", \
    "-XX:+UseSerialGC", \
    "-Xms128m", \
    "-Xmx256m", \
    "-XX:+UseContainerSupport", \
    "-Dspring.backgroundpreinitializer.ignore=true", \
    "-jar", "app.jar"]
