<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='sec'
	uri='http://www.springframework.org/security/tags'%>

	<ul id="menu">
		<li><a href="<c:url value="/" />"><spring:message
					code="menu.1tab" /></a></li>

		<sec:authorize ifAnyGranted='ROLE_MANAGER'>
			<li><a href="<c:url value="/manager/competence" />"> Quiz</a></li>
			<li><a href="#"><spring:message code="menu.10tab" /></a>
			<ul>
				<li><a href="<c:url value="/manager/groups"/>">Groups</a></li>
				<li><a href="<c:url value="/manager/attendees"/>">Attendees</a></li>
				<li><a href="<c:url value="/manager/ratings"/>">Ratings</a></li>
			</ul>
			</li>
				
		</sec:authorize>

		<sec:authorize ifNotGranted='ROLE_ADMIN'>
			<li><a href="<c:url value="/competence" />"><spring:message
						code="menu.3tab1" /></a></li>
		</sec:authorize>

		<sec:authorize ifAnyGranted='ROLE_ADMIN'>
			<li><a href=" <c:url value ="/admin/competenceAll"/>">Competence</a></li>
			<li><a
				href=<c:url value="/admin/achievementtype/allAchievements" />><spring:message
						code="menu.4tab1" /></a></li>
			<li><a href=<c:url value="/admin/allManagers" />>Managers</a></li>
		</sec:authorize>

		<sec:authorize ifAnyGranted='ROLE_USER,ROLE_MANAGER,ROLE_ADMIN'>
			<li><a href="<c:url value="scheduleTable"/>"><spring:message
						code="menu.8tab" /></a>
		</sec:authorize>
		<sec:authorize ifAnyGranted='ROLE_ADMIN'>
			<ul>
				<li><a href="<c:url value="scheduleTable"/>">Schedule</a></li>
				<li><a href="<c:url value="addSchedule"/>">add schedule</a></li>
			</ul>
			</li>

		</sec:authorize>
		<li><a href="#"><spring:message code="menu.5tab1" /></a>
			<ul>
				<li><a href="?language=en"><spring:message
							code="menu.5tab2" /></a></li>
				<li><a href="?language=es"><spring:message
							code="menu.5tab3" /></a></li>
				<li><a href="?language=ua"><spring:message
							code="menu.5tab4" /></a></li>
			</ul></li>





		<sec:authorize ifAnyGranted='ROLE_ANONYMOUS'>
			<li><a href="#"><spring:message code="menu.6tab1" /></a>
				<ul>
					<li><a href="#"><spring:message code="menu.7tab" /></a></li>
					<li><a href="#"><spring:message code="menu.6tab2" /></a></li>
					<li><a href="#"><spring:message code="menu.6tab3" /></a></li>
					<li><a href="#"><spring:message code="menu.6tab4" /></a></li>
					<li><a href="#"><spring:message code="menu.6tab5" /></a></li>
				</ul></li>


			<li style="float: right;"><a href="<c:url value= "/login"/>"><spring:message
						code="menu.2tab" /></a></li>
			<li style="float: right;"><a href="<c:url value= "/registration"/>"><spring:message
						code="menu.3tab" /></a></li>
		</sec:authorize>
		<sec:authorize ifAnyGranted='ROLE_USER,ROLE_MANAGER,ROLE_ADMIN'>
			<li style="float: right;"><a
				href=<c:url value='/j_spring_security_logout'/>><spring:message code="registration.logout"></spring:message></a></li>
			<li style="float: right"><a
				href="<c:url value="/userprofile" />"><sec:authentication
						property="principal.username" /></a></li>
		</sec:authorize>
	</ul> 
