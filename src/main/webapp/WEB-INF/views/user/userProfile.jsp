<%@ include file="../libs/libs.jsp" %>
<html>
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
        <center>
            <h1>
                <spring:message code="editprofile.title"/>
                <br><br><br>
            </h1>
            <h3>${error}</h3>
        </center>
        <br>
        <br>

        <form method="post" action="editprofile">
            <table width="100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="leftcol"><img
                            src="<c:url value="/showImage/"/>${username}" width="240"
                            height="360"/>
                    <td valign="top"><br> <br> <br> <br> <br>
                        <br>


                        <div class="row form-aGroup">
                            <div class="row form-aGroup">
                                <div class="col-md-offset-2 col-md-4">
                                    <spring:message code="editprofile.avatar"/>
                                </div>

                                <div class="col-md-4">
                                    <a href="image"><spring:message code="editprofile.download"/></a>
                                </div>
                            </div>


                            <div class="row form-aGroup">
                                <div class="col-md-offset-2 col-md-4">
                                    <spring:message code="editprofile.pass"/>
                                </div>

                                <div class="col-md-4">
                                    <a href="passwordchanging"><spring:message
                                            code="editprofile.change"/></a>
                                </div>
                            </div>

                            <div class="row form-aGroup">
                                <div class="col-md-offset-2 col-md-4">
                                    <spring:message code="editprofile.name"/>
                                </div>

                                <div class="col-md-4">
                                    <input class="form-control" type="text" name="name"
                                           value="${name}"
                                           placeholder=<spring:message code="editprofile.ph.name"/>>
                                </div>
                            </div>

                            <div class="row form-aGroup">
                                <div class="col-md-offset-2 col-md-4">
                                    <spring:message code="editprofile.lastname"/>
                                </div>

                                <div class="col-md-4">
                                    <input class="form-control" type="text" name="surname"
                                           value="${surname}"
                                           placeholder=<spring:message code="editprofile.ph.lastname"/>>
                                </div>
                            </div>

                            <div class="row form-aGroup">
                                <div class="col-md-offset-2 col-md-4">
                                    <spring:message code="editprofile.mail"/>
                                </div>

                                <div class="col-md-4">
                                    <input class="form-control" type="e-mail" name="email"
                                           value="${email}"
                                           placeholder=<spring:message code="editprofile.ph.mail"/>>
                                </div>
                            </div>

                            <div class="row form-aGroup">
                                <div class="col-md-offset-6 col-md-4">
                                    <div class="btn-aGroup btn-aGroup-lg">
                                        <button class="btn btn-default" type="submit"
                                                name="removeCompetence">
                                            <spring:message code="update"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                </tr>
            </table>
        </form>
    </section>
</div>
<br>
<br>
<section class="about">
    <center>
        � 1993-2014 <a href="http://softserve.ua/">SoftServe</a>

    </center>
</section>


</body>
</html>