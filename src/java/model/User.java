package model;

public class User {
    private int userID;
    private String userName;
    private String password;
    private String role;

    public User() {
    }

    public User(int userID, String userName, String password, String role) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public int getUserID() { return userID; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", userName=" + userName + ", password=" + password + ", role=" + role + '}';
    }
    
}
