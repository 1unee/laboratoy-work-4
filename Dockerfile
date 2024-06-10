# Используем openjdk:21-slim как базовый образ для сборки
FROM openjdk:21-slim AS build

# Устанавливаем необходимые инструменты, включая Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Копируем исходный код и ресурсы в Docker-образ
COPY pom.xml /build/
COPY src /build/src/

# Устанавливаем рабочую директорию
WORKDIR /build/

# Собираем проект с помощью Maven
RUN mvn clean package -DskipTests

# Создаем отдельный образ с нашим приложением
FROM openjdk:21-slim

# Копируем JAR файл из предыдущего этапа сборки
COPY --from=build /build/target/laboratory-work-0.0.4.jar /app/laboratory-work.jar

# Устанавливаем рабочую директорию
WORKDIR /app

# Запускаем приложение
CMD ["java", "-jar", "laboratory-work.jar"]