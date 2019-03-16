<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<div class="container">
    <c:forEach var="competence" items="${competences}">
        <c:if test="${not empty competence.users}">
            <div class="panel panel-default">
                <div class="panel-heading">${competence.name}</div>
                <table class="table">
                    <c:forEach var="user" items="${competence.users}">
                        <form method="POST">
                            <tr>
                                <td>${user.name}</td>
                                <td>${user.surname}</td>
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>
                                <td>
                                    <input type="hidden" name="user_id" value="${user.id}"/>
                                    <input type="hidden" name="competence_id"
                                           value="${competence.id}"/>
                                    <select class="form-control" name="group_id">
                                        <c:forEach var="group"
                                                   items="${competence.groups}">
                                            <option value="${group.id}">${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-default" type="submit">Save</button>
                                    </div>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </c:forEach>
</div>

