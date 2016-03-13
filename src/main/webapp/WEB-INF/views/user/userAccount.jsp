<%@ include file="../libs/libs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<center>
    <h1>Welcome</h1>
</center>

<sec:authorize ifAnyGranted='ROLE_USER'>
    <%@ include file="simpleUserProfile.jsp" %>
</sec:authorize>
</body>
</html>