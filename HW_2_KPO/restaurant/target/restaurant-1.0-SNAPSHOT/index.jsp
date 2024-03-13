<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Home Page</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #343a40; /* Темный фон */
            color: #ffffff; /* Белый текст */
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .buttons-container {
            display: flex;
            gap: 30px;
        }
        .button {
            background-color: #007bff; /* Светлый синий цвет кнопки */
            color: white;
            padding: 15px 30px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            display: flex;
            justify-content: center;
            align-items: center;
            transition: background-color 0.2s; /* Плавный переход цвета */
        }
        .button:hover {
            background-color: #0056b3; /* Темно-синий цвет при наведении */
        }
        h1 {
            color: #ffffff; /* Белый цвет заголовка */
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<h1>Restaurant Home Page</h1>
<div class="buttons-container">
    <a href="sign-in" class="button">Авторизация</a>
    <a href="sign-up" class="button">Регистрация</a>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Выйти из аккаунта" class="button" />
    </form>
</div>
</body>
</html>
