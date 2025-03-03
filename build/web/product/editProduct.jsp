<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Product</title>
    </head>
    <body>
        <h2>Edit product</h2>
        <form method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${product.id}">

            <label>Name:</label> 
            <input type="text" name="name" value="${product.name}" required><br>
            <label>Price:</label>  
            <input type="number" step="0.01" name="price" value="${product.price}" required><br>
            <label>Description:</label>  
            <input type="text" name="description" value="${product.description}" required><br>
            <label>Stock:</label>  
            <input type="number" name="stock" value="${product.stock}" required><br>
            
            <input type="submit" value="Update">
        </form>

            <a href="products"><button>Back</button></a>
    </body>
</html>
