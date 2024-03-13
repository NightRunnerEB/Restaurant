package servlets;

import com.zaxxer.hikari.HikariDataSource;
import connection.pool.DataConnectionPool;
import model.Dish;
import model.Order;
import repositories.DishRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;
import repositories.OrderRepository;
import model.OrderStatus;
import service.OrderProcessingService;

@WebServlet(name = "userServlet", value = { "/order", "/order/makeOrder", "/order/deleteOrder"})
public class UserServlet extends HttpServlet {

    private OrderProcessingService orderProcessingService;
    private OrderRepository orderRepository;
    private DishRepository dishRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        HikariDataSource dataSource = DataConnectionPool.getDataSource();
        this.orderRepository = new OrderRepository(dataSource);
        this.dishRepository = new DishRepository(dataSource);
        this.orderProcessingService = new OrderProcessingService(dataSource);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        try {
            if (requestURI.endsWith("/order/makeOrder")) {
                int userId = Integer.parseInt((String) session.getAttribute("userId"));
                int dishId = Integer.parseInt(request.getParameter("dishId"));
                Timestamp orderDateTime = new Timestamp(System.currentTimeMillis());
                OrderStatus status = OrderStatus.ACCEPTED;
                int orderId = orderRepository.addNewOrder(userId, dishId, orderDateTime, status.toString());

                int time = dishRepository.getTime(dishId);
                orderProcessingService.processOrder(orderId, time);

                dishRepository.decreaseQuantity(dishId);
            } else if (requestURI.endsWith("/order/deleteOrder")) {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                orderRepository.deleteOrder(orderId);
            }
        } catch (SQLException exp) {
            request.setAttribute("SQLError", "Возникла проблема, попробуйте позже!");
            doGet(request, response);
            return;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        response.sendRedirect(request.getContextPath() + "/order");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Dish> dishes = null;
        try {
            dishes = dishRepository.getAvailableDishes();
        } catch (SQLException exp) {
            request.setAttribute("SQLError", "Возникла проблема, попробуйте позже!");
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        List<Order> orders = null;
        int userId = (Integer) session.getAttribute("userId");
        try {
            orders = orderRepository.getOrders(userId);
        } catch (SQLException exp) {
            request.setAttribute("SQLError", "Возникла проблема, попробуйте позже!");
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        request.setAttribute("dishes", dishes);
        request.setAttribute("userOrders", orders);
        getServletContext().getRequestDispatcher("/user.jsp").forward(request, response);
    }
}