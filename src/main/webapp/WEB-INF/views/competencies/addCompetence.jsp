<%@ include file="../libs/libs.jsp" %>

<c:if test="${status == 'success'}">
    <div class="alert alert-success">Competence added!</div>
    <hr/>
</c:if>

<div class="row">
    <div class="col-md-3">
        <div class="panel panel-default">
            <div class="panel-heading">All competencies</div>
            <table class="table">
                <c:forEach var="item" items="${competencelist}">
                    <tr>
                        <td>${item.name}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <form method="POST">
        <div class="col-md-9">
            <div class="panel panel-default">
                <div class="panel-heading"><spring:message code="menu.9tab4"/></div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <input class="form-control" type="text" name="competenceName" placeholder=
                                        <spring:message code="competence.name"/>/>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <button class="btn btn-success form-control" type="submit" name="addCompetence">
                                    <spring:message code="add"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>