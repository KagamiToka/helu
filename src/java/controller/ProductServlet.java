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
    private final IProductDAO productDAO = new ProductDAO(); // Sử dụng interface

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");
        
        // Số sản phẩm trên mỗi trang
//        int productsPerPage = 5;
//        int page = 1;
//
//        if (request.getParameter("page") != null) {
//            try {
//                page = Integer.parseInt(request.getParameter("page"));
//            } catch (NumberFormatException e) {
//                page = 1; // Nếu lỗi, mặc định là trang 1
//            }
//        }
//
//        int offset = (page - 1) * productsPerPage;
//        int totalProducts = productDAO.getTotalProducts();
//        int totalPages = (int) Math.ceil((double) totalProducts / productsPerPage);

        // CHẶN USER TRUY CẬP `CREATE`, `EDIT`, `DELETE`
        if ((action != null) && (action.equals("create") || action.equals("edit") || action.equals("delete"))) {
            if (role == null || !"admin".equals(role)) {
                System.out.println("DEBUG: User do not allow, prevent connect " + action);
                response.sendRedirect("products?error=notAdmin");
                return;
            }
        }

        switch (action != null ? action : "") {
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
                List<Product> allProducts = productDAO.getAllProducts();
                request.setAttribute("productList", allProducts);
                request.getRequestDispatcher("product/listProduct.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");

        // Chặn User thực hiện các hành động Admin (Thêm, Sửa, Xóa)
        if (!"admin".equals(role)) {
            response.sendRedirect("products?error=notAdmin");
            return;
        }

        switch (action) {
            case "add":
                String name = request.getParameter("name");
                String priceStr = request.getParameter("price");
                String description = request.getParameter("description");
                String stockStr = request.getParameter("stock");

                System.out.println("DEBUG: Received data- Name: " + name + ", Price: " + priceStr + ", Description: " + description + ", Stock: " + stockStr);

                if (name == null || priceStr == null || description == null || stockStr == null) {
                    System.out.println("ERROR: Data not enough!");
                    response.sendRedirect("products?error=missingData");
                    return;
                }

                try {
                    double price = Double.parseDouble(priceStr);
                    int stock = Integer.parseInt(stockStr);

                    boolean success = productDAO.insertProduct(new Product(name, price, description, stock, null));
                    if (success) {
                        System.out.println("DEBUG: Add successfull!");
                        response.sendRedirect("products?success=added");
                    } else {
                        System.out.println("ERROR: Add fail!");
                        response.sendRedirect("products?error=addFailed");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Error change type of data!");
                    response.sendRedirect("products?error=invalidData");
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
