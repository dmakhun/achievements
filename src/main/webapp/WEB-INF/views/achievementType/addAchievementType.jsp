<%@ include file="../libs/libs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<br>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">Existing achievement types of ${competence.name} competence</div>
        <table class="table">
            <tr>
                <th>Name</th>
                <th>points</th>
                <th></th>
            </tr>
            <c:forEach var="achievement" items="${achievements}">
                <tr>
                    <td>${achievement.name}</td>
                    <td>${achievement.points}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<c:if test="${status == 'success'}">
    Achievement Type added!
</c:if>
<c:if test="${status == 'fail'}">
    You must enter a number in the field "points"!
</c:if>
<div class="container">
    <center>
        <h1>
            <spring:message code="menu.4tab4"/>
        </h1>
    </center>
    <form action="" method="post">

        <br>
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <div class="form-group">
                    <input class="form-control" type="text" name="name"
                           placeholder=
                           <spring:message code="achievement.name"/>/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <div class="form-group">
                    <input class="form-control" type="text" name="points"
                           placeholder=
                           <spring:message code="achievement.points"/>/>
                </div>
            </div>
        </div>

        <div class="row text-center">
            <div class="col-md-offset-4 col-md-4">
                <div class="btn-group btn-group-lg">
                    <button class="btn btn-success" type="submit">
                        <spring:message code="add"/>
                    </button>
                </div>
            </div>
        </div>
    </form>

</div>
</body>
