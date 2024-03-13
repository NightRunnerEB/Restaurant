<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #343a40; /* Темный фон */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #ffffff; /* Белый текст */
        }
        .form-container {
            background-color: #212529; /* Еще более темный фон для контейнеров */
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.15);
            width: 300px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        form {
            display: flex;
            flex-direction: column;
            width: 100%;
        }
        label {
            margin-bottom: 5px;
            font-weight: 600;
            color: #ffffff; /* Белый текст для лейблов */
        }
        input[type=text], input[type=password] {
            padding: 15px;
            margin-bottom: 15px; /* Добавлен нижний отступ между полями ввода */
            border: 1px solid #495057; /* Светлее, для видимости на темном фоне */
            background-color: #2c3034; /* Темный фон для инпутов */
            border-radius: 6px;
            font-size: 16px;
            width: 100%;
            box-sizing: border-box;
            color: #ffffff; /* Белый текст */
        }
        input[type=submit] {
            background-color: #17a2b8;
            color: white;
            padding: 15px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            width: 100%;
        }
        input[type=submit]:hover {
            background-color: #138496;
        }
        h2 {
            color: #ffffff; /* Белый текст для заголовков */
            margin-bottom: 20px;
        }
        p {
            color: #f8d7da; /* Светлый цвет для ошибок, чтобы они были видны на темном фоне */
            margin-top: 15px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Регистрация</h2>
    <form action="${pageContext.request.contextPath}/sign-up" method="post">
        <div>
            <label for="name">Имя:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div>
            <label for="login">Логин:</label>
            <input type="text" id="login" name="login" required>
        </div>
        <div>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <label for="repeatPassword">Повторите пароль:</label>
            <input type="password" id="repeatPassword" name="repeatPassword" required>
        </div>
        <input type="submit" value="Зарегистрироваться">
    </form>
    <p>${passwordError}</p>
    <p>${loginError}</p>
    <p>${emptyFieldsError}</p>
    <p>${SQLError}</p>
</div>

</body>
</html>
