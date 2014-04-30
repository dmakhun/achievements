<%@ include file="../libs/libs.jsp" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<div class="container">
	<form method="POST">
	<br>
		<div class="row">
			<div class="col-md-offset-3 col-md-4">
				<div class="form-group input-group-lg">
					<select class="form-control" name="competence">
						<c:forEach var="item" items="${list}">
							<option value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-md-2">
				<div class="btn-group btn-group-lg">
					<button class="btn btn-success" style="width: 100%;" type="Submit">Submit</button>
				</div>
			</div>
		</div>
	</form>
	<c:if test="${not empty list_groups }">
	<div class="row">
		<div class="col-md-offset-3 col-md-4">
			<div class="panel panel-default">
			<div class="panel-heading">Groups</div>
			<table class="table table-hover">
				<c:forEach var="item" items="${list_groups}">
					<tr><td><a href="<c:url value="/manager/group/${item.id}" />">${item.name}</a></td></tr>
				</c:forEach>
			</table>
		</div>
	</div>
	</div>
	</c:if>
</div>
</body>
</html>