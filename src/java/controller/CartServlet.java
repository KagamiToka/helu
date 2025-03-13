package controller;

import dao.ProductDAO;
import model.Cart;
import model.CartItem;
import model.Product;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CartServlet", urlPatterns = {"/carts"})
public class CartServlet extends HttpServlet {
    private ProductDAO productDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        request.setAttribute("cart", cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart/cart.jsp"); // Điều chỉnh đường dẫn
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carts");
            return;
        }

        String productIdParam = request.getParameter("productId");
        if (productIdParam == null || productIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carts");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/carts");
            return;
        }

        Product product = productDao.getProductById(productId);
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/carts");
            return;
        }

        switch (action) {
            case "add":
                String quantityParam = request.getParameter("quantity");
                if (quantityParam == null || quantityParam.isEmpty()) {
                    cart.addItem(product, 1); // Mặc định là 1 nếu không có quantity
                } else {
                    try {
                        int quantity = Integer.parseInt(quantityParam);
                        cart.addItem(product, quantity);
                    } catch (NumberFormatException e) {
                        cart.addItem(product, 1); // Mặc định là 1 nếu lỗi
                    }
                }
                break;

            case "update":
                String updateQuantityParam = request.getParameter("quantity");
                if (updateQuantityParam == null || updateQuantityParam.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/carts");
                    return;
                }
                try {
                    int updateQuantity = Integer.parseInt(updateQuantityParam);
                    cart.updateItem(productId, updateQuantity);
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/carts");
                    return;
                }
                break;

            case "remove":
                cart.removeItem(productId);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/carts");
                return;
        }

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/carts");
    }
}