<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Moment</title>
						
<link rel="stylesheet" href="css/common.css?v=<%=new Date().getTime() %>" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
</head>
<body>
<tiles:insertAttribute name="header" />
<tiles:insertAttribute name="bar" />
<div id="content">
	<tiles:insertAttribute name="content" />
</div>
<tiles:insertAttribute name="footer" />



</body>
</html>