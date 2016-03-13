<%@ include file="../libs/libs.jsp" %>
<link rel="stylesheet"
      href="<c:url value="/resources/css/newButton.css" />" type="text/css"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<body>
<center>
    <h1>
        <spring:message code="image.title"/>
        <br>
    </h1>
    <h3>${mess}</h3>
    <form method="post" enctype="multipart/form-data">
        <br/>
        <spring:message code="image.file"/>
        <br/> <br/> <input type="file" name="file" accept="image/*"><br/>


        <input type="submit" value="Upload">

    </form>
    <img src="<c:url value="/showImage/" />${username}" width="260"
         height="360"/>
</center>
<section class="about">
    <center>
        � 1993-2014 <a href="http://softserve.ua/">SoftServe</a>
    </center>
</section>

</body>