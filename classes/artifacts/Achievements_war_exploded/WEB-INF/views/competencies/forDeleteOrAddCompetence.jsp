<%@ include file="../libs/libs.jsp" %>
<c:if test="${statusRemove == 'success'}">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-warning">Competence has been removed</div>
        </div>
    </div>

</c:if>
<c:if test="${statusAdd == 'success'}">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-success">Competence has been added</div>
        </div>
    </div>

</c:if>
<div class="panel panel-default">
    <div class="panel-heading">Competencies</div>
    <table class="table">
        <c:forEach var="item" items="${competencelist}">
            <tr>
                <td><p>${item.name}</p></td>
                <td>
                    <button class="btn btn-default"
                            onclick="location.href='<c:url value="/admin/removeCompetence/${item.id} "/>'">Remove
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="panel panel-default">
    <div class="panel-body">
        <button class="btn btn-default" onclick="location.href='<c:url value ="/addCompetence"/>'">Add Competence
        </button>
    </div>
</div>
