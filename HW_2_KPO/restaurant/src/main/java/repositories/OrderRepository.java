package repositories;

import com.zaxxer.hikari.HikariDataSource;
import model.OrderStatus;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final HikariDataSource dataSource;

    public OrderRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getNumberOfOrders() throws SQLException {
        int totalOrders = 0;
        String getCountSQL = "SELECT COUNT(*) AS total_orders FROM orders";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getCountSQL);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalOrders = rs.getInt("total_orders");
            }
        }
        return totalOrders;
    }

    public boolean deleteOrder(int orderId) throws SQLException {
        String deleteOrderSQL = "DELETE FROM orders WHERE orderId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteOrderSQL)) {

            stmt.setInt(1, orderId);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        }
    }

    public int addNewOrder(int userId, int dishId, Timestamp orderDateTime, String status) throws SQLException {
        String addNewOrderSQL = "INSERT INTO orders (userId, dishId, orderDateTime, status) VALUES (?, ?, ?, ?)";
        int generatedOrderId = 0;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(addNewOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, dishId);
            stmt.setTimestamp(3, orderDateTime);
            stmt.setString(4, status.toUpperCase());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedOrderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Не удалось создать заказ, ID не получен");
                    }
                }
            }
        }
        return generatedOrderId;
    }

    public void deleteAllOrders() throws SQLException {
        String deleteAllOrdersSQL = "DELETE FROM orders";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteAllOrdersSQL)) {
            stmt.executeUpdate();
        }
    }

    public List<Order> getOrders(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String getOrdersSQL = "SELECT o.orderId, o.userId, o.dishId, o.orderDateTime, o.status, d.name AS dishName " +
                "FROM orders o " +
                "JOIN dishes d ON o.dishId = d.id " +
                "WHERE o.userId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getOrdersSQL)) {

            stmt.setInt(1, userId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setUserId(resultSet.getInt("userId"));
                    order.setOrderId(resultSet.getInt("orderId"));
                    order.setDishId(resultSet.getInt("dishId"));
                    order.setDishName(resultSet.getString("dishName"));
                    order.setOrderTime(resultSet.getTimestamp("orderDateTime"));
                    order.setStatus(OrderStatus.valueOf(resultSet.getString("status").toUpperCase()));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

}
