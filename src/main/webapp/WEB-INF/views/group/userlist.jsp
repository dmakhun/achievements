<%@ include file="../libs/libs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/addAchiv.css" />" type="text/css"/>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">Users of aGroup</div>
        <table class="table">
            <c:forEach var="item" items="${users}">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.surname}</td>
                    <td>${item.username}</td>
                    <td>${item.email}</td>
                    <td><a
                            href="<c:url value="/manager/user/award/${item.id}" />">Award</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>