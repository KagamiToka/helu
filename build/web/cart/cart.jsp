<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="dao.ProductDAO, model.Product, model.Cart, model.CartItem" %>
<%
    // Chỉ kiểm tra session nếu không được forward từ servlet
    if (request.getAttribute("cart") == null) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/user/login.jsp");
            return;
        }
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
            margin: 20px auto;
            font-size: 18px;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .update-btn {
            background-color: blue;
            color: white;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }
        .remove-btn {
            background-color: red;
            color: white;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }
        .continue-btn, .checkout-btn {
            text-decoration: none;
            background-color: green;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            margin: 10px;
            display: inline-block;
        }
    </style>
</head>
<body>
    <h2 style="text-align: center;">Shopping Cart</h2>
    <c:set var="cart" value="${requestScope.cart != null ? requestScope.cart : sessionScope.cart}" />

    <c:choose>
        <c:when test="${empty cart.items or cart.items.size() == 0}">
            <p style="text-align: center; font-size: 20px;">Your cart is empty!</p>
        </c:when>
        <c:otherwise>
            <table align="center">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>

                <c:set var="totalCost" value="0" />

                <c:forEach var="item" items="${cart.items}">
                    <c:set var="subtotal" value="${item.quantity * item.product.price}" />
                    <tr>
                        <td>${item.product.id}</td>
                        <td>${item.product.name}</td>
                        <td><fmt:formatNumber value="${item.product.price}" pattern="#,##0.00"/> VND</td>
                        <td>
                            <form action="<%= request.getContextPath() %>/carts" method="post" style="display: flex; justify-content: center; align-items: center;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="productId" value="${item.product.id}">
                                <input type="number" name="quantity" value="${item.quantity}" min="1" style="width: 50px; text-align: center;">
                                <button type="submit" class="update-btn">Update</button>
                            </form>
                        </td>
                        <td><fmt:formatNumber value="${subtotal}" pattern="#,##0.00"/> VND</td>
                        <td>
                            <form action="<%= request.getContextPath() %>/carts" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="productId" value="${item.product.id}">
                                <button type="submit" class="remove-btn">Remove</button>
                            </form>
                        </td>
                    </tr>
                    <c:set var="totalCost" value="${totalCost + subtotal}" />
                </c:forEach>

                <tr>
                    <td colspan="4" style="text-align: right; font-weight: bold;">Total Cost:</td>
                    <td colspan="2" style="font-weight: bold; font-size: 20px;"><fmt:formatNumber value="${totalCost}" pattern="#,##0.00"/> VND</td>
                </tr>
            </table>

            <div style="text-align: center;">
                <a href="<%= request.getContextPath() %>/products?action=user" class="continue-btn">Continue Shopping</a>
                <a href="<%= request.getContextPath() %>/checkout" class="checkout-btn">Checkout</a>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>