<%@ include file="../libs/libs.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Ratings</title>
</head>
<body>
	<div class="container">
		<p>Users</p>
		<div class="panel panel-default">
			<table class="table">
				<c:forEach var="item" items="${users}">
					<tr>
						<td>${item.name}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="container">
		<p>Points</p>
		<p></p>
		<div class="panel panel-default">
			<table class="table">
				<c:forEach items="${points}">
					<tr>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<br>
</body>
</html>
