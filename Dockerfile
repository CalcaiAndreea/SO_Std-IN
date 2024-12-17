# Imaginea de bază cu Java și Alpine Linux
FROM eclipse-temurin:17-jdk-alpine

# Instalăm Maven
RUN apk add --no-cache maven

# Setăm directorul de lucru în container
WORKDIR /app

# Copiem fișierul pom.xml și descărcăm dependențele
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiem codul sursă în container
COPY src/ src/

# Construim proiectul folosind Maven
RUN mvn clean package -DskipTests

# Expunem portul 8080 pentru accesul la aplicație
EXPOSE 8080

# Comanda de rulare a aplicației
CMD ["java", "-jar", "target/planificare-procese-1.0.0-jar-with-dependencies.jar"]
