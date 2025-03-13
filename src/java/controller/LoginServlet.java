package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    // Thêm doGet() để hỗ trợ khi người dùng nhập URL trực tiếp
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("user/login.jsp").forward(request, response);
    }

    //  Xử lý đăng nhập bằng phương thức POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUserName());
            session.setAttribute("role", user.getRole());
            session.setAttribute("user", user);

            // Debug kiểm tra role
            System.out.println("DEBUG: Đăng nhập thành công!");
            System.out.println("DEBUG: User = " + user.getUserName() + ", Role = " + user.getRole());

            // Nếu là Admin -> Chuyển đến products (có quyền quản lý)
            // Nếu là User -> Cũng vào products nhưng không có quyền quản lý
//            response.sendRedirect("dashboard.jsp");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } else {
            System.out.println("DEBUG: Error!");
            response.sendRedirect("user/login.jsp?error=Invalid username or password");
        }
    }
}

