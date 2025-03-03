package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class UserDAO implements IUserDAO {
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
}
