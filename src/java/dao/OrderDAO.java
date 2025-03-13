package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;


public class OrderDAO implements IOrderDAO{
    // SQL Queries
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM Orders WHERE id = ?";
    private static final String INSERT_ORDER = "INSERT INTO Orders (user_id, total_price, status) VALUES (?, ?, ?)";
   // private static final String INSERT_ORDER_DETAILS = "INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM Orders";
    private static final String UPDATE_ORDER = "UPDATE Orders SET total_price = ?, status = ? WHERE id = ?";
    private static final String DELETE_ORDER = "DELETE FROM Orders WHERE id = ?";

    public Order getOrderById(int id) { 
        Order order = null; 
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SELECT_ORDER_BY_ID)) { 

            ps.setInt(1, id); 
            ResultSet rs = ps.executeQuery(); 

            if (rs.next()) { 
                order = Order.fromResultSet(rs); 
            } else { 
                System.out.println("User not found!"); 
            } 
        } catch (SQLException ex) { 
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex); 
        } 
        return order; 
    }


    // Lấy tất cả đơn hàng
    @Override
    public List<Order> selectAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ORDERS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("totalPrice"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Chèn đơn hàng mới và trả về ID của đơn hàng vừa tạo
   @Override
    public void insertOrder(Order order) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ORDER)) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();
        }
    }
    

    // Cập nhật đơn hàng
    @Override
    public boolean updateOrder(Order order) throws SQLException {
        boolean updated = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ORDER)) {

            ps.setDouble(2, order.getTotalPrice());;
            ps.setString(2, order.getStatus());
            ps.setInt(3, order.getId());

            updated = ps.executeUpdate() > 0;
        }
        return updated;
    }

    // Xóa đơn hàng
    @Override
    public boolean deleteOrder(int id) throws SQLException {
        boolean deleted = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ORDER)) {

            ps.setInt(1, id);
            deleted = ps.executeUpdate() > 0;
        }
        return deleted;
    }
    public void addOrderDetail(int orderId, int productId, int quantity, double price) {
        String sql = "INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Order detail added successfully!");
            } else {
                System.out.println(" Failed to add order detail.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int createOrderReturnId(Order order) throws SQLException {
        int orderId = -1; // Giá trị mặc định nếu có lỗi

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();

            // Lấy ID của đơn hàng vừa tạo
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1); // Lấy giá trị ID tự động tạo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderId;
    }
}
