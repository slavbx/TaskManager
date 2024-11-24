# Используем официальный образ Maven для сборки
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Используем официальный образ OpenJDK для запуска
FROM eclipse-temurin:17.0.10_7-jdk
COPY --from=build /app/target/TaskManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]