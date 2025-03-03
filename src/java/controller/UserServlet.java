//package controller;
//
//import dao.UserDAO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import model.User;
//
//@WebServlet("/users")
//public class UserServlet extends HttpServlet {
//    private final UserDAO userDAO = new UserDAO();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String role = (String) session.getAttribute("role");
//
//        if (role == null || !"admin".equals(role)) {
//            response.sendRedirect("products?error=notAdmin");
//            return;
//        }
//
//        String action = request.getParameter("action");
//        if ("edit".equals(action)) {
//            int id = Integer.parseInt(request.getParameter("id"));
//            User user = userDAO.getUserById(id);
//            request.setAttribute("user", user);
//            request.getRequestDispatcher("user/editUser.jsp").forward(request, response);
//        } else {
//            List<User> userList = userDAO.getAllUsers();
//            request.setAttribute("userList", userList);
//            request.getRequestDispatcher("user/listUser.jsp").forward(request, response);
//        }
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String role = (String) session.getAttribute("role");
//
//        if (role == null || !"admin".equals(role)) {
//            response.sendRedirect("products?error=notAdmin");
//            return;
//        }
//
//        String action = request.getParameter("action");
//        if ("update".equals(action)) {
//            int id = Integer.parseInt(request.getParameter("id"));
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
//            String userRole = request.getParameter("role");
//
//            userDAO.updateUser(new User(id, username, password, userRole));
//            response.sendRedirect("users");
//        } else if ("delete".equals(action)) {
//            int id = Integer.parseInt(request.getParameter("id"));
//            userDAO.deleteUser(id);
//            response.sendRedirect("users");
//        }
//    }
//}
//
