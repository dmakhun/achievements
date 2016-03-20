<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="panel panel-default manager-list">
    <input type="hidden" id="listSize" value="${currentSize}"/>
    <table class="table">
        <tr>
            <th>Name</th>
            <th>Surname</th>
            <th>Username</th>
            <th>Email</th>
        </tr>
        <c:forEach var="user" items="${userlist}">
            <tr>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.username}</td>
                <td>${user.email }</td>
                <td>
                    <button class="btn btn-default"
                            onclick="location.href='<c:url value="/admin/removeManager/${user.id}"/>'">Remove
                        manager
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>