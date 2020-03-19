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

			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${computerDTO.id }"></c:out>
					</div>
					<h1>
						<spring:message code="label.editComputer" />
					</h1>

					<form action="editComputer" method="POST">
						<input type="hidden" value="${computerDTO.id}" name="computerId"
							id="computerId" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="label.computerName" /></label> <input type="text"
									class="form-control" id="computerName"
									name="computerNameArenvoyer" value="${computerDTO.name}"
									required>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="label.dateIntro" /></label> ${computerDTO.introducedDate} <input
									type="date" class="form-control" id="introduced"
									name="introduced" value="${introducedDate}">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="label.dateDisco" /></label> <input type="date"
									class="form-control" id="discontinued" name="discontinued"
									value="${discontinuedDate}">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="label.company" /></label>
								<c:choose>
									<c:when test="${empty computerDTO.companyDTO.name}">
										<spring:message code="label.noCompany" />
									</c:when>
									<c:otherwise>
									${computerDTO.companyDTO.name}
								</c:otherwise>
								</c:choose>

								<select class="form-control" id="companyId" name="companyId">
									<c:choose>
										<c:when test="${empty computerDTO.companyDTO.name}">

											<option value="0">--</option>
										</c:when>
										<c:otherwise>
											<option value="${computerDTO.companyDTO.id}"><c:out
													value="${computerDTO.companyDTO.name}" /></option>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${not empty computerDTO.companyDTO.name}">

											<option value="0">--</option>
										</c:when>
									</c:choose>
									<c:forEach items="${ companyDTOList }" var="company">
										<option value="${ company.id }"><c:out
												value="${ company.name }" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value=<spring:message
										code="label.edit" /> class="btn btn-primary"
								href="dashboard"> or <a href="dashboard"
								class="btn btn-default"><spring:message
										code="label.cancel" /></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</body>
</html>