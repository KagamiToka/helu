<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.User, dao.UserDAO" %>
<%
//    // Kiểm tra quyền admin
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null || !"admin".equals(currentUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Lấy danh sách người dùng từ database
    UserDAO userDAO = new UserDAO();
    List<User> userList = userDAO.selectAllUsers();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách người dùng</title>
    <style>
        body { display: flex; min-height: 100vh; background-color: #f0f2f5; }
        .sidebar { width: 250px; background-color: #2c3e50; padding: 20px; color: white; position: fixed; height: 100%; }
        .sidebar h2 { margin-bottom: 30px; font-size: 24px; }
        .sidebar a { display: block; color: white; text-decoration: none; padding: 10px; margin: 10px 0; border-radius: 5px; }
        .sidebar a:hover { background-color: #34495e; }
        .content { margin-left: 250px; padding: 20px; width: calc(100% - 250px); }
        .logout { position: absolute; bottom: 20px; width: 210px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #2c3e50; color: white; }
        button { padding: 5px 10px; background-color: #2c3e50; color: white; border: none; border-radius: 3px; cursor: pointer; }
        a { text-decoration: none; }
    </style>
</head>
<body>
    <h2>List User</h2>
    <a href="${pageContext.request.contextPath}/users?action=create">Create New User</a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>User Name</th>
            <th>Role</th>
<!--            <th>Status</th>-->
            <th>Actions</th>
        </tr>
        <% for (User user : userList) { %>
        <tr>
            <td><%= user.getUserID() %></td>
            <td><%= user.getUserName() %></td>
            <td><%= user.getRole() %></td>

            <td>
                <a href="${pageContext.request.contextPath}/users?action=edit&id=<%= user.getUserID() %>"><button>Edit</button></a>
                <a href="${pageContext.request.contextPath}/users?action=delete&id=<%= user.getUserID() %>" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
        <% } %>
    </table>
    <br>
    <a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a>
</body>
</html>