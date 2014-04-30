<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='sec'
	uri='http://www.springframework.org/security/tags'%>

<div class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value="/" />">Achievements</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<sec:authorize ifAnyGranted='ROLE_MANAGER'>
					<li><a href="<c:url value="/manager/competence" />"> Quiz</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><spring:message code="menu.10tab" /> <b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/manager/groups"/>">Groups</a></li>
							<li><a href="<c:url value="/manager/attendees"/>">Attendees</a></li>
							<li><a href="<c:url value="/manager/ratings"/>">Ratings</a></li>
						</ul></li>
				</sec:authorize>

				<sec:authorize ifNotGranted='ROLE_ADMIN'>
					<li><a href="<c:url value="/competence" />"><spring:message
								code="menu.3tab1" /></a></li>
				</sec:authorize>

				<sec:authorize ifAnyGranted='ROLE_ADMIN'>
					<li><a href="<c:url value ="/admin/competenceAll"/>">Competence</a></li>
					<li><a
						href="<c:url value="/admin/achievementtype/allAchievements" />"><spring:message
								code="menu.4tab1" /></a></li>
					<li><a href="<c:url value="/admin/allManagers" />">Managers</a></li>
					<li class="dropdown"><a href="<c:url value="/scheduleTable"/>"
						class="dropdown-toggle" data-toggle="dropdown">Schedule <b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/scheduleTable"/>">Schedule</a></li>
							<li><a href="<c:url value="/addSchedule"/>">add schedule</a></li>
						</ul></li>
				</sec:authorize>
				<sec:authorize ifAnyGranted='ROLE_MANAGER,ROLE_USER'>
					<li><a href="<c:url value="/scheduleTable"/>">Schedule</a></li>
				</sec:authorize>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><spring:message code="menu.5tab1" /> <b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="?language=en"><spring:message
									code="menu.5tab2" /></a></li>
						<li><a href="?language=es"><spring:message
									code="menu.5tab3" /></a></li>
						<li><a href="?language=ua"><spring:message
									code="menu.5tab4" /></a></li>
					</ul></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<sec:authorize ifAnyGranted='ROLE_ANONYMOUS'>
					<li style="float: right;"><a href="<c:url value= "/login"/>"><spring:message
								code="menu.2tab" /></a></li>
					<li style="float: right;"><a
						href="<c:url value= "/registration"/>"><spring:message
								code="menu.3tab" /></a></li>
				</sec:authorize>

				<sec:authorize ifAnyGranted='ROLE_USER,ROLE_MANAGER,ROLE_ADMIN'>
					<li style="float: right;"><a
						href=<c:url value='/j_spring_security_logout'/>><spring:message
								code="registration.logout"></spring:message></a></li>
					<li style="float: right"><a
						href="<c:url value="/userprofile" />"><sec:authentication
								property="principal.username" /></a></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
</div>
