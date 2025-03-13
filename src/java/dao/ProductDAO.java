package dao;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO{
    private static final String INSERT_PRODUCT = "INSERT INTO Product (name, price, description, stock, import_date) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Product WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product";
    private static final String DELETE_PRODUCT = "DELETE FROM Product WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ? WHERE id = ?";
    private static final String SEARCH_PRODUCTS_BY_PRICE = "SELECT * FROM Product WHERE price BETWEEN ? AND ?";
    
    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getTimestamp("import_date")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Tìm kiếm sản phẩm theo khoảng giá
    public List<Product> searchProductByPrice(double minPrice, double maxPrice) {
        List<Product> productList = new ArrayList<>();
//        String sql = "SELECT * FROM Product WHERE price BETWEEN ? AND ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_PRODUCTS_BY_PRICE)) {

            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getTimestamp("import_date")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    
    @Override
    public Product getProductById(int id) {
//        String sql = "SELECT * FROM Product WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
             
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getTimestamp("import_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertProduct(Product product) {
//        String sql = "INSERT INTO Product (name, price, description, stock, import_date) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_PRODUCT)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getStock());
            stmt.setTimestamp(5, new Timestamp(product.getImportDate().getTime()));

            int rowsInserted = stmt.executeUpdate();
            System.out.println("DEBUG: Add successfull! Rows affected: " + rowsInserted);
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateProduct(Product product) {
//        String sql = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PRODUCT)) {
             
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getStock());
//            stmt.setTimestamp(5, new Timestamp(product.getImportDate().getTime()));
            stmt.setInt(6, product.getId());

            return stmt.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int id) {
//        String sql = "DELETE FROM Product WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_PRODUCT)) {
             
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Product> getProductsByPage(int offset, int limit) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getTimestamp("import_date")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    @Override
    public int getTotalProducts() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM Product";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
//    private Timestamp getCurrentImportDate(int id) {
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getTimestamp("import_date");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new Timestamp(System.currentTimeMillis()); // Giá trị mặc định
//    }
    
    public static void main(String[] args) {
        ProductDAO pr = new ProductDAO();
        for (Product p : pr.getAllProducts()){
            System.out.println(p);
        }
    }
}
