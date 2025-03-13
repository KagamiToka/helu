package controller;

import dao.IProductDAO;
import dao.ProductDAO;
import model.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private final IProductDAO productDAO = new ProductDAO();
//    private final List<Product> allProducts = productDAO.getAllProducts();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");

        List<Product> allProducts = productDAO.getAllProducts();
        
        if ((action != null) && (action.equals("create") || action.equals("edit") || action.equals("delete"))) {
            if (role == null || !"admin".equals(role)) {
                System.out.println("DEBUG: User do not allow, prevent connect " + action);
                response.sendRedirect("products?error=notAdmin");
                return;
            }
        }

        switch (action != null ? action : "") {
            case "user":
                String username = (String) session.getAttribute("username");
                if (username == null) {
                    System.out.println("DEBUG: Session expired, redirecting to login");
                    response.sendRedirect(request.getContextPath() + "/user/login.jsp");
                    return;
                }
                
                System.out.println("DEBUG: Loaded " + allProducts.size() + " products for user: " + username);
                request.setAttribute("allProducts", allProducts);
                request.getRequestDispatcher("/user/listProductUser.jsp").forward(request, response);
                break;
            case "create":
                request.getRequestDispatcher("product/createProduct.jsp").forward(request, response);
                break;

            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getProductById(id);
                request.setAttribute("product", product);
                request.getRequestDispatcher("product/editProduct.jsp").forward(request, response);
                break;

            case "search":
                String minPriceStr = request.getParameter("minPrice");
                String maxPriceStr = request.getParameter("maxPrice");
                List<Product> productList;

                try {
                    double minPrice = Double.parseDouble(minPriceStr);
                    double maxPrice = Double.parseDouble(maxPriceStr);
                    productList = productDAO.searchProductByPrice(minPrice, maxPrice);
                } catch (NumberFormatException e) {
                    productList = productDAO.getAllProducts();
                }

                request.setAttribute("productList", productList);
                request.getRequestDispatcher("product/listProduct.jsp").forward(request, response);
                break;

            default:
                System.out.println("DEBUG: ProductServlet loaded " + allProducts.size() + " products");
                request.setAttribute("allProducts", allProducts);
                request.getRequestDispatcher("product/listProduct.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");

        if (!"admin".equals(role)) {
            response.sendRedirect("products?error=notAdmin");
            return;
        }

        switch (action) {
            case "add":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String nameUpdate = request.getParameter("name");
                    double priceUpdate = Double.parseDouble(request.getParameter("price"));
                    String descriptionUpdate = request.getParameter("description");
                    int stockUpdate = Integer.parseInt(request.getParameter("stock"));

                    boolean success = productDAO.updateProduct(new Product(id, nameUpdate, priceUpdate, descriptionUpdate, stockUpdate, null));
                    if (success) {
                        response.sendRedirect("products?success=updated");
                    } else {
                        response.sendRedirect("products?error=updateFailed");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("products?error=invalidData");
                } catch (Exception e) {
                    System.out.println("DEBUG: Update failed: " + e.getMessage());
                    response.sendRedirect("products?error=updateFailed");
                }
                break;

            case "update":
                int id = Integer.parseInt(request.getParameter("id"));
                String nameUpdate = request.getParameter("name");
                double priceUpdate = Double.parseDouble(request.getParameter("price"));
                String descriptionUpdate = request.getParameter("description");
                int stockUpdate = Integer.parseInt(request.getParameter("stock"));

                productDAO.updateProduct(new Product(id, nameUpdate, priceUpdate, descriptionUpdate, stockUpdate, null));
                response.sendRedirect("products");
                break;

            case "delete":
                int productId = Integer.parseInt(request.getParameter("id"));
                productDAO.deleteProduct(productId);
                response.sendRedirect("products");
                break;

            default:
                response.sendRedirect("products");
                break;
        }
    }
}