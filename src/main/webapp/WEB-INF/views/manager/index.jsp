<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../menu.jsp"%>
<%@ page errorPage="errorPage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Achievements</title>

<link rel="stylesheet"
	href="<c:url value="/resources/css/addAchiv.css" />" type="text/css" />
</head>
<body>
	<div class="container" style="background-color: white">
		<div class="row">
			<div class="col-md-2">Спеціальності:</div>
			<div class="col-md-10">
				<c:forEach var="item" items="${competences}">
					<div class="badge">${item.name}</div>
				</c:forEach>
			</div>
		</div>
      

		<div class="row">
		  <div class="col-md-12">
		      <c:forEach var="list" items="${groups_lists}">
		          <c:forEach var="item" items="${list}">
		              <p>Група: ${item.name}, відкриття: ${item.opened}</p>
		          </c:forEach>
		      </c:forEach>
		  </div>
		</div>
	</div>
	<br><h1>


</body>
</html>