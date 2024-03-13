package repositories;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FeedbackRepository {

    private final HikariDataSource dataSource;

    public FeedbackRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addNewFeedback(int dishId, int rating, String comment) throws SQLException {
        String addNewFeedbackSQL = "INSERT INTO feedbacks (dishId, rating, comment) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(addNewFeedbackSQL)) {

            stmt.setInt(1, dishId);
            stmt.setInt(2, rating);
            stmt.setString(3, comment);

            stmt.executeUpdate();
        }
    }

    public void deleteAllFeedbacks() throws SQLException {
        String deleteAllFeedbacksSQL = "DELETE FROM feedbacks";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteAllFeedbacksSQL)) {
            stmt.executeUpdate();
        } catch (SQLException exp) {
            throw new SQLException("Произошла ошибка при удалении всех отзывов: " + exp.getMessage(), exp);
        }
    }
}
