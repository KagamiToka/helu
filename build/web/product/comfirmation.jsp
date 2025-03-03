<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="product" class="model.Product" scope="session"/>
<jsp:setProperty name="product" property="*"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Tin Sản Phẩm</title>
</head>
<body>
    <h2>Thông tin đã nhập:</h2>
    <p>Tên sản phẩm: <jsp:getProperty name="product" property="name" /></p>
    <p>Giá: <jsp:getProperty name="product" property="price" /></p>
    <p>Mô tả: <jsp:getProperty name="product" property="description" /></p>
    <p>Số lượng: <jsp:getProperty name="product" property="stock" /></p>
    <p>Ngày nhập: <jsp:getProperty name="product" property="importDate" /></p>
    <button><a href="/lab4CRUD/products?action=testBean">Xác nhận</a></button>
</body>
</html>
