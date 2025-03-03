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
</head>
<body>
    <h2>Chào mừng, <%= username %>!</h2>
    
    <% if ("admin".equals(role)) { %>
        <a href="user/userList.jsp">Quản lý Người Dùng</a><br>
        <a href="product/listProduct.jsp">Quản lý Sản Phẩm</a><br>
    <% } else { %>
        <a href="product/listProduct.jsp">Xem Sản Phẩm</a><br>
        <a href="cart.jsp">Giỏ Hàng</a><br>
    <% } %>
    
    <a href="user/logout.jsp">Đăng Xuất</a>
</body>
</html>
