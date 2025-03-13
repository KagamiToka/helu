<%@ page language="java" contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html> 
<head> 
    <title>User Management Application</title> 
</head> 
<body> 
<center> 
    <h1>User Management</h1> 
    <h2> 
        <a href="user/listUser.jsp">List All Users</a> 
    </h2> 
</center> 
<div class="content">
        <h1>Create New User</h1>
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } %>
        <form action="${pageContext.request.contextPath}/users" method="post">
            <input type="hidden" name="action" value="add">
            <label>Name:</label>
            <input type="text" name="username" required>
            <label>Password:</label>
            <input type="password" name="password" required>
            <label>Role:</label>
            <select name="role">
                <option value="user">user</option>
                <option value="admin">admin</option>
            </select>
            <button type="submit">Create</button>
        </form>
    </div>
</body> 
</html>
