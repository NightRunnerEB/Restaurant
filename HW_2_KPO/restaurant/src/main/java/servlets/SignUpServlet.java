package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import connection.pool.DataConnectionPool;
import repositories.UserRepository;

@WebServlet(name = "signUpServlet", value = "/sign-up")
public class SignUpServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");

        if (name.isEmpty() || login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            request.setAttribute("emptyFieldsError", "Заполните все поля!");
            doGet(request, response);
            return;
        }

        if (!password.equals(repeatPassword)) {
            request.setAttribute("passwordError", "Пароли не совпадают!");
            doGet(request, response);
            return;
        }

        try {
            UserRepository userRepository = new UserRepository(DataConnectionPool.getDataSource());
            if (userRepository.isExistSQL(login)) {
                request.setAttribute("loginError", "Пользователя с таким логином не существует!");
                doGet(request, response);
                return;
            } else {
                userRepository.createUser(name, login, password);
            }
        } catch (SQLException exp) {
            request.setAttribute("SQLError", "Введены некорректные данные!");
            doGet(request, response);
            return;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        response.sendRedirect(request.getContextPath() + "/sign-in");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/sign-up.jsp").forward(request, response);
    }
}