<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	
	<div class="container">
		<center>
			<h1>
				<spring:message code="menu.9tab2" /> 

			</h1>
		</center>
		<form  method="post">
			<c:if test="${status == 'success'}">
				<div class="row">
					<div class="col-md-offset-3 col-md-6">
						<div class="alert alert-warning">Manager has been added!</div>
					</div>
				</div>

			</c:if>

			<div class="row">
				<div class="col-md-offset-4 col-md-4">
					<div class="form-group">
						<input class="form-control" type="text" name="username"
							placeholder=<spring:message code="user.username"/> />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-offset-4 col-md-4">
					<div class="form-group">
						<input class="form-control" type="password" name="password"
							placeholder=<spring:message code="registration.password"/> />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-offset-4 col-md-4">
					<div class="form-group">
						<input class="form-control" type="text" name="surname"
							placeholder=<spring:message code="registration.secondname"/> />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-offset-4 col-md-4">
					<div class="form-group">
						<input class="form-control" type="text" name="e-mail"
							placeholder=<spring:message code="registration.email"/> />
					</div>
				</div>
			</div>
			<div class="row text-center">
				<div class="col-md-offset-4 col-md-4">
					<div class="btn-group btn-group-lg">
						<button class="btn btn-success" type="submit">
						Submit
						</button>
					</div>
				</div>
			</div>
		</form>
		</div>
		
