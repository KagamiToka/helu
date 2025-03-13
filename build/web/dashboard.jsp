<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null) {
        response.sendRedirect("user/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        
        body {
            display: flex;
            min-height: 100vh;
            background-color: #f0f2f5;
        }
        
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            padding: 20px;
            color: white;
            position: fixed;
            height: 100%;
        }
        
        .sidebar h2 {
            margin-bottom: 30px;
            font-size: 24px;
        }
        
        .sidebar a {
            display: block;
            color: white;
            text-decoration: none;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        
        .sidebar a:hover {
            background-color: #34495e;
        }
        
        .content {
            margin-left: 250px;
            padding: 20px;
            width: calc(100% - 250px);
        }
        
        .logout {
            position: absolute;
            bottom: 20px;
            width: 210px;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Chào mừng, <%= username %>!</h2>
        
        <% if ("admin".equals(role)) { %>
            <a href="user/listUser.jsp">Quản lý Người Dùng</a>
            <a href="product/listProduct.jsp">Quản lý Sản Phẩm</a>
        <% } else { %>
            <a href="user/listProductUser.jsp">Xem Sản Phẩm</a>
            <a href="cart/cart.jsp">Giỏ Hàng</a>
        <% } %>
        
        <a href="user/logout.jsp" class="logout">Đăng Xuất</a>
    </div>
    
    <div class="content">
        <!-- Nội dung chính sẽ hiển thị ở đây -->
        <h1>Dashboard</h1>
        <p>Chào mừng bạn đến với hệ thống quản lý.</p>
    </div>
</body>
</html>