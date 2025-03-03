<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.User, dao.UserDAO" %>
<%
    // Kiểm tra quyền admin
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null || !"admin".equals(currentUser.getRole())) {
        response.sendRedirect("user/login.jsp");
        return;
    }
    
    // Lấy danh sách người dùng từ database
    UserDAO userDAO = new UserDAO();
    List<User> userList = userDAO.getAllUsers();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách người dùng</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h2>Danh sách người dùng</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Tên đăng nhập</th>
            <th>Vai trò</th>
        </tr>
        <% for (User user : userList) { %>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getUserName() %></td>
            <td><%= user.getRole() %></td>
        </tr>
        <% } %>
    </table>
    <br>
    <a href="dashboard.jsp">Quay lại Dashboard</a>
</body>
</html>