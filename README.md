
# Java Web Application "Restaurant"

Этот проект представляет собой веб-приложение для ресторана, разработанное с использованием Apache Tomcat, Java-сервлетов, JSP-файлов и базой данных PostgreSQL. Сервис поддерживает управление заказами, регистрацию пользователей, создание новых блюд и возможность оставлять отзывы.

## Характеристики проекта
1. Архитектура приложения основана на паттерне MVC. 
2. Репозитории реализованы по паттерну Data Access Object.
3. Класс DataConnectionPool реализует паттерн Singleton.
4. Сервер поддерживает асинхронные запросы.

## Настройка БД

Для работы с базой данных вам необходимо создать таблицы и настроить PostgreSQL, выполнив следующие SQL-команды:

```sql
-- Создаем таблицу для пользователей
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Создаем таблицу для блюд
CREATE TABLE dishes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    preparation_time INT NOT NULL,
    isAvailable BOOLEAN DEFAULT TRUE
);

-- Создаем таблицу для заказов
CREATE TABLE orders (
    orderId SERIAL PRIMARY KEY,
    userId INT NOT NULL,
    dishId INT NOT NULL,
    orderDateTime TIMESTAMP NOT NULL,
    status VARCHAR(255),
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (dishId) REFERENCES dishes(id)
);

-- Создаем таблицу для отзывов
CREATE TABLE feedbacks (
    feedbackId SERIAL PRIMARY KEY,
    dishId INT NOT NULL,
    comment TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    FOREIGN KEY (dishId) REFERENCES dishes(id)
);
```

## Конфигурации

В классе `DataConnectionPool` необходимо заменить настройки подключения к базе данных под настройки вашей системы:

```java
config.setJdbcUrl("jdbc:postgresql://localhost/Restaurant"); // Путь к БД
config.setUsername("postgress"); // Здесь должен располагаться ваш суперюзер
config.setPassword("admin");     // Здесь должен располагаться пароль от суперюзера
```

## Требования к запуску
- Успешное соединение с базой данных PostgreSQL
- Добавить в конфигурациях Tomcat (local) и доступные артифакты для подключения веб-сервера.
- Для администрирования необходимо в начале зарегистрировать пользователя admin.
