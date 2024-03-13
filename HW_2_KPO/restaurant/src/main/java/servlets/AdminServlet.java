package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import model.Dish;
import com.zaxxer.hikari.HikariDataSource;
import repositories.DishRepository;
import connection.pool.DataConnectionPool;
import repositories.OrderRepository;
import repositories.FeedbackRepository;

import java.util.List;
import java.util.Map;

@WebServlet(name = "adminServlet", value = {"/admin", "/admin/deleteDish", "/admin/addDish", "/admin/deleteFeedbacks"})
public class AdminServlet extends HttpServlet {
    private DishRepository dishRepository;
    private OrderRepository orderRepository;
    private FeedbackRepository feedbackRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        HikariDataSource dataSource = DataConnectionPool.getDataSource();
        this.dishRepository = new DishRepository(dataSource);
        this.orderRepository = new OrderRepository(dataSource);
        this.feedbackRepository = new FeedbackRepository(dataSource);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/admin/deleteDish")) {
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            try {
                dishRepository.deleteDish(dishId);
            } catch (SQLException exp) {
                String errorMessage = exp.getMessage();
                errorMessage = errorMessage.replace("java.sql.SQLException: ", "");
                request.setAttribute("SQLError", errorMessage);
                doGet(request, response);
                return;
            } catch (Exception exp) {
                throw new RuntimeException(exp);
            }
        } else if (requestURI.endsWith("/admin/addDish")) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String quantity = request.getParameter("quantity");
            String price = request.getParameter("price");
            String time = request.getParameter("time");

            if (name.isEmpty() || description.isEmpty() || quantity.isEmpty() || price.isEmpty() || time.isEmpty()) {
                request.setAttribute("emptyFieldsError", "Заполните все поля!");
                doGet(request, response);
                return;
            }

            try {
                dishRepository.addNewDish(name, description, quantity, price, time);
            } catch (SQLException exp) {
                request.setAttribute("SQLError", "Введены некорректные данные!");
                doGet(request, response);
                return;
            } catch (Exception exp) {
                throw new RuntimeException(exp);
            }
        } else if (requestURI.endsWith("/admin/deleteFeedbacks")) {
            try {
                feedbackRepository.deleteAllFeedbacks();
            } catch (SQLException exp) {
                request.setAttribute("SQLError4", "Возникла проблема, попробуйте позже!");
                doGet(request, response);
                return;
            } catch (Exception exp) {
                throw new RuntimeException(exp);
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Dish> dishes = null;
        try {
            dishes = dishRepository.getDishes();
        } catch (SQLException exp) {
            request.setAttribute("SQLError1", "Не удалось получить блюда, повторите попытку позже");
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        int numberOfOrders = 0;
        try {
            numberOfOrders = orderRepository.getNumberOfOrders();
            request.setAttribute("numberOfOrders", numberOfOrders);
        } catch (SQLException exp) {
            request.setAttribute("SQLError2", "Не удалось получить количество заказов, повторите попытку позже");
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        Map<Integer, Double> averageRating = null;
        try {
            averageRating = dishRepository.getAverageRating();
            request.setAttribute("averageRating", averageRating);
        } catch (SQLException exp) {
            request.setAttribute("SQLError3", "Не удалось получить количество заказов, повторите попытку позже");
        }

        request.setAttribute("dishes", dishes);
        getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}