package controller;

import dao.IProductDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;


@WebServlet("/ProductServlet")
public class ProductServletBean extends HttpServlet {
    private final IProductDAO productDAO = new ProductDAO();
    
    // Xử lý GET request
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "testBean":
                    addProductByBean(request, response);
                    break;
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                default:
                    listProduct(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    // Xử lý POST request
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void addProductByBean(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Product product = (Product) request.getSession().getAttribute("product");
        if (product != null) {
            productDAO.insertProduct(product);
            response.sendRedirect("products");
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/product/createNewProduct.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.getProductById(productId);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/product/editProduct.jsp").forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(productId);
        response.sendRedirect("products");
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> productList = productDAO.getAllProducts();
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("/product /listProducts.jsp").forward(request, response);
    }
}
