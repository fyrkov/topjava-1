<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Users</h2>
<%
 List list = (List) request.getAttribute("users");
 out.println("Pick user");
%>
    <form method="post" action="users">
        <select name="users">
        <c:forEach items="${users}" var="user">
           <option value="${user.id}">${user.name}</option>
        </c:forEach>
        </select>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</body>
</html>