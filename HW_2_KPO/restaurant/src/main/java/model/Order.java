package model;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int userId;
    private int dishId;
    private String dishName;
    private Timestamp orderTime;
    private OrderStatus status;

    public Order() {
    }

    public Order(int orderId, int userId, int dishId, String dishName, Timestamp orderTime, OrderStatus status) {
        this.dishName = dishName;
        this.orderId = orderId;
        this.userId = userId;
        this.dishId = dishId;
        this.orderTime = orderTime;
        this.status = status != null ? status : OrderStatus.READY;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status != null ? status : OrderStatus.READY;
    }
}
