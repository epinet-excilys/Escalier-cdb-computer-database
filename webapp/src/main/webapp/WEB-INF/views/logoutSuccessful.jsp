<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Logout</title>
</head>
<body> 
	<div>You have been logged out. Redirecting to home...</div> 
    <h1>Logout Successful!</h1>
</body>

<script>
    var XHR = new XMLHttpRequest();
    XHR.open("GET", "${pageContext.request.contextPath}/dashboard", true, "no user", "no password");
    XHR.send();

    setTimeout(function () {
        window.location.href = "${pageContext.request.contextPath}";
    }, 3000);
</script>

</html>

