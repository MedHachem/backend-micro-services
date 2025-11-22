# --- STAGE 1 : BUILD ---
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -U -e -X -DskipTests clean package

# --- STAGE 2 : RUN ---
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le jar
COPY --from=builder /app/target/*.jar app.jar

# Copier le script wait-for
COPY wait-for.sh ./wait-for.sh
RUN chmod +x wait-for.sh

# Installer netcat (nc) pour vérifier un port
RUN apt-get update && apt-get install -y netcat-openbsd && apt-get clean

EXPOSE 8087

# Lancer d'abord le script puis le microservice
ENTRYPOINT ["./wait-for.sh", "service-registery", "8761","./wait-for.sh", "config-server", "8088", "java", "-jar", "app.jar"]
