package dao;

import java.sql.SQLException;
import java.util.List;
import model.User;

public interface IUserDAO {
    public User selectUser(int id); 
    public List<User> selectAllUsers(); 
    public User login(String username, String password);
    public void insertUser(User user) throws SQLException;
    public boolean deleteUser(int id) throws SQLException;
    public boolean updateUser(User user) throws SQLException;
}
