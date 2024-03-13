package repositories;
import model.Dish;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;

public class DishRepository {

    private final HikariDataSource dataSource;

    public DishRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dish> getDishes() throws SQLException {
        List<Dish> dishes = new ArrayList<>();
        String getDishesSQL = "SELECT * FROM dishes";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getDishesSQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDescription(rs.getString("description"));
                dish.setQuantity(rs.getInt("quantity"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setTime(rs.getInt("preparation_time"));
                dishes.add(dish);
            }
        }
        return dishes;
    }

    public List<Dish> getAvailableDishes() throws SQLException {
        List<Dish> availableDishes = new ArrayList<>();
        String getAvailableSQL = "SELECT * FROM dishes WHERE isAvailable = TRUE";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getAvailableSQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDescription(rs.getString("description"));
                dish.setQuantity(rs.getInt("quantity"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setTime(rs.getInt("preparation_time"));
                dish.setIsAvailable(rs.getBoolean("isAvailable"));
                availableDishes.add(dish);
            }
        }
        return availableDishes;
    }
    public void addNewDish(String name, String description, String quantity, String price, String time) throws SQLException {
        String addNewDishSQL = "INSERT INTO dishes (name, description, quantity, price, preparation_time) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(addNewDishSQL)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, Integer.parseInt(quantity));
            stmt.setBigDecimal(4, new java.math.BigDecimal(price));
            stmt.setInt(5, Integer.parseInt(time));
            stmt.executeUpdate();
        }
    }

    public void decreaseQuantity(int dishId) throws SQLException {
        String decreaseQuantitySQL = "UPDATE dishes SET quantity = quantity - 1 WHERE id = ? AND quantity > 0 RETURNING quantity";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(decreaseQuantitySQL)) {
            updateStmt.setInt(1, dishId);
            try (ResultSet resultSet = updateStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt("quantity") <= 0) {
                    String updateAvailabilitySql = "UPDATE dishes SET isAvailable = FALSE WHERE id = ?";
                    try (PreparedStatement updateAvailabilityStmt = conn.prepareStatement(updateAvailabilitySql)) {
                        updateAvailabilityStmt.setInt(1, dishId);
                        updateAvailabilityStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public void deleteDish(int dishId) throws SQLException {
        String deleteDishSQL = "DELETE FROM dishes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteDishSQL)) {
            stmt.setInt(1, dishId);
            stmt.executeUpdate();
        } catch (SQLException exp) {
            throw new SQLException("Блюдо не может быть удалено, если оно присутствует в заказе пользователя или является частью отзывов!");
        }
    }

    public int getTime(int dishId) throws SQLException {
        String getTimeSQL = "SELECT preparation_time FROM dishes WHERE id = ?";
        int time = 0;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getTimeSQL)) {
            stmt.setInt(1, dishId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    time = rs.getInt("preparation_time");
                } else {
                    throw new SQLException("Блюда с ID " + dishId + "не найдено");
                }
            }
        }
        return time;
    }

    public Map<Integer, Double> getAverageRating() throws SQLException {
        Map<Integer, Double> averageRatings = new HashMap<>();
        String getAverageSQL = "SELECT dishId, AVG(rating) AS average_rating FROM feedbacks GROUP BY dishId";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getAverageSQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int dishId = rs.getInt("dishId");
                double avgRating = rs.getDouble("average_rating");
                averageRatings.put(dishId, avgRating);
            }
        }
        return averageRatings;
    }
}
