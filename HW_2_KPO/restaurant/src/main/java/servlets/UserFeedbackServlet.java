package servlets;

import com.zaxxer.hikari.HikariDataSource;
import connection.pool.DataConnectionPool;
import repositories.OrderRepository;
import repositories.FeedbackRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserFeedbackServlet", value = { "/order/feedback"})
public class UserFeedbackServlet extends HttpServlet {
    private FeedbackRepository feedbackRepository;
    private OrderRepository orderRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        HikariDataSource dataSource = DataConnectionPool.getDataSource();
        this.feedbackRepository = new FeedbackRepository(dataSource);
        this.orderRepository = new OrderRepository(dataSource);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String rating = request.getParameter("rating");
        String comment = request.getParameter("comment");
        if (comment == null) {
            comment = "";
        }
        int dishId = Integer.parseInt(request.getParameter("orderDishId"));
        if (rating.isEmpty()) {
            request.setAttribute("emptyFieldsError","Выберите оценку, чтобы завершить действие!");
            doGet(request, response);
            return;
        }
        try {
            feedbackRepository.addNewFeedback(dishId, Integer.parseInt(rating), comment);
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
        try {
            orderRepository.deleteOrder(Integer.parseInt(request.getParameter("orderId")));
        } catch (SQLException exp) {
            request.setAttribute("paymentError", "Произошла ошибка оплаты, пожалуйста, повторите попытку позже!");
            getServletContext().getRequestDispatcher(request.getContextPath() + "/order").forward(request, response);
            return;
        }
        getServletContext().getRequestDispatcher("/feedback.jsp").forward(request, response);
    }
}