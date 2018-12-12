<%@ include file="../libs/libs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/css/shedudeTable.css" />" rel="stylesheet"
          type="text/css"/>
</head>
<body>
<center>
    <h1>
        <font face="Impact"><spring:message code="menu.8tab"/></font>
    </h1>
    <table id="rounded-corner">
        <thead>
        <tr>
            <th scope="col"><h2>
                <a href="<c:url value="/schedule/${aGroup}/${prev}" />"><img
                        src="<c:url value="/resources/images/arrowl.gif" />" border="0"></a>
                <a href="<c:url value="/schedule/${aGroup}/${next}" />"><img
                        src="<c:url value="/resources/images/arrowr.gif" />" border="0"></a>
            </h2></th>
            <th scope="col"><h2>
                <spring:message code="week.monday"/>
                ${mapWeek[1]}
            </h2></th>
            <th scope="col"><h2>
                <spring:message code="week.tuesday"/>
                ${mapWeek[2]}
            </h2></th>
            <th scope="col"><h2>
                <spring:message code="week.wednesday"/>
                ${mapWeek[3]}
            </h2></th>
            <th scope="col"><h2>
                <spring:message code="week.thursday"/>
                ${mapWeek[4]}
            </h2></th>
            <th scope="col"><h2>
                <spring:message code="week.friday"/>
                ${mapWeek[5]}
            </h2></th>
        </tr>
        </thead>

        <tbody>
        <tr>
            <th scope="col">08:00-08:45</th>
            <td>${map[1]}</td>
            <td>${map[14]}</td>
            <td>${map[27]}</td>
            <td>${map[40]}</td>
            <td>${map[53]}</td>
        </tr>
        <tr>
            <th scope="col">09:00-09:45</th>
            <td>${map[2]}</td>
            <td>${map[15]}</td>
            <td>${map[28]}</td>
            <td>${map[41]}</td>
            <td>${map[54]}</td>
        </tr>
        <tr>
            <th scope="col">10:00-10:45</th>
            <td>${map[3]}</td>
            <td>${map[16]}</td>
            <td>${map[29]}</td>
            <td>${map[42]}</td>
            <td>${map[55]}</td>
        </tr>
        <tr>
            <th scope="col">11:00-11:45</th>
            <td>${map[4]}</td>
            <td>${map[17]}</td>
            <td>${map[30]}</td>
            <td>${map[43]}</td>
            <td>${map[56]}</td>
        </tr>
        <tr>
            <th scope="col">12:00-12:45</th>
            <td>${map[5]}</td>
            <td>${map[18]}</td>
            <td>${map[31]}</td>
            <td>${map[44]}</td>
            <td>${map[57]}</td>
        </tr>
        <tr>
            <th scope="col">13:00-13:45</th>
            <td>${map[6]}</td>
            <td>${map[19]}</td>
            <td>${map[32]}</td>
            <td>${map[45]}</td>
            <td>${map[58]}</td>
        </tr>
        <tr>
            <th scope="col">14:00-14:45</th>
            <td>${map[7]}</td>
            <td>${map[20]}</td>
            <td>${map[33]}</td>
            <td>${map[46]}</td>
            <td>${map[59]}</td>
        </tr>
        <tr>
            <th scope="col">15:00-15:45</th>
            <td>${map[8]}</td>
            <td>${map[21]}</td>
            <td>${map[34]}</td>
            <td>${map[47]}</td>
            <td>${map[60]}</td>
        </tr>
        <tr>
            <th scope="col">16:00-16:45</th>
            <td>${map[9]}</td>
            <td>${map[22]}</td>
            <td>${map[35]}</td>
            <td>${map[48]}</td>
            <td>${map[61]}</td>
        </tr>
        <tr>
            <th scope="col">17:00-17:45</th>
            <td>${map[10]}</td>
            <td>${map[23]}</td>
            <td>${map[36]}</td>
            <td>${map[49]}</td>
            <td>${map[62]}</td>
        </tr>
        <tr>
            <th scope="col">18:00-18:45</th>
            <td>${map[11]}</td>
            <td>${map[24]}</td>
            <td>${map[37]}</td>
            <td>${map[50]}</td>
            <td>${map[63]}</td>
        </tr>
        <tr>
            <th scope="col">19:00-19:45</th>
            <td>${map[12]}</td>
            <td>${map[25]}</td>
            <td>${map[38]}</td>
            <td>${map[51]}</td>
            <td>${map[64]}</td>
        </tr>
        <tr>
            <th scope="col">20:00-20:45</th>
            <td>${map[13]}</td>
            <td>${map[26]}</td>
            <td>${map[39]}</td>
            <td>${map[52]}</td>
            <td>${map[65]}</td>
        </tr>
        </tbody>
    </table>
</center>
</body>
</html>