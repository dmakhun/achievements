<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../libs/libs.jsp" %>
<html>
<body>
<form action="" method="POST">
    <div class="container">
        <c:if test="${status eq 'success'}">
            <div class="alert alert-success"><strong>Success!</strong> Achievement has been added!</div>
        </c:if>
        <div class="panel panel-default">
            <div class="panel-heading">Achievements</div>
            <table class="table">
                <c:forEach var="item" items="${achievements}">

                    <tr>
                        <form method="POST">
                            <td><input type="hidden" name="achievement_type_id"
                                       value="${item.id}"/> ${item.name}</td>
                            <td>${item.id}</td>
                            <td>${item.points}</td>
                            <td><input type="text" name="comment" class="form-control"
                                       placeholder="comment"/></td>
                            <td>
                                <button class="btn btn-default" type="submit">Award!</button>
                            </td>
                        </form>
                    </tr>

                </c:forEach>
            </table>
        </div>
    </div>
</form>


</body>
</html>