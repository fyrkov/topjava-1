<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<head>
		<title>Meals</title>
	</head>
	<body>
		<h3><a href="index.html">Home</a></h3>
		<h2>Meals  <a href="meals?action=add">[Add]</a></h2>
		<table class="w3-table w3-striped w3-border">
			<tr>
				<td>
					Date and Time
				</td>
				<td>
					Description
				</td>
				<td>
					Calories
				</td>
                <td>
                </td>
                <td>
                </td>
			</tr>
			<c:forEach var="item" items="${mealsList}">
				<tr>
					<td>
						<c:out value="${item.dateTime}"/>
					</td>
					<td>
						<c:out value="${item.description}"/>
					</td>
                    <c:if test="${item.exceed}">
					    <c:set var="caloriesColor" value="red"/>
                    </c:if>
                    <c:if test="${!item.exceed}">
					    <c:set var="caloriesColor" value=""/>
                    </c:if>
					<td style="background-color:${caloriesColor}">
						<c:out value="${item.calories}"/>
					</td>
                    <td><a href="meals?action=edit&Id=<c:out value="${item.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&Id=<c:out value="${item.id}"/>">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>