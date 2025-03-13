package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO implements IUserDAO {
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users1 WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users1";
    private static final String INSERT_USER = "INSERT INTO Users1 (name, password, role) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM Users1 WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE Users1 SET name = ?, password = ?, role = ? WHERE id = ?";
    
    public User login(String username, String password) {
        String sql = "SELECT * FROM Users1 WHERE name = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            System.out.println("DEBUG: SQL Query: " + stmt.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("DEBUG: Login success!");
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getString("role"));
            } else {
                System.out.println("DEBUG: Wrong password or username!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User selectUser(int id) {
        try(Connection conn = DBConnection.getConnection()) {
            if(conn!=null){
                PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID);

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("User found: ");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Username: " + rs.getString("username"));
                    System.out.println("Password: " + rs.getString("password"));
                    System.out.println("Role: " + rs.getString("role"));                    
                    } else {
                        System.out.println("User not found!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection()) {
                PreparedStatement preparedStatement =
                conn.prepareStatement(SELECT_ALL_USERS);
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String passwword = rs.getString("password");
                    String role = rs.getString("role");                    
                    users.add(new User(id, name, passwword, role));
                }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }
    
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    
    public void insertUser(User user) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeUpdate();
        }
    }
    
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setInt(4, user.getUserID());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
