<%@ include file="../libs/libs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<br>
	<div class="container">
		<c:if test="${statusRemove == 'success'}">
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
					<div class="alert alert-warning">Competence has been removed</div>
				</div>
			</div>

		</c:if>
			<c:if test="${statusAdd == 'success'}">
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
					<div class="alert alert-success">Competence has been added</div>
				</div>
			</div>

		</c:if>
		<div class="panel panel-default">
			<div class="panel-heading">Competencies</div>
			<table class="table">
				<tr>
					<th>Competence</th>
					<th></th>
				</tr>
				<c:forEach var="item" items="${competencelist}">
					<tr>
						<td>${item.name}</td>
						<td><button class="btn btn-default"
								onclick="location.href='<c:url value="/admin/removeCompetence/${item.id} "/>'">Remove
							</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<button class="btn btn-default"
			onclick="location.href='<c:url value ="/addCompetence"/>'">Add
			Competence</button>
	</div>

</body>
</html>