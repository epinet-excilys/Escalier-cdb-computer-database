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
      tr:nth-of-type(odd) {
      background-color:#fff;
    }
    tr:nth-of-type(even) {
      background-color:#ccc;
    }
</style>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message
					code="label.header" />
			</a>
			<div class= "container" align="right">
				<a class="dropdown-item" href="?lang=en">
	            	<img src="resources/img/united-kingdom.png" width="45" height="45"></a>
	            	&nbsp;&nbsp;&nbsp;
	            <a class="dropdown-item" href="?lang=fr">
	                <img src="resources/img/france.png" width="45" height="45"></a>
            </div>
                    
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
			


			<h1 id="homeTitle">
				<c:out value="${NbRowComputer}">
				</c:out>
				<spring:message code="label.computerNumberRow" />
			</h1>

			<div id="actions" class="form-horizontal">
				<div class="pull-left">
				<spring:message code="label.search"/>
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${search}" /> 
							
							<input type="submit" id="searchsubmit"
							value= <spring:message code="label.filterByName"/>
							class= "btn btn-primary" />
					
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="label.addComputer"/></a> 
					<a class="btn btn-default" id="editComputer" href="#" 
					onclick="$.fn.toggleEditMode();"><spring:message code="label.editComputer"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="dashboard/deleteComputer" method="POST">
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
						<th><a href="dashboard?order=Computer"><spring:message code="label.computerName"/></a></th>
						<th><a href="dashboard?order=Introduced"><spring:message code="label.dateIntro"/></a></th>
						<th><a href="dashboard?order=Discontinued"><spring:message code="label.dateDisco"/></a></th>
						<th><a href="dashboard?order=Company"><spring:message code="label.company"/></a></th>
					</tr>
				</thead>

				<tbody id="results">
					<c:forEach items="${computerList}" var="computer" varStatus="loop">
						<tr class="${loop.index % 2 == 0 ? 'even' : 'odd' }">
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


				<li>
				<c:if test="${pageIterator>0}">
						<a
							href="dashboard?pageIterator=${ pageIterator - 1 }
										<c:if test="${ pageSize != null }">&pageSize=${ pageSize }</c:if>
										<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ orderName != null and (orderName != '') }">&orderName=${ orderName }</c:if>"
							aria-label="Previous"> <span aria-hidden="true">&lArr;</span>
						</a>
					</c:if></li>
					
					<c:if test="${pageIterator >= (2)}">
					<li><a
						href="dashboard?pageIterator=${pageIterator - 2 }
											<c:if test="${ pageSize != null }">&pageSize=${ pageSize }</c:if>
											<c:if test="${ (search != null) and (search != '') }">&search=${ search }
											</c:if>
											<c:if test="${(orderName != null) and (orderName != '') }">&orderName=${ orderName }
											</c:if>"
						value="${pageIterator - 2 }">[${pageIterator - 2 }] </a></li>
				</c:if>
					

				<c:if test="${pageIterator >= (1)}">
					<li><a
						href="dashboard?pageIterator=${pageIterator - 1 }
											<c:if test="${ pageSize != null }">&pageSize=${ pageSize }</c:if>
											<c:if test="${ (search != null) and (search != '') }">&search=${ search }
											</c:if>
											<c:if test="${(orderName != null) and (orderName != '') }">&orderName=${ orderName }
											</c:if>"
						value="${pageIterator - 1 }">[${pageIterator - 1 }] </a></li>
				</c:if>

				<c:if test="${pageIterator < (maxPage+1)}">
					<li><a
						href="dashboard?pageIterator=${pageIterator }
											<c:if test="${ pageSize != null }">&pageSize=${ pageSize }</c:if>
											<c:if test="${ (search != null) and (search != '') }">&search=${ search }
											</c:if>
											<c:if test="${(orderName != null) and (orderName != '') }">&orderName=${ orderName }
											</c:if>"
						value="${pageIterator}">[${pageIterator}] </a></li>
				</c:if>
				
				<c:if test="${pageIterator < (maxPage )}">
					<li><a
						href="dashboard?pageIterator=${pageIterator + 1 }
											<c:if test="${ pageSize != null }">&pageSize=${ pageSize }</c:if>
											<c:if test="${ (search != null) and (search != '') }">&search=${ search }
											</c:if>
											<c:if test="${(orderName != null) and (orderName != '') }">&orderName=${ orderName }
											</c:if>"
						value="${pageIterator + 1 }">[${pageIterator +1 }] </a></li>
				</c:if>
				
				<c:if test="${pageIterator < (maxPage-1 )}">
					<li><a
						href="dashboard?pageIterator=${pageIterator + 2 }
											<c:if test="${ pageSize != null }">&pageSize=${ pageSize }</c:if>
											<c:if test="${ (search != null) and (search != '') }">&search=${ search }
											</c:if>
											<c:if test="${(orderName != null) and (orderName != '') }">&orderName=${ orderName }
											</c:if>"
						value="${pageIterator + 2 }">[${pageIterator +2 }] </a></li>
				</c:if>
				
				<li><c:if test="${pageIterator < (maxPage)}">
						<a
							href="dashboard?pageIterator=${ pageIterator + 1 }
										<c:if test="${ pageSize != null }">&pageSize=${ pageSize }</c:if>
										<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>"
							aria-label="Next"> <span aria-hidden="true">&rArr;</span>
						</a>
					</c:if></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a
					href="dashboard?pageIterator=0&pageSize=10
					<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="0" />">10</button>
				</a> <a
					href="dashboard?pageIterator=0&pageSize=20
					<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="0" />">20</button>
				</a> <a
					href="dashboard?pageIterator=0&pageSize=50
					<c:if test="${ (search != null) and (search != '') }">&search=${ search }</c:if>
										<c:if test="${ order != null and (order != '') }">&order=${ order }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="0" />">50</button>
				</a> <a
					href="dashboard?pageIterator=0&pageSize=100
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
