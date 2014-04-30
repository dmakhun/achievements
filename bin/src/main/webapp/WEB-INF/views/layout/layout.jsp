<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<style>
@font-face {
  font-family: 'Glyphicons Halflings';

  src: url('<c:url value="/resources/fonts/" />glyphicons-halflings-regular.eot');
  src: url('<c:url value="/resources/fonts/" />glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), url('<c:url value="/resources/fonts/" />glyphicons-halflings-regular.woff') format('woff'), url('<c:url value="/resources/fonts/" />glyphicons-halflings-regular.ttf') format('truetype'), url('<c:url value="/resources/fonts/" />glyphicons-halflings-regular.svg#glyphicons_halflingsregular') format('svg');
}
</style>
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/addAchiv.css" />" type="text/css" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery-ui.css" />" type="text/css" />
<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet" type="text/css" />
</head>
<body>  
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />
</body>
</html>