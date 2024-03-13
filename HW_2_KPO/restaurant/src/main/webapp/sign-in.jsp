<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Авторизация</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #343a40; /* Темный фон */
            color: #ffffff; /* Белый текст */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .form-container {
            background-color: #212529; /* Темный фон для контейнера формы */
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(255,255,255,0.1); /* Светлая тень для акцента */
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
            align-self: flex-start;
        }
        input[type=text], input[type=password] {
            padding: 15px;
            margin-bottom: 15px; /* Добавлен нижний отступ между полями ввода */
            border: 1px solid #ced4da;
            background-color: #495057; /* Темный фон для полей ввода */
            border-radius: 6px;
            font-size: 16px;
            width: 100%;
            box-sizing: border-box;
            color: #ffffff; /* Белый текст для полей ввода */
        }
        input[type=submit] {
            background-color: #17a2b8;
            color: white;
            padding: 15px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px; /* Add margin to the top for spacing */
            transition: background-color 0.2s; /* Smooth transition for hover effect */
        }
        input[type=submit]:hover {
            background-color: #138496;
        }
        h2 {
            margin-bottom: 20px;
        }
        p {
            color: #f8d7da; /* Светло-розовый цвет для сообщений об ошибках */
            margin-top: 15px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Авторизация</h2>
    <form action="${pageContext.request.contextPath}/sign-in" method="post">
        <div>
            <label for="login">Логин:</label>
            <input type="text" id="login" name="login" required>
        </div>
        <div>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <input type="submit" value="Авторизоваться">
    </form>
    <p>${emptyFieldsError}</p>
    <p>${loginError}</p>
    <p>${SQLError}</p>
</div>

</body>
</html>
