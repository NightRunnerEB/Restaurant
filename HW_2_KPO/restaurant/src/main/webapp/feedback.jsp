<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Оставить отзыв</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #121212; /* Темный фон */
            color: #ffffff; /* Белый текст */
        }
        .feedback-form {
            background-color: #1e1e1e; /* Темный фон для формы */
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(255,255,255,0.1); /* Светлая тень для контраста */
            width: 300px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #ffffff; /* Белый текст для лейблов */
        }
        select, textarea, button {
            width: 90%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #333; /* Темный бордюр для элементов формы */
            background-color: #2a2a2a; /* Темный фон для элементов формы */
            color: #ffffff; /* Белый текст для элементов формы */
            margin-bottom: 10px;
        }
        button {
            background-color: #5cb85c;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #4cae4c;
        }
        .form-message {
            color: #28a745; /* Зеленый текст для сообщений */
            font-size: small;
        }
    </style>
</head>
<body>
<div class="feedback-form">
    <h2>Оставить отзыв</h2>
    <form action="${pageContext.request.contextPath}/order/feedback" method="post">
        <input type="hidden" name="orderDishId" value="${param.orderDishId}" />
        <div class="form-group">
            <label for="rating">Оценка:</label>
            <select name="rating" id="rating">
                <option value="1">Ужасно</option>
                <option value="2">Плохо</option>
                <option value="3">Сомнительно, но окей</option>
                <option value="4">Хорошо</option>
                <option value="5">Чудесно</option>
            </select>
        </div>
        <div class="form-group">
            <label for="comment">Комментарий:</label>
            <textarea name="comment" id="comment" rows="5" placeholder="Напишите свой отзыв"></textarea>
        </div>
        <p class="form-message">Оплата прошла успешно</p>
        <button type="submit">Подтвердить</button>
        <p class="form-message">${emptyFieldsError}</p>
    </form>
</div>
</body>
</html>
