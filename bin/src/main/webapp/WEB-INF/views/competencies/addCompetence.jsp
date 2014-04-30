<%@ include file="../libs/libs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<br>
	<div class="container" style="background-color: white">
		<div class="row">
			<div class="col-md-2">All competencies:</div>
			<div class="col-md-10">
				<c:forEach var="item" items="${competencelist}">
					<div class="badge">${item.name}</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="container">
		<center>
			<h1>
				<spring:message code="menu.9tab4" />
			</h1>
		</center>
		<br />

		<form action="" method="post">
			<c:if test="${status == 'success'}">
		Competence added!
		</c:if>
			<div class="row">
				<div class="col-md-offset-4 col-md-4">
					<div class="form-group">
						<input class="form-control" type="text" name="competenceName"
							placeholder=<spring:message code="competence.name"/> />
					</div>
				</div>
			</div>
			<div class="row text-center">
				<div class="col-md-offset-4 col-md-4">
					<div class="btn-group btn-group-lg">
						<button class="btn btn-success" type="submit" name="addCompetence">
							<spring:message code="add" />
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>