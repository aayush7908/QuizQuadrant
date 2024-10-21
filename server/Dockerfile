# stage - 1
FROM maven:3.8.7-openjdk-18 as builder
WORKDIR /server
COPY . .
RUN mvn clean package -DskipTests

# stage - 2
FROM openjdk:18-jdk-slim
WORKDIR /server
COPY --from=builder /server/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]