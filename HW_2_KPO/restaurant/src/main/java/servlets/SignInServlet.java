package servlets;

import connection.pool.DataConnectionPool;
import repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "signInServlet", value = "/sign-in")
public class SignInServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login.isEmpty() || password.isEmpty()) {
            request.setAttribute("emptyFieldsError", "Заполните все поля!");
            doGet(request, response);
            return;
        }

        HttpSession session = request.getSession();
        try {
            UserRepository userRepository = new UserRepository(DataConnectionPool.getDataSource());
            if (!userRepository.isUserAuthenticated(request, login, password)) {
                doGet(request, response);
                return;
            }
            int id = userRepository.getUserId(login, password);
            session.setAttribute("userId", id);
        } catch (SQLException exp) {
            request.setAttribute("SQLError", "Введены некорректные данные!");
            doGet(request, response);
            return;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        String loginAdmin = "admin";
        String passwordAdmin = "admin";
        if (loginAdmin.equals(login) && passwordAdmin.equals(password)) {
            session.setAttribute("isAdmin", true);
            response.sendRedirect(request.getContextPath() + "/admin");
        } else {
            session.setAttribute("isAdmin", false);
            response.sendRedirect(request.getContextPath() + "/order");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/sign-in.jsp").forward(request, response);
    }
}