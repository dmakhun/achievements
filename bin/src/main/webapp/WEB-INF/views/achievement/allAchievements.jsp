<%@ include file="../libs/libs.jsp" %>
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
