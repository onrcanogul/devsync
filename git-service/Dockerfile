# Stage 1: Build
FROM maven:3.9-eclipse-temurin-23 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:23-jre
WORKDIR /app

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Debug toggle (profile ile açabilirsin)
ARG ENABLE_DEBUG=false
ENV JAVA_TOOL_OPTIONS=""

RUN if [ "$ENABLE_DEBUG" = "true" ]; then \
      export JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n"; \
    fi

COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
