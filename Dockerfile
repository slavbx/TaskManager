# Используем официальный образ Temurin как базовый
FROM eclipse-temurin:17.0.10_7-jdk

# Создаем рабочую директорию
WORKDIR /app

# Копируем jar файл в контейнер
COPY target/TaskManager-0.0.1-SNAPSHOT.jar app.jar

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]