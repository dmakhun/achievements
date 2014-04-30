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
		<c:if test="${not empty error}">
				<div class="alert alert-danger" style="margin: 0 20px;">
					<spring:message code="registration.error" />
				</div>
			</c:if>
		<h1>
			<spring:message code="registration.passChang" />
					
		</h1>
		
		<form method="post" action="passwordchanging" id="details">
			<table>
				<tr>
					<spring:message code="registration.password" />
					</br>
					<input   name="oldPassword" type="password" /> 
					
				</tr></br>
				<tr>
					<spring:message code="registration.new" />
					</br>
					<input id="password" name="newPassword" type="password" /> 
				</tr></br>
				
				<tr>
					<spring:message code="registration.confirm" />
					</br>
					<div id="matchpass" class="stack-aside alert" style="display: none; margin: 0 0 0 -415px;"></div>
					<input id="confirmpass"  name="confirmPassword" type="password" /> 
				</tr></br>

				
				<tr>
					<td><input value="<spring:message code="registration.done"/>" type="submit" /></td>
				</tr>
			</table>

		</form> </section>
	</div>
</body>

