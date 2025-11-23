# --- STAGE 1 : BUILD ---
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -U -e -X -DskipTests clean package

# --- STAGE 2 : RUN ---
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le jar final
COPY --from=builder /app/target/*.jar app.jar

# Copier le script d'attente
COPY wait-for.sh ./wait-for.sh
RUN chmod +x wait-for.sh

# Installer netcat pour tester les ports
RUN apt-get update && apt-get install -y netcat-openbsd && apt-get clean

# Le gateway tourne sur 8080
EXPOSE 8080

# Attendre Eureka ET Config Server avant de démarrer
ENTRYPOINT ["./wait-for.sh", "service-registery", "8761", "config-server", "8088", "java", "-jar", "app.jar"]

