<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, java.util.ArrayList, model.Product, dao.ProductDAO" %>
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null) {
        response.sendRedirect("../user/login.jsp");
        return;
    }

    List<Product> allProducts = (List<Product>) request.getAttribute("allProducts");
    if (allProducts == null) {
        try {
            ProductDAO productDAO = new ProductDAO();
            allProducts = productDAO.getAllProducts();
            request.setAttribute("allProducts", allProducts);
            System.out.println("DEBUG: Loaded " + allProducts.size() + " products from DAO in listProductUser.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            allProducts = new ArrayList<>();
            request.setAttribute("allProducts", allProducts);
        }
    }

    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>List Product for User</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        h2 { text-align: center; color: #333; }
        p { text-align: center; margin-bottom: 20px; }
        form { text-align: center; margin-bottom: 20px; }
        input[type="number"] { padding: 5px; margin: 0 5px; }
        button, a.button { padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; text-decoration: none; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <h2>List Product</h2>
    <p>Welcome, <b>${sessionScope.username}</b> (${sessionScope.role})</p>
    <form action="${pageContext.request.contextPath}/products" method="get">
        <label>Search by price:</label>
        <input type="number" step="0.01" name="minPrice" placeholder="Min Price" value="${param.minPrice}">
        <input type="number" step="0.01" name="maxPrice" placeholder="Max Price" value="${param.maxPrice}">
        <button type="submit">Search</button>
    </form>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
        <%-- Thiết lập số sản phẩm hiển thị trên mỗi trang --%>
        <c:set var="pageSize" value="10"/>
        <c:set var="currentPage" value="${param.page != null ? param.page : 1}"/>
        <c:set var="start" value="${(currentPage - 1) * pageSize}"/>
        <c:set var="end" value="${start + pageSize}"/>
        <c:set var="totalProducts" value="${products.size()}"/>
        <c:set var="totalPages" value="${Math.ceil(totalProducts / pageSize)}"/>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Stock</th>
            <th>Import Day</th>
            <th>Action</th>
        </tr>
        <c:forEach var="product" items="${allProducts}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.description}</td>
                <td>${product.stock}</td>
                <td><fmt:formatDate value="${product.importDate}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                <td>
                    <form action="${pageContext.request.contextPath}/carts" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="number" name="quantity" value="1" min="1" style="width: 50px;">
                        <button type="submit" class="button">Add to Cart</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="${pageContext.request.contextPath}/carts" class="button">View Cart</a></p>
    <a href="${pageContext.request.contextPath}/user/logout.jsp"><button>Sign out</button></a>
    <a href="dashboard.jsp"><button>Back to Dashboard</button></a>
</body>
</html>