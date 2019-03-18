<%@ include file="../libs/libs.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Ratings</title>

</head>
<body>
<h2><strong>
    <center>Ratings</center>
</strong></h2>
<br>
<div class="container">
    <div class="panel panel-default">
        <table class="table">
            <thead>
            <tr>
                <td><strong>Place</strong></td>
                <td><strong>User</strong></td>
                <td><strong>Points</strong></td>
            </tr>
            </thead>
            <c:forEach var="user" items="${users}" varStatus="i">
                <tr>
                    <td>${i.count}.</td>
                    <td>${user.name} ${user.surname}</td>
                    <td>${user.points} </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<br>
</body>
</html>