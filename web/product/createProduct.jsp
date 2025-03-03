<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Product</title>
    </head>
    <body>
        <h2>Create product</h2>
        <form action="products" method="post">
            <input type="hidden" name="action" value="add">

            <label>Name:</label>
            <input type="text" name="name" required><br>

            <label>Price:</label>
            <input type="number" step="0.01" name="price" required><br>

            <label>Description:</label>
            <input type="text" name="description" required><br>

            <label>Stock:</label>
            <input type="number" name="stock" required><br>

            <input type="submit" value="Create Product">
        </form>

        <a href="products"><button>Back</button></a>
    </body>
</html>
