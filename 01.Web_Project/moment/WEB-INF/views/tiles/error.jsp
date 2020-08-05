<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IoT</title>
<link rel="icon" type="image/x-icon" href="img/hanul.ico" />
<style type="text/css">
#error{
	width: 600px; margin: 0 auto;
	position: absolute; left: 50%; top: 50%;
	transform: translate(-50%, -50%); 
}
	
</style>
</head>
<body>
<div id="error">
	<div class="left">
		<a href="<c:url value='/' />"><img src="img/hanul.logo.png" /></a>
	</div>
	<hr>
	<div class="left">
		<tiles:insertAttribute name="content" />
	</div>
</div>

</body>
</html>