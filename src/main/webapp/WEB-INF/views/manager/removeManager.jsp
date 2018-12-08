<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ include file="../menu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/resources/css/addAchiv.css" />"
          rel="stylesheet" type="text/css"/>
</head>
<body>

<div class="container">
    <center>
        <h1>
            <spring:message code="menu.9tab3"/>
        </h1>
    </center>
    <br/>
    <form method="post">
        <c:if test="${status == 'success'}">
            <div class="row">
                <div class="col-md-offset-3 col-md-6">
                    <div class="alert alert-warning">Manager has been removed!</div>
                </div>
            </div>

        </c:if>

        <div class="row text-center">
            <div class="col-md-offset-3 col-md-4">
                <div class="form-aClass">
                    <select class="form-control" name="userlist">
                        <option selected><spring:message code="select.manager"/></option>
                        <c:forEach var="item" items="${userlist}">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-2">
                <div class="btn-aClass ">
                    <button class="btn btn-success" type="submit">
                        <spring:message code="remove"/>
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>