## Task Management System

Веб приложение для многопользовательского управления задачами на Spring Boot. 

#### Основные функци
Аутентификация и авторизация    
Создание, редактирование, удаление и просмотр задач    
Установка статуса задачи (в ожидании, в процессе, завершено)    
Установка приоритета задачи (высокий, средний, низкий)    
Назначение исполнителя задачи и возможность добавления комментариев    

#### Используемое ПО
InteliJIDEA 2024.1.4
Java JDK17+ (17.0.10)
Maven 3.9.9
Docker Desktop

#### Запуск проекта
Для начала необходимо клонировать репозиторий командой "git clone"    
Затем в терминале необходимо перейти в папку с проектом и выполнить команду "docker-compose up --build"    
После этого Docker Compose в соответствии с Dockerfile соберёт образ приложения на основе maven:3.9.9-eclipse-temurin-17    
Далее Docker Compose создаст и запустит контейнер для PostgreSQL "tm-postgres" на основе postgres:latest    
Далее запустится контейнер с приложением "tm-app", приложение подключится к БД контейнера "tm-postgres" и применит миграции liquibase    
В результате возможно будет получить доступ к приложению по адресу http://localhost:8080/taskmanager/swagger-ui/index.html    