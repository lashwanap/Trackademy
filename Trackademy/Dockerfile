# ── Build stage ───────────────────────────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copier tout le projet Spring Boot dans le conteneur
COPY . .

# Compiler et créer le JAR exécutable (sans les tests)
RUN mvn clean package -DskipTests

# ── Runtime stage ─────────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copier uniquement le JAR depuis le build stage
COPY --from=build /app/target/*.jar app.jar

# Railway injecte la variable $PORT automatiquement
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
