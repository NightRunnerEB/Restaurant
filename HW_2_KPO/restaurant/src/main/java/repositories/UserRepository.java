package repositories;

import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;


public class UserRepository {

    private final HikariDataSource dataSource;

    public UserRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isUserAuthenticated(HttpServletRequest request, String login, String password) throws SQLException {
        boolean isAuthenticated = false;
        String isAuthenticatedSQL = "SELECT password FROM users WHERE login = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(isAuthenticatedSQL)) {
            stmt.setString(1, login);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (storedPassword != null && storedPassword.equals(password)) {
                        isAuthenticated = true;
                    } else {
                        request.setAttribute("loginError", "Неверный логин или пароль!");
                    }
                } else {
                    request.setAttribute("loginError", "Пользователя с таким логином не существует!");
                }
            }
        }
        return isAuthenticated;
    }

    public boolean isExistSQL(String login) throws SQLException {
        String isExistSQL = "SELECT COUNT(*) FROM users WHERE login = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(isExistSQL)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public Integer getUserId(String login, String password) throws SQLException {
        String getUserIdSQL = "SELECT id FROM users WHERE login = ? AND password = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getUserIdSQL)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return null;
    }

    public void createUser(String name, String login, String password) throws SQLException {
        String createUserSQL = "INSERT INTO users (username, login, password) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(createUserSQL)) {
            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.executeUpdate();
        }
    }

}
