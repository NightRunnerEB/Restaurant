<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Page</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #2c3e50; /* Темный цвет фона темы */
            color: #ffffff; /* Белый текст */
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            align-items: flex-start;
            gap: 20px;
            padding-top: 20px;
            margin: 0;
        }
        .menu-section, .order-section {
            flex: 1 1 300px;
            padding: 40px;
            background-color: #34495e; /* Темный фон раздела темы */
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.25); /* Тень для придания глубины */
            margin: 10px;
        }
        .item, .order-item {
            margin-bottom: 20px;
            padding: 20px;
            background-color: #3f5368; /* Немного светлее фона раздела */
            border-radius: 8px;
        }
        .form-container form {
            display: flex;
            flex-direction: column;
        }
        .form-container button, .pay-btn {
            cursor: pointer;
            background-color: #3498db; /* Изменен цвет кнопок для темной темы */
            padding: 15px 30px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            margin-top: 10px;
        }
        .form-container button:hover, .pay-btn:hover {
            background-color: #2980b9; /* Кнопка затемнения при наведении курсора */
        }
        .pay-btn {
            background-color: #28a745; /* Зеленая кнопка для оплаты */
        }
        .pay-btn:hover {
            background-color: #1d8348; /* Затемнить кнопку оплаты при наведении курсора */
        }
        .margin-down {
            margin-top: 5px;
        }
        h2 {
            margin-bottom: 20px;
        }
        p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
<div class="menu-section">
    <h2>Меню</h2>
    <c:forEach var="dish" items="${dishes}">
        <div class="item">
            <p><strong>Название:</strong> ${dish.getName()}</p>
            <p><strong>Описание:</strong> ${dish.getDescription()}</p>
            <p><strong>Цена:</strong> ${dish.getPrice()}</p>
            <p><strong>Время приготовления:</strong> ${dish.getTime()} minutes</p>
            <p><strong>Оставшееся количество:</strong> ${dish.getQuantity()}</p>

            <form action="${pageContext.request.contextPath}/order/makeOrder" method="post" class="form-container">
                <input type="hidden" name="dishId" value="${dish.getId()}" />
                <button type="submit" class="order-btn">Order</button>
            </form>
        </div>
    </c:forEach>
</div>
<div class="order-section">
    <h2>Ваши заказы</h2>
    <c:forEach var="order" items="${userOrders}">
        <div class="order-item">
            <p>Блюдо: ${order.getDishName()}</p>
            <p>Время: ${order.getOrderTime()}</p>
            <p>Статус: ${order.getStatus()}</p>
            <form action="${pageContext.request.contextPath}/order/deleteOrder" method="post" class="form-container">
                <input type="hidden" name="orderId" value="${order.getOrderId()}" />
                <button type="submit" class="cancel-btn">Отменить</button>
            </form>
            <c:if test="${order.status == 'READY'}">
                <form action="${pageContext.request.contextPath}/order/feedback" method="get" style="display:inline;" class="form-container">
                    <input type="hidden" name="orderDishId" value="${order.getDishId()}" />
                    <input type="hidden" name="orderId" value="${order.getOrderId()}" />
                    <button type="submit" class="pay-btn margin-down">Заплатить</button>
                </form>
            </c:if>
            <p>${paymentError}</p>
        </div>
    </c:forEach>
</div>
</body>
</html>
