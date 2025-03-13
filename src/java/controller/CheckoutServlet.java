package controller;

import dao.OrderDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.Order;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();
    private ProductDAO productDao = new ProductDAO();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart"); // Lấy giỏ hàng đúng kiểu

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        int userId = 1; // Giả sử người dùng đã đăng nhập
        double totalPrice = 0.0;

        // Tính tổng tiền đơn hàng
        for (CartItem item : cart.getItems()) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        // Tạo đơn hàng mới
        Order order = new Order(0, userId, totalPrice, "Pending");
        int orderId = 0;
        try {
            orderId = orderDAO.createOrderReturnId(order);
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Thêm chi tiết đơn hàng
        for (CartItem item : cart.getItems()) {
            orderDAO.addOrderDetail(orderId, item.getProduct().getId(), item.getQuantity(), item.getProduct().getPrice());
        }

        // Xóa giỏ hàng sau khi đặt hàng thành công
        session.removeAttribute("cart");
        response.sendRedirect("success.jsp");
    }
}