<%@ include file="../libs/libs.jsp" %>

<div class="panel panel-default">
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <td></td>
            <td><strong><spring:message code="competence"/></strong></td>
            <td><strong><spring:message code="date"/></strong></td>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="iteam" items="${competences}" varStatus="i">
            <tr>
                <td>${i.count}</td>
                <td>${iteam.name}</td>
                <td><fmt:formatDate value="${iteam.date}" pattern="dd-mm-yyyy"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>