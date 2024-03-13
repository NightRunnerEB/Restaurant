package service;

import java.util.concurrent.*;
import com.zaxxer.hikari.HikariDataSource;
import model.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class OrderProcessingService {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private final HikariDataSource dataSource;
    public OrderProcessingService(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void processOrder(int orderId, int time) {
        long delayPreparingSeconds = (long) (time * 6L);
        executorService.schedule(() -> {
            try {
                updateOrderStatus(orderId, OrderStatus.PREPARING);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, delayPreparingSeconds, TimeUnit.SECONDS);

        long timeSeconds = (long) time * 60;

        executorService.schedule(() -> {
            try {
                updateOrderStatus(orderId, OrderStatus.READY);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, timeSeconds, TimeUnit.SECONDS);
    }

    private void updateOrderStatus(int orderId, OrderStatus status) throws SQLException {
        String update = "UPDATE orders SET status = ? WHERE orderId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(update)) {
            stmt.setString(1, status.toString());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }
}
