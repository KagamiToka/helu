<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null || !"admin".equals(role)) {
        response.sendRedirect("../user/login.jsp");
        return;
    }

    User user = (User) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect("listUser.jsp?error=userNotFound");
        return;
    }

    // Lấy thông báo lỗi nếu có
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
    <style>
        body { display: flex; min-height: 100vh; background-color: #f0f2f5; }
        .sidebar { width: 250px; background-color: #2c3e50; padding: 20px; color: white; position: fixed; height: 100%; }
        .sidebar h2 { margin-bottom: 30px; font-size: 24px; }
        .sidebar a { display: block; color: white; text-decoration: none; padding: 10px; margin: 10px 0; border-radius: 5px; }
        .sidebar a:hover { background-color: #34495e; }
        .content { margin-left: 250px; padding: 20px; width: calc(100% - 250px); }
        .logout { position: absolute; bottom: 20px; width: 210px; }
        form { max-width: 400px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        label { display: block; margin: 10px 0 5px; }
        input, select { width: 100%; padding: 8px; margin-bottom: 10px; }
        button { background-color: #2c3e50; color: white; padding: 10px; border: none; border-radius: 5px; cursor: pointer; }
        .error { color: red; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Chào mừng, <%= username %>!</h2>
        <a href="${pageContext.request.contextPath}/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/user/listUser.jsp">Quản lý Người Dùng</a>
        <a href="${pageContext.request.contextPath}/product/listProduct.jsp">Quản lý Sản Phẩm</a>
        <a href="${pageContext.request.contextPath}/user/logout.jsp" class="logout">Đăng Xuất</a>
    </div>
    
    <div class="content">
        <h1>Edit User</h1>
        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } %>
        <form action="${pageContext.request.contextPath}/users" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${user.userID}">
            <label>Name:</label>
            <input type="text" name="username" value="${user.userName}" required>
            <label>Password:</label>
            <input type="password" name="password" value="${user.password}" required>
            <label>Role:</label>
            <select name="role" required>
                <option value="user" ${user.role == 'user' ? 'selected' : ''}>user</option>
                <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>admin</option>
            </select>
            <button type="submit">Update</button>
        </form>
    </div>
</body>
</html>