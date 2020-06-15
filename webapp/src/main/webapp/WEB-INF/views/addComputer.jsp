<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<html>
<head>
<title><spring:message code="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message
					code="label.header" /></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="label.addComputer" />
					</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="label.computerName" /></label> <input type="text"
									class="form-control" id="computerName" name="computerName"
									placeholder="Computer name" required>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="label.dateIntro" /></label> <input type="date"
									class="form-control" id="introduced" name="introduced"
									placeholder="Introduced date" min="1971-01-01">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="label.dateDisco" /></label> <input type="date"
									class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date" min="1971-01-01">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="label.company" /></label> <select class="form-control"
									id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${ companyDTOList }" var="company">
										<option value="${ company.id }"><c:out
												value="${ company.name }" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value=<spring:message code="label.add" /> class="btn btn-primary"> 
								<spring:message code="label.or" />  <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel" /></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>