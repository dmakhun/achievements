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
        <c:forEach var="competence" items="${competences}" varStatus="i">
            <tr>
                <td>${i.count}</td>
                <td>${competence.name}</td>
                <fmt:parseDate value="${competence.dateCreated}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date"
                                pattern="dd-MM-yyyy"/>
                <td>${newParsedDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>