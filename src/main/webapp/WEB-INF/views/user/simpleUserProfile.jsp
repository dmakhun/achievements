<%@ include file="../libs/libs.jsp" %>
<%@ page pageEncoding="utf-8" %>
<c:if test="${not empty waiting_attend}">
    Speciality:
    <c:forEach var="item" items="${waiting_attend}">
       ${item.name}
    </c:forEach>
    </c:if>

    <c:choose>
        <c:when test="${not empty competences}">
            <form method="GET">
                Acquire to competence: <select name="competence">
                    <c:forEach var="item" items="${competences}">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>

                <button type="submit">Submit</button>
            </form>
        </c:when>

        <c:otherwise>
    </c:otherwise>
    </c:choose>