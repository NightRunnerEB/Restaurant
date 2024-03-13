<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница ошибки</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #343a40; /* Темный фон */
            color: #ffffff; /* Белый цвет текста */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .error-container {
            max-width: 500px;
            padding: 40px;
            background-color: #495057; /* Светлее, чем основной фон, но все еще темный */
            box-shadow: 0 4px 8px rgba(0,0,0,0.2); /* Более заметная тень для создания контраста */
            border-radius: 10px;
            text-align: center;
        }
        .error-container h1 {
            color: #ff4757; /* Яркий красный для привлечения внимания */
        }
        .error-container p {
            margin: 20px 0 30px;
            line-height: 1.6;
        }
        .error-container a button {
            cursor: pointer;
            background-color: #007bff; /* Синий цвет кнопки */
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            outline: none;
            box-shadow: none;
            transition: background-color 0.2s;
        }
        .error-container a button:hover {
            background-color: #0056b3; /* Темно-синий цвет при наведении */
        }
    </style>
</head>
<body>

<div class="error-container">
    <h1>Упс!</h1>
    <p>Упс... Что-то пошло не так :( Мы не смогли обработать запрос. Приносим свои извинения, повторите попытку чуть позже.</p>
    <a href="${pageContext.request.contextPath}/index.jsp"><button type="button">Попробовать снова</button></a>
</div>

</body>
</html>
