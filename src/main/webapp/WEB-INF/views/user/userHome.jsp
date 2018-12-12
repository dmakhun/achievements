<%@ include file="../libs/libs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="utf-8" %>
<html>
<body background="red">
<br>
<center><b>User Home</b></center>
<br>
<div class="container">
    <p>Groups</p>
    <div class="panel panel-default">
        <table class="table">
            <c:forEach var="item" items="${aGroups}">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.dateOpened}</td>
                    <td>${item.dateClosed}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div class="container">
    <p>Achievements</p>
    <p></p>
    <div class="panel panel-default">
        <table class="table">
            <c:forEach var="item" items="${achievements}">
                <tr>
                    <td>${item.achievementType.name}</td>
                    <td>${item.achievementType.points}</td>
                    <td>${item.comment}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<br>
<div class="container">
    <sec:authorize access="hasAnyRole('ROLE_USER')">
        <%@ include file="simpleUserProfile.jsp" %>
    </sec:authorize>
</div>
</body>
</html>