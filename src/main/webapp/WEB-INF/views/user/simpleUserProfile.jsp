<%@ include file="../libs/libs.jsp" %>
<%@ page pageEncoding="utf-8" %>
<c:if test="${not empty userCompetences}">
    Speciality:
    <c:forEach var="item" items="${userCompetences}">
        ${item.name}
    </c:forEach>
</c:if>

<c:choose>
    <c:when test="${not empty availableCompetences}">
        <form method="post">
            Acquire to competence: <select name="competence">
            <c:forEach var="item" items="${availableCompetences}">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>

            <button type="submit">Submit</button>
        </form>
    </c:when>

    <c:otherwise>
    </c:otherwise>
</c:choose>