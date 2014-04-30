<%@ include file="../libs/libs.jsp"%>

<div class="container" style="background-color: white">
	<div class="row">
		<div class="col-md-2">
			<p class="lead pull-right">Specialties:</p>
		</div>
		<div class="col-md-10">
			<p class="lead">
				<c:forEach var="item" items="${competences}">
				${item.name}
			</c:forEach>
			</p>
		</div>
	</div>


	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3>Groups will be opened recently</h3>
				</div>
				<table class="table table-hover">
					<tr>
						<th>Name</th>
						<th>Open date</th>
						<th>Competence</th>
					</tr>
					<c:forEach var="list" items="${groups_lists}">
						<c:forEach var="item" items="${list}">
							<tr>
								<td>${item.name}</td>
								<td>${item.opened}</td>
								<td>${item.competence.name}</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>