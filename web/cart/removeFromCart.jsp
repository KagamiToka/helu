<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("../user/login.jsp");
        return;
    }
    int productId = Integer.parseInt(request.getParameter("id"));
    model.Cart cart = (model.Cart) session.getAttribute("cart");
    if (cart != null) {
        cart.removeItem(productId);
    }
    response.sendRedirect("cart.jsp");
%>