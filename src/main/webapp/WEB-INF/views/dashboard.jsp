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
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
		  <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    <i class="fa fa-check fa-2x"></i> &nbsp;&nbsp;&nbsp;&nbsp;<strong><spring:message
                        code="label.success"/>:</strong>
                    <c:out value="${successMessage}"/>
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    <i class="fa fa-exclamation-triangle fa-2x"></i>
                    &nbsp;&nbsp;&nbsp;&nbsp;<strong><spring:message
                        code="label.error"/>:</strong>
                    <c:out value="${errorMessage}"/>
                </div>
            </c:if>
		
		
			<h1 id="homeTitle">
				<c:out value="${NbRowComputer}">
				</c:out>
				Computers founds in DataBase
			</h1>

			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
					
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${search}"/> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="/dashboard/deleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						 <th>
                          <a href="dashboard?order=Computer">Computer name</a>
                        </th>
                        <th>
                            <a href="dashboard?order=Introduced">Introduced date</a>
                        </th>
                        <th>
                          <a href="dashboard?order=Discontinued">  Discontinued date</a>
                        </th>
                        <th>
                            <a href="dashboard?order=Company">Company</a>
                        </th>
					</tr>
				</thead>

				<tbody id="results">
					<c:forEach items="${computerList}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id }"></td>
							<td><a href="editComputer?computerId=${computer.id}"
								onclick=""><c:out value="${computer.name}"></c:out></a></td>
							<td><c:out value="${computer.introducedDate}"></c:out></td>
							<td><c:out value="${computer.discontinuedDate}"></c:out></td>
							<td><c:out value="${computer.companyDTO.name}"></c:out></td>
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
						<a
							href="dashboard?pageIterator=${ pageIterator - 1 }
										<c:if test="${ taillePage != null }">&taillePage=${ taillePage }</c:if>
										<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ orderName != null and (orderName != '') }">&orderName=${ orderName }</c:if>"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a>
					</c:if></li>
					
					
				<c:if test="${pageIterator < (maxPage+1)}">
					<li><a
										href="dashboard?pageIterator=${pageIterator }
											<c:if test="${ taillePage != null }">&taillePage=${ taillePage }</c:if>
											<c:if test="${ (search != null) and (search != '') }">&search=${ search }
											</c:if>
											<c:if test="${(orderName != null) and (orderName != '') }">&orderName=${ orderName }
											</c:if>"
											value="${pageIterator}">[${pageIterator}]
						
						</a></li>
				</c:if>
				<li><c:if test="${pageIterator < (maxPage)}">
						<a
							href="dashboard?pageIterator=${ pageIterator + 1 }
										<c:if test="${ taillePage != null }">&taillePage=${ taillePage }</c:if>
										<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a>
					</c:if></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a
				href="dashboard?pageIterator=0&taillePage=10
					<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="0" />">10</button>
				</a>
				<a
					href="dashboard?pageIterator=0&taillePage=20
					<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="0" />">20</button>
				</a>
				<a
					href="dashboard?pageIterator=0&taillePage=50
					<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="0" />">50</button>
				</a>
				<a
					href="dashboard?pageIterator=0&taillePage=100
					<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="0" />">100</button>
				</a>

		</div>
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>
