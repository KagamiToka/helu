<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Information</title>
    </head>
    <body>
        <h2>Welcome, ${username}!</h2>

        <table border="1">
            <tr>
                <th>UserID</th>
                <th>UserName</th>
                <th>Password</th>
                <th>Role</th>
            </tr>
            <tr>
                <td>${userID}</td>
                <td>${username}</td>
                <td>*****</td>  <!-- Không hiển thị mật khẩu thật -->
                <td>${role}</td>
            </tr>
        </table>

            <a href="logout.jsp"><button>Sign out</button></a>
    </body>
</html>
