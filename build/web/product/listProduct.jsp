<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Product</title>
    </head>
    <body>
        <h2>List product</h2>
        <p>Welcome, <b>${sessionScope.username}</b> (${sessionScope.role})</p>
        <form action="products" method="get">
            <label>Search by price:</label>
            <input type="number" step="0.01" name="minPrice" placeholder="Min Price" value="${param.minPrice}">
            <input type="number" step="0.01" name="maxPrice" placeholder="Max Price" value="${param.maxPrice}">
            <button type="submit">Search</button>
        </form>
        <!-- Chỉ Admin mới thấy nút "Thêm sản phẩm" -->
        
            <a href="products?action=create"><button>Create</button></a>
       
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
            <c:forEach var="p" items="${productList}">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td>${p.price}</td>
                    <td>${p.description}</td>
                    <td>${p.stock}</td>
                    <td>${p.importDate}</td>
                    <!-- Chỉ Admin mới thấy nút "Sửa" & "Xóa" -->
                    
                        <td>
                            <a href="products?action=edit&id=${p.id}"><button>Edit</button></a> |
                            <a href="products?action=delete&id=${p.id}" onclick="return confirm('Xác nhận xóa?');"><button>Delete</button></a>
                        </td>
                    
                </tr>
            </c:forEach>
        </table>
            <!-- Nút chuyển trang -->
        <div>
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
        <a href="user/logout.jsp"><button>Sign out</button></a>
    </body>
</html>
