FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
WORKDIR /app/Trackademy/Trackademy
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/Trackademy/Trackademy/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]