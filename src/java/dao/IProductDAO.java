package dao;
import java.sql.SQLException;
import model.Product;
import java.util.List;

public interface IProductDAO {
    public List<Product> getAllProducts();
    public List<Product> searchProductByPrice(double minPrice, double maxPrice);
    public Product getProductById(int id); // Lấy sản phẩm theo ID
    public boolean insertProduct(Product product); // Thêm sản phẩm
    public boolean updateProduct(Product product); // Cập nhật sản phẩm
    public boolean deleteProduct(int id); // Xóa sản phẩm
    public int getTotalProducts() throws SQLException;
}
