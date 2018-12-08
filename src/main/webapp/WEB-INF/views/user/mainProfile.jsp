<%@ include file="../libs/libs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="lt-ie9" lang="en">
<html lang="en">
<head>
    <link rel="stylesheet" href="css/style.css">
    <style type="text/css">
        TD.leftcol {
            width: 110px;
            vertical-align: top;
        }
    </style>

</head>

<body>
<div class="container">
    <section class="register">
        <div style="text-align: center;">
            <h1>
                <spring:message code="myprofile.title"/><br>
            </h1>
            <h3>${error}</h3>
            <br>
        </div>

        <table width="100%" cellspacing="0" cellpadding="0">
            <tr>
                <td class="leftcol"><img
                        src="<c:url value="/showImage/${username}"/>" width="240"
                        height="360"/>
                <td valign="top"><br> <br> <br> <br> <br>
                    <br> <br> <br>

                    <form method="get">
                        <div class="reg_section personal_info">
                            <div class="row form-aClass">
                                <div class="col-md-offset-2 col-md-4">
                                    <spring:message code="myprofile.edit"/></div>
                                <a href="editprofile"><spring:message code="myprofile.edite"/></a>
                            </div>


                            <div class="row form-aClass">
                                <div class="col-md-offset-2 col-md-4"><spring:message code="myprofile.name"/></div>

                                <div>
                                    <h4>
                                        ${name}<br>
                                    </h4>
                                </div>
                            </div>

                            <div class="row form-aClass">
                                <div class="col-md-offset-2 col-md-4"><spring:message code="myprofile.lastname"/></div>
                                <div>
                                    <h4>
                                        ${surname}<br>
                                    </h4>
                                </div>
                            </div>

                            <div class="row form-aClass">
                                <div class="col-md-offset-2 col-md-4"><spring:message code="myprofile.mail"/></div>
                                <div>
                                    <h4>
                                        ${email} <br>
                                    </h4>
                                </div>
                            </div>
                        </div>
                    </form>
            </tr>
        </table>

        </form>
    </section>
</div>
<br>
<section class="about">
    <div style="text-align: center;">
        <a href="https://softserveinc.com">SoftServe</a>
    </div>
</section>


</body>
</html>