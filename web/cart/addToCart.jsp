<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="model.Product" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("../user/login.jsp");
        return;
    }
    int productId = Integer.parseInt(request.getParameter("id"));
    ProductDAO productDAO = new ProductDAO();
    Product product = productDAO.getProductById(productId);
    model.Cart cart = (model.Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new model.Cart();
        session.setAttribute("cart", cart);
    }
    cart.addItem(product, 1); // Mặc định thêm 1 sản phẩm
    response.sendRedirect("cart.jsp");
%>