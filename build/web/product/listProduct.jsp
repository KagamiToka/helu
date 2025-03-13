<%@page import="dao.ProductDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList, model.Product" %>
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null || !"admin".equals(role)) {
        response.sendRedirect("/Filter_Demo/login");
        return;
    }

    // Kiểm tra nếu allProducts là null, gán danh sách rỗng để tránh lỗi
    List<Product> allProducts = (List<Product>) request.getAttribute("allProducts");
    if (allProducts == null) {
        try {
            ProductDAO productDAO = new ProductDAO();
            allProducts = productDAO.getAllProducts();
            request.setAttribute("allProducts", allProducts);
            System.out.println("DEBUG: Loaded " + allProducts.size() + " products from DAO in JSP");
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
    <title>List Product</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        h2 { text-align: center; color: #333; }
        p { text-align: center; margin-bottom: 20px; }
        form { text-align: center; margin-bottom: 20px; }
        input[type="number"] { padding: 5px; margin: 0 5px; }
        button, a.button { padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; text-decoration: none; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #2c3e50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .error { color: red; text-align: center; }
        .pagination { text-align: center; margin-top: 20px; }
        a { margin: 0 5px; }
    </style>
</head>
<body>
    <h2>List product</h2>
    <p>Welcome, <b>${sessionScope.username}</b> (${sessionScope.role})</p>
    <form action="${pageContext.request.contextPath}/products" method="get">
        <label>Search by price:</label>
        <input type="number" step="0.01" name="minPrice" placeholder="Min Price" value="${param.minPrice}">
        <input type="number" step="0.01" name="maxPrice" placeholder="Max Price" value="${param.maxPrice}">
        <button type="submit">Search</button>
    </form>
    <c:if test="${sessionScope.role eq 'admin'}">
        <a href="${pageContext.request.contextPath}/products?action=create" class="button">Create</a>
    </c:if>
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
                    <c:if test="${sessionScope.role eq 'admin'}">
                        <a href="${pageContext.request.contextPath}/products?action=edit&id=${product.id}" class="button">Edit</a> |
                        <a href="${pageContext.request.contextPath}/products?action=delete&id=${product.id}" class="button" onclick="return confirm('Xác nhận xóa?');">Delete</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <!-- Nút chuyển trang (chưa triển khai đầy đủ) -->
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="products?page=${currentPage - 1}"><button>Previous</button></a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <a href="products?page=${i}"><button>${i}</button></a>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="products?page=${currentPage + 1}"><button>Next</button></a>
        </c:if>
    </div>
    <a href="${pageContext.request.contextPath}/user/logout.jsp"><button>Sign out</button></a>
    <a href="${pageContext.request.contextPath}/dashboard.jsp"><button>Back to Dashboard</button></a>
</body>
</html>