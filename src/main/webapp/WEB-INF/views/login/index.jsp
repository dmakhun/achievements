<%@ include file="../libs/libs.jsp" %>

<div class="row">
    <div class="col-md-2">
        Specialties:
    </div>
    <div class="col-md-10">
        <c:forEach var="item" items="${competences}">
            ${item.name}
        </c:forEach>
    </div>
</div>

<hr/>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Groups will be opened recently
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