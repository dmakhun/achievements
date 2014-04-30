<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="<c:url value="/resources/css/addAchiv.css" />" type="text/css" />
<title>All achievements</title>
</head>
<body>
	<br>
	<c:if test="${status == 'success'}">
		<div class="alert alert-success">
			<strong>Success!</strong> Achievement type removed!
		</div>
	</c:if>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">Achievements</div>
			<table class="table">
				<tr>
					<th>Name</th>
					<th>Points</th>
					<th>Competence</th>
					<th></th>
				</tr>
				<c:forEach var="item" items="${achievementTypeList}">
					<tr>
						<td>${item.name}</td>
						<td>${item.points}</td>
                        <td>${item.competence.name}</td>
						<td><button class="btn btn-default"
								onclick="location.href='<c:url value="/admin/removeAchievementType/${item.id} "/>'">Remove</button></td>

					</tr>
				</c:forEach>
			</table>
		</div>
		<button class="btn btn-default"
			onclick="location.href='<c:url value ="/admin/achievementtype/add"/>'">Add
			Achievement type</button>
	</div>
</body>
</html>