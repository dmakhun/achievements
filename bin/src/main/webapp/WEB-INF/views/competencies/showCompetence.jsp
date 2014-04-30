<%@ include file="../libs/libs.jsp"%>
<body>
	<div class="container">
		<br>
		<div class="panel panel-default">
			
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<td><h3></h3></td>
						<td><h3><spring:message code="competence" /></h3></td>
						<td><h3><spring:message code="date" /></h3></td>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="iteam" items="${competences}" varStatus="i">
						<tr>
							<td><h4>${i.count}</h4></td>
							<td><h4>${iteam.name}</h4></td>
							<td><h4>
									<fmt:formatDate value="${iteam.date}" pattern="dd-mm-yyyy" />
								</h4></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>