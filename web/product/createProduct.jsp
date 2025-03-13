<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null || !"admin".equals(role)) {
        response.sendRedirect("../user/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản Phẩm</title>
    <style>
        body { display: flex; min-height: 100vh; background-color: #f0f2f5; }
        .sidebar { width: 250px; background-color: #2c3e50; padding: 20px; color: white; position: fixed; height: 100%; }
        .sidebar h2 { margin-bottom: 30px; font-size: 24px; }
        .sidebar a { display: block; color: white; text-decoration: none; padding: 10px; margin: 10px 0; border-radius: 5px; }
        .sidebar a:hover { background-color: #34495e; }
        .content { margin-left: 350px; padding: 20px; width: calc(100% - 250px); }
        .logout { position: absolute; bottom: 20px; width: 210px; }
        form { max-width: 400px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        label { display: block; margin: 10px 0 5px; }
        input, textarea { width: 100%; padding: 8px; margin-bottom: 10px; }
        button { background-color: #2c3e50; color: white; padding: 10px; border: none; border-radius: 5px; cursor: pointer; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Chào mừng, <%= username %>!</h2>
        <a href="../dashboard.jsp">Dashboard</a>
        <a href="../user/listUser.jsp">Quản lý Người Dùng</a>
        <a href="listProduct.jsp">Quản lý Sản Phẩm</a>
        <a href="../user/logout.jsp" class="logout">Đăng Xuất</a>
    </div>
    
    <div class="content">
        <h1>Add New Product</h1>
        <form action="products" method="post">
            <input type="hidden" name="action" value="add">
            <label>Name:</label>
            <input type="text" name="name" required>
            <label>Price:</label>
            <input type="number" step="0.01" name="price" required>
            <label>Description:</label>
            <textarea name="description" required></textarea>
            <label>Stock:</label>
            <input type="number" name="stock" required>
            <button type="submit">Create</button>
        </form>
    </div>
</body>
</html>