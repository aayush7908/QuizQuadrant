# stage - 1

FROM maven:3.8.7-openjdk-18 as builder

WORKDIR /server

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ ./src/

RUN mvn clean package -DskipTests=true



# stage - 2

FROM eclipse-temurin:22.0.2_9-jdk-alpine

RUN mkdir /server

COPY --from=builder /server/target/*.jar /server/app.jar

WORKDIR /server

EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]