<%@ include file="../libs/libs.jsp" %>
<c:if test="${status == 'success'}">
	<div class="alert alert-success">
		<strong>Success!</strong> Achievement type removed!
	</div>
</c:if>

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
				<td><p>${item.name}</p></td>
				<td><p>${item.points}</p></td>
                      <td><p>${item.competence.name}</p></td>
				<td><button class="btn btn-default"
						onclick="location.href='<c:url value="/admin/removeAchievementType/${item.id} "/>'">Remove</button></td>

			</tr>
		</c:forEach>
	</table>
</div>
		
<div class="panel panel-default">
	<div class="panel-body">
		<div class="btn-group">
			<button class="btn btn-default"onclick="location.href='<c:url value ="/admin/achievementtype/add"/>'">Add Achievement type</button>
		</div>
	</div>
</div>
		