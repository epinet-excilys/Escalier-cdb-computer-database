
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>


<html>
<head>
<title>Computer Database - JSP Version</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
			<a class="navbar-brand" href="DashBoard"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${NbRowComputer}">
				</c:out>
				Computers founds in DataBase
			</h1>

			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> 
						<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->

				<tbody id="results">
					<c:out value="Alors peut être" />
					<c:forEach items="${computerList}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td><a href="editComputer?computerId=${computer.id}" onclick=""><c:out
										value="${computer.name}"></c:out></a></td>
							<td><c:out value="${computer.introducedDate}"></c:out></td>
							<td><c:out value="${computer.discontinuedDate}"></c:out></td>
							<td><c:out value="${computer.company.name}"></c:out></td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><c:if test="${pageIterator>0}">
						<a href="DashBoard?pageIterator=${pageIterator-1}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a>
					</c:if></li>
				<c:if test="${pageIterator < maxPage}">
					<c:forEach var="i" begin="1" end="3">
						<li><a href="DashBoard?pageIterator=${pageIterator+i}"><c:out
									value="${pageIterator+i}"></c:out></a></li>
					</c:forEach>
				</c:if>
				<li><c:if test="${pageIterator < maxPage}">
						<a href="DashBoard?pageIterator=${pageIterator+1}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a>
					</c:if></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default">
					<a href="DashBoard?taillePage=10">10</a>
				</button>
				<button type="button" class="btn btn-default">
					<a href="DashBoard?taillePage=20">20</a>
				</button>
				<button type="button" class="btn btn-default">
					<a href="DashBoard?taillePage=50">50</a>
				</button>
				<button type="button" class="btn btn-default">
					<a href="DashBoard?taillePage=100">100</a>
				</button>
			</div>

		</div>
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>
