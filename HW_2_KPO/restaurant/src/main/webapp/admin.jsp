<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #343a40; /* Темный фон */
            color: #ffffff; /* Белый текст */
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            align-items: flex-start;
            gap: 20px;
            padding-top: 20px;
            margin: 0;
        }
        .dish-section, .menu-section, .stats-section {
            flex: 1 1 300px;
            padding: 40px;
            background-color: #212529; /* Еще более темный фон для секций */
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.5); /* Усиленная тень */
        }
        .dish-info {
            margin-bottom: 20px;
            padding: 20px;
            background-color: #495057; /* Серо-черный фон для информации о блюде */
            border-radius: 8px;
        }
        .form-container textarea, .form-container input, .form-container button {
            margin-bottom: 15px;
            padding: 15px;
            border: 1px solid #6c757d; /* Светло-серый бордюр */
            background-color: #212529; /* Еще более темный фон для секций */
            color: #ffffff; /* Белый текст для элементов формы */
            border-radius: 6px;
            width: calc(100% - 30px);
            box-sizing: border-box;
        }
        .form-container button {
            margin-bottom: 5px;
            cursor: pointer;
            background-color: #007bff; /* Синяя кнопка */
        }
        .form-container button:hover {
            background-color: #0056b3; /* Темно-синяя кнопка при наведении */
        }
        .delete-btn {
            cursor: pointer;
            background-color: #dc3545; /* Красная кнопка */
            padding: 10px;
            border: none;
            border-radius: 6px;
            margin-top: 10px;
        }
        .delete-btn:hover {
            background-color: #bd2130; /* Темно-красная кнопка при наведении */
        }
        h2 {
            color: #ffffff; /* Белый текст для заголовков */
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="dish-section form-container">
    <h2>Добавить блюдо</h2>
    <form action="${pageContext.request.contextPath}/admin/addDish" method="post">
        <label for="name">Название:</label>
        <input type="text" id="name" name="name" required>

        <label for="description">Описание:</label>
        <textarea id="description" name="description" required></textarea>

        <label for="quantity">Имеющееся количество блюд:</label>
        <input type="number" id="quantity" name="quantity" required>

        <label for="price">Цена:</label>
        <input type="text" id="price" name="price" required>

        <label for="time">Время приготовления:</label>
        <input type="number" id="time" name="time" required>

        <button type="submit">Добавить блюдо в меню</button>
    </form>
    <form action="${pageContext.request.contextPath}/admin/deleteFeedbacks" method="post">
        <button type="submit">Очистить историю</button>
    </form>
    <p>${emptyFieldsError}</p>
    <p>${SQLError}</p>
    <p>${SQLError1}</p>
    <p>${SQLError4}</p>
</div>

<div class="menu-section">
    <h2>Меню</h2>
    <c:forEach var="dish" items="${dishes}">
        <div class="dish-info">
            <p><strong>Название:</strong> ${dish.getName()}</p>
            <p><strong>Описание:</strong> ${dish.getDescription()}</p>
            <p><strong>Цена:</strong> ${dish.getPrice()}</p>
            <p><strong>Время приготовления:</strong> ${dish.getTime()} minutes</p>
            <p><strong>Имеющееся количество блюд:</strong> ${dish.getQuantity()}</p>
            <p><strong>Доступность:</strong> ${dish.getIsAvailable()}</p>

            <form action="${pageContext.request.contextPath}/admin/deleteDish" method="post">
                <input type="hidden" name="dishId" value="${dish.getId()}"/>
                <button type="submit" class="delete-btn">Удалить блюдо</button>
            </form>
        </div>
    </c:forEach>
</div>

<div class="stats-section">
    <h2>Отчет</h2>
    <p><strong>Заказов в процессе обслуживания:</strong> ${numberOfOrders}</p>
    <p>${SQLError2}</p>
</div>

<div class="stats-section">
    <h2>Средняя оценка обслуживания</h2>
    <c:forEach var="entry" items="${averageRating}">
        <p><strong>ID блюда ${entry.key}:</strong> оценка - ${entry.value}</p>
    </c:forEach>
    <p>${SQLError3}</p>
</div>

</body>
</html>

