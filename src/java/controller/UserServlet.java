package controller;

import dao.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import org.apache.jasper.compiler.ErrorDispatcher;
import utils.ErrorDialog;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

       User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                User user = userDAO.selectUser(id);
//                ErrorDialog.showError("id");
                if (user != null) {
//                    ErrorDialog.showError("id");
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/user/editUser.jsp").forward(request, response);
                } else {
                    List<User> userList = userDAO.selectAllUsers();
                    request.setAttribute("userList", userList);
                    request.setAttribute("error", "Người dùng không tồn tại.");
                    request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                List<User> userList = userDAO.selectAllUsers();
                request.setAttribute("userList", userList);
                request.setAttribute("error", "ID không hợp lệ.");
                request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
            }
        } else if ("create".equals(action)) {
            request.getRequestDispatcher("/user/createUser.jsp").forward(request, response);
        } else {
            List<User> userList = userDAO.selectAllUsers();
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (role == null || !"admin".equals(role)) {
            response.sendRedirect("user/login.jsp?error=notAdmin");
            return;
        }

        String action = request.getParameter("action");
        switch (action != null ? action : "") {
            case "update":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String userRole = request.getParameter("role");

                    userDAO.updateUser(new User(id, username, password, userRole));
                    response.sendRedirect("users");
                } catch (SQLException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    List<User> userList = userDAO.selectAllUsers();
                    request.setAttribute("userList", userList);
                    request.setAttribute("error", "Cập nhật thất bại.");
                    request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    List<User> userList = userDAO.selectAllUsers();
                    request.setAttribute("userList", userList);
                    request.setAttribute("error", "ID không hợp lệ.");
                    request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
                }
                break;

            case "delete":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    userDAO.deleteUser(id);
                    List<User> userList = userDAO.selectAllUsers();
                    request.setAttribute("userList", userList);
                    request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    List<User> userList = userDAO.selectAllUsers();
                    request.setAttribute("userList", userList);
                    request.setAttribute("error", "Xóa thất bại.");
                    request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    List<User> userList = userDAO.selectAllUsers();
                    request.setAttribute("userList", userList);
                    request.setAttribute("error", "ID không hợp lệ.");
                    request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
                }
                break;

            case "add":
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String userRole = request.getParameter("role");
                try {
                    userDAO.insertUser(new User(0, username, password, userRole));
                    response.sendRedirect("users");
                } catch (SQLException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("error", "Thêm người dùng thất bại.");
                    request.getRequestDispatcher("/user/createUser.jsp").forward(request, response);
                }
                break;

            default:
                // Trường hợp action không khớp với bất kỳ giá trị nào
                List<User> userList = userDAO.selectAllUsers();
                request.setAttribute("userList", userList);
                request.setAttribute("error", "Hành động không hợp lệ.");
                request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
                break;
        }
    }
}