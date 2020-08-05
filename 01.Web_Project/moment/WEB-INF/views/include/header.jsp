<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Sriracha&display=swap');
	#wrap { position: fixed; width: 100%; background-color: black; height: 50px; line-height: 45px; top:0;  z-index: 997;}
	#header-top { 
		height: 50px; background-color: black; width: 80%; margin-top: 15px;
		position: absolute; margin: 0 auto; left: 10%; top:0;
	}
	#header-menu { float: right; padding: 0; margin: 0;  overflow: hidden; }
	#header-menu li{ 
		list-style: none; display:inline-block; vertical-align:middle;
	}
	#header-menu li { color: #fc7703; }
	#header-menu li a { color: white; padding: 5px 17px; font-size: 17px; font-family: 'Sriracha', cursive; }
	#header-menu li a:hover, #header-top li a.active { color: #fc7703; cursor: pointer; }
	#header-home-image { float: left; }
	#header-home-image img { width: 50px; height: 50px;  }
</style>
<div id="wrap">
	<div id="header-top" class="category">
		<div id="header-home-image"><a href='<c:url value="/" />'><img src="background/logo1.jpg" alt="홈으로" /></a></div>
		<ul id="header-menu">
			<c:if test="${!empty login_info}">
			<li><a href="mypostlist.moment" class="${category eq 'my' ? 'active' : '' }">MY POST</a></li>
			</c:if>
			<li><a href='<c:url value="/" />' class="${category eq 'home' ? 'active' : '' }">HOME</a></li>
			<li style="font-weight: bold;">│</li>
			<li><a href="list.bo" class="${category eq 'bo' ? 'active' : '' }">POST</a></li>
			<li style="font-weight: bold;">│</li>
			<li><a href="list.pa" class="${category eq 'pa' ? 'active' : '' }">PANORAMA</a></li>
			<li style="font-weight: bold;">│</li>
			<li><a href="list.qn" class="${category eq 'qa' ? 'active' : '' }">QnA</a></li>
			<li style="font-weight: bold;">│</li>
			<li><a href="map.moment" class="${category eq 'ma' ? 'active' : '' }">MAP</a></li>
			<li style="font-weight: bold;">│</li>
			<li><a href="room" class="${category eq 'ch' ? 'active' : '' }">CHAT</a></li>
			<li style="font-weight: bold;">│</li>
			<c:if test="${login_info.u_admin eq 'M' }">
			<li><a href='push' class="${category eq 'al' ? 'active' : '' }">PushService</a></li>
			</c:if>			
			<li><a id="bar-button" style="font-size: 25px; color: #fc7703;"><i class="fas fa-bars"></i></a></li>
		</ul>
	</div>
</div>