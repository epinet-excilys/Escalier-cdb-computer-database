<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>


<html>
<head>
<title><spring:message code="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
<style>
</style>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message
					code="label.header" />
			</a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${not empty successMessage}">
				<div class="alert alert-success" role="alert">
					<i class="fa fa-check fa-2x"></i> &nbsp;&nbsp;&nbsp;&nbsp;
					<c:out value="${successMessage}" />
				</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">
					<i class="fa fa-exclamation-triangle fa-2x"></i>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<c:out value="${errorMessage}" />
				</div>
			</c:if>

			<div class="container">
				<div class="row">
					<div class="col">
						<form class="form" action="addUser" method="POST">
							<div class="form-group">
								<label for="username">Username</label> <input type="text"
									class="form-control" id="username" name="username"
									placeholder="username" required="required">
							</div>
							<div class="form-group">
								<label for="password">Password</label> <input type="password"
									class="form-control" id="password" name="password"
									required="required">
							</div>
							<div class="form-group">
								<label for="role">User Role</label> <select class="form-control"
									id="role" name="role" required="required">
									<option value="" selected>Select user's role</option>
									<option value="ADMIN">ADMIN</option>
									<option value="USER">USER</option>
								</select>
							</div>
							<button class="btn btn-primary" type="submit">Create new
								user</button>
						</form>
					</div>
				</div>
			</div>



		</div>
	</section>

	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>
