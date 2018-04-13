<%@ include file="../libs/libs.jsp" %>

<c:if test="${not empty error}">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <div class="alert alert-danger">
                <spring:message code="registration.error"/>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty usernameSurname}">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <div class="alert alert-success">
                    ${usernameSurname}<br/>
                <spring:message code="registration.userMessage"/>
            </div>
        </div>
    </div>
</c:if>

<form method="post" action="<c:url value='/j_spring_security_check'/>">
    <div class="row">
        <div class="col-md-offset-3 col-md-6 text-center">
            <h1><spring:message code="registration.singIn"/></h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-3 col-md-2">
            <p><spring:message code="registration.login"/></p>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <input class="form-control" type="text" name="j_username" id="username" value="${username}" size="20"/>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-3 col-md-2">
            <p><spring:message code="registration.password"/></p>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <input class="form-control" type="password" name="j_password" id="password" size="20"/>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-5 col-md-4">
            <p><input type='checkbox' name='_spring_security_remember_me' checked="checked"/>
                <spring:message code="registration.rememberMe"/></p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-5 col-md-4">
            <input class="btn btn-info" type="submit" value="<spring:message code="registration.done"/>"/>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-5 col-md-4">
            <hr/>
            <a href="<c:url value="/registration" />"> <spring:message code="registration.registration"/></a> |
            <a href="TODO"> <spring:message code="registration.lostPassword"/></a>
        </div>
    </div>
</form>