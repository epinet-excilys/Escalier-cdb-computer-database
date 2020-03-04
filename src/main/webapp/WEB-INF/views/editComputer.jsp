<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
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
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${computerDTO.id }"></c:out>
					</div>
					<h1>Edit Computer</h1>

					<form action="editComputer" method="POST">
						<input type="hidden" value="${computerDTO.id}" name="computerId"
							id="computerId" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerNameArenvoyer" value="${computerDTO.name}" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label>
								${computerDTO.introducedDate} <input type="date"
									class="form-control" id="introduced" 
									name="introduced"  value = "${introducedDate}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" value = "${discontinuedDate}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<c:choose>
									<c:when test="${empty companyOfComputer}">
									No Company
								</c:when>
									<c:otherwise>
									${companyOfComputer}
								</c:otherwise>
								</c:choose>
								<select class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${ companyDTOList }" var="company">
										<option value="${ company.id }"><c:out
												value="${ company.name }" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary" href="DashBoard">
							or <a href="DashBoard" class="btn btn-default">Cancel</a>
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