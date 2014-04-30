<%@ include file="../libs/libs.jsp"%>
<body onload='document.f.j_username.focus();'>
	<div class="container">
		<section id="content">
			<c:if test="${not empty error}">
				<div class="alert alert-danger" style="margin: 0 20px;">
					<spring:message code="registration.error" />
				</div>
			</c:if>

			<c:if test="${not empty usernameSurname}">
				<div class="alert alert-success" style="margin: 0 20px;">
					${usernameSurname}<br />
					<spring:message code="registration.userMessage" />
				</div>
			</c:if>


			<form method="post"
				action="<c:url value='/j_spring_security_check'/>">
				<h1>
					<spring:message code="registration.singIn" />
				</h1>
				<div>
					<input type="text" name="j_username" id="username" value="${username}" size="20" />
				</div>
				<div>
					<input type="password" name="j_password" id="password" size="20" />
				</div>

				<div>
					<h4>
						<spring:message code="registration.rememberMe" />
						<input type='checkbox' name='_spring_security_remember_me' checked="checked" />
					</h4>
				</div>

				<div>
					<input type="submit" value="<spring:message code="registration.done"/>" />
				</div>
			</form>
			<a href="registration">                             <spring:message code="registration.registration" /></a> 
			<a href="https://www.google.com/accounts/recovery"> <spring:message code="registration.lostPassword" /></a>
		</section>
	</div>
</body>
