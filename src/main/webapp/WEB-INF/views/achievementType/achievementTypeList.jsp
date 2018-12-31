<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="panel panel-default">
    <div class="panel-heading">Existing achievement types of
        ${competence.name} competence
        <div style="float: right">
            <div class="btn-group btn-group-xs">
                <button class="btn btn-default" onclick="displayDialog('${competenceId}');">add achievement type
                </button>
            </div>
        </div>
    </div>
    <table class="table">
        <tr>
            <th>Name</th>
            <th>points</th>
        </tr>
        <c:forEach var="achievement" items="${achievements}">
            <tr>
                <td>${achievement.name}</td>
                <td>${achievement.points}</td>
            </tr>
        </c:forEach>
    </table>
</div>