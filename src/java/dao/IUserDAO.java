package dao;

import model.User;

public interface IUserDAO {
    public User login(String username, String password);
}
