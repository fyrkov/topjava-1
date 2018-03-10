<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<head>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<title>Meal Edit</title>
	</head>
	<body>
	    <script>
            $(function() {
                $('input[name=dateTime]').datepicker();
            });
        </script>

		<h3><a href="index.html">Home</a></h3>
		<h2>Meal Edit</h2>

		    <form method="POST" action='meals' name="frmAddMeal">
                Meal ID : <input type="text" readonly="readonly" name="id"
                    value="<c:out value="${meal.id}" />" /> <br />
                calories : <input
                    type="text" name="calories"
                    value="<c:out value="${meal.calories}" />" /> <br />
                description : <input
                    type="text" name="description"
                    value="<c:out value="${meal.description}" />" /> <br />
                dateTime : <input
                    type="text" name="dateTime"
                    value="<c:out value="${meal.dateTime}" />" /> <br />
            <input type="submit" value="Submit" />
            </form>
	</body>
</html>