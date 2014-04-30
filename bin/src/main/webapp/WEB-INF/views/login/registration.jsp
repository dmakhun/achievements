<%@ include file="../libs/libs.jsp"%>
<head>
<script type="text/javascript" src="resources/script/jquery.js"></script>
<script type="text/javascript">
	function onLoad() {

		$("#password").keyup(checkPasswordsMatch);
		$("#confirmpass").keyup(checkPasswordsMatch);
		$("#details").submit(canSubmit);
	}

	function canSubmit() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (password != confirmpass) {
			alert("<fmt:message key='UnmatchedPasswords.user.password' />");
			return false;
		} else {
			return true;
		}
	}

	function checkPasswordsMatch() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (password.length >= 4 && confirmpass.length >= 1) {
			$("#matchpass").css("display", "inherit");
			if (password == confirmpass) {
				$("#matchpass").text("<fmt:message key='MatchedPasswords.user.password' />");
				$("#matchpass").addClass("alert-success");
				$("#matchpass").removeClass("alert-danger");
			} else {
				$("#matchpass").text("<fmt:message key='UnmatchedPasswords.user.password' />");
				$("#matchpass").addClass("alert-danger");
				$("#matchpass").removeClass("alert-success");
			}
		}
		
		if (password.length == 0) {
			$("#matchpass").css("display", "none");
		}
	}
	$(document).ready(onLoad);
</script>
</head>
<body>
	<div class="container">
		<section id="content">
		<h1>
			<spring:message code="menu.3tab" />
		</h1>
		<sf:form method="post" action="createaccount" id="details" commandName="user">
			<table>
				<tr>
					<spring:message code="registration.login" />
					</br><sf:input id="username" path="username" name="username" type="text"/>
				    <sf:errors cssClass="stack-aside alert alert-danger" cssStyle="margin: 0 0 0 50px;" path="username"></sf:errors>
				</tr></br>
				<tr>
					<spring:message code="registration.firstname" />
					</br>
					<sf:errors cssClass="stack-aside alert alert-danger" cssStyle="margin: 0 0 0 -450px;" path="name"></sf:errors>
					<sf:input path="name" name="name" type="text" /> 
				</tr></br>
				<tr>
					<spring:message code="registration.secondname" />
					</br><sf:input id="username" path="surname" name="surname" type="text" /> 
					<sf:errors cssClass="stack-aside alert alert-danger" cssStyle="margin: 0 0 0 50px;" path="surname"></sf:errors>
				</tr></br>
				<tr>
					<spring:message code="registration.email" />
					</br>
					<sf:errors cssClass="stack-aside alert alert-danger" cssStyle="margin: 0 0 0 -450px;" path="email"></sf:errors>
					<sf:input id="email" path="email"  name="email" type="text" /> 
				</tr></br>
				<tr>
					<spring:message code="registration.password" />
					</br>
					<div id="matchpass" class="stack-aside alert" style="display: none; margin: 0 0 0 -415px;"></div>
					<sf:input id="password" path="password" name="password" type="password" /> 
					<sf:errors cssClass="stack-aside alert alert-danger" cssStyle="margin: 0 0 0 50px;" path="password"></sf:errors>
				</tr></br>

				<tr>
					<spring:message code="registration.confirm" />
					</br><input id="confirmpass" name="confirmpass" type="password" />
				</tr></br>
				<tr>
					<td><input value="<spring:message code="registration.done"/>" type="submit" /></td>
				</tr>
			</table>

		</sf:form> </section>
	</div>
</body>

