<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    

<style>
#bar-wrap{
	position: fixed;
	top: 0;
	width: 100%; height: 100%; background-color: #00000060;
	display: none;
	z-index: 998;
}
#bar-content {
	position: absolute;
	top: 0; right: -500px; z-index: 999;
	color: white;
	width: 500px; height: 100%; background-color: #1b1b1b;
	display: none;
}
 .bar-menu { margin: 55px 0 0 30px; } 
.bar-menu li { font-size: 22px; margin-top: 10px; text-align: left; }
.bar-menu li a { color: white; font-family: 'Sriracha', cursive; } 
.bar-menu li a:hover, .bar-menu li a.active { color: #fc7703; cursor: pointer; }
.bar-user { margin: 20px 0 20px 20px; width: 480px; height: 100px; }
.bar-profile { 
	width: 100px; height: 100px; border: 2px solid #2f2f2f;
	border-radius: 50px; float: left;
	background-color: white; 
}
.bar-close{
	height: 40px;
	font-size: 40px;
	margin-bottom: 30px;
	text-align: left;
}
.bar-login { float: left; margin-left: 20px; } 
.fa-caret-square-left:hover{ color: #fc7703; }

.bLogin_img{ 
	width: 100px;
    border-radius: 50px;
    height: 100px;
}
</style>
<div id="bar-wrap"></div>
	<div id="bar-content">
		<div class="bar-close">
			<a class="bar-close-btn">
				&nbsp;<i class="fas fa-caret-square-left"></i>
			</a>
		</div>
		<div class="bar-user">
			<div class="bar-profile">
				<c:if test="${empty login_info }">
				<img class="bLogin_img" src="background/logo2.png">
				</c:if>
				<span id='preview_profile' ></span>
			</div>
			<div class="bar-login">
				<c:if test="${empty login_info }">
					<a class="btn-fill" href='loginuser'>LOGIN</a>
					<a class="btn-fill" href='joinuser'>JOIN US</a>
				</c:if>
				<c:if test="${!empty login_info }">
					<h3>${login_info.u_name}</h3>
					<a class="btn-fill" onclick="go_logout()">LOGOUT</a>
					<a class="btn-fill" href="usermodify">정보수정</a>
				</c:if>
			</div>
		</div>
		<ul class="bar-menu">
			<li><a href='<c:url value="/" />' class="${category eq 'home' ? 'active' : '' }">HOME</a></li>
			<li><a href="list.bo" class="${category eq 'bo' ? 'active' : '' }">POST</a></li>
			<li><a href="list.pa" class="${category eq 'pa' ? 'active' : '' }">PANORAMA</a></li>
			<li><a href="list.qa" class="${category eq 'qa' ? 'active' : '' }">QnA</a></li>
			<li><a href="map.moment" class="${category eq 'ma' ? 'active' : '' }">MAP</a></li>
			<li><a href='room' class="${category eq 'ch' ? 'active' : '' }">CHAT</a></li>
			<c:if test="${login_info.u_admin eq 'M' }">
			<li><a href='push' class="${category eq 'al' ? 'active' : '' }">PushService</a></li>
			</c:if>
		</ul>
	</div>
<script>
function go_logout(){
	$.ajax({
		type:'post',
		url: 'weblogout',
		success: function(){
			location.href="http://localhost/moment";
		},error: function(req, text){
			alert(text+": "+req.status);
		} 
	});
}
	$('#bar-button').on('click', function(){
		var scroll = window.scrollY || document.documentElement.scrollTop;
		$('#bar-wrap').css('display','block');
		$('#bar-content').css('display','block');
		$('#bar-content').css('top', scroll );
		$('#bar-content').show().animate({right: 0}, "slow");
		//스크롤바 제거
		$('body').css('overflow-y','hidden');
		$('body').css('overflow-x','hidden');
	});
	
	$('#bar-wrap, .bar-close-btn').on('click', function(){
		$('#bar-content').show().animate({right: -500}, "fast", function(){
			$(this).css('display','none');
		});
		$('#bar-wrap').css('display','none');
		//스크롤바 생성
		$('body').css('overflow-y','scroll');
	});
	if( ${!empty login_info} ){
		showAttachedImage( '#preview_profile' );
	}
	function showAttachedImage( id ){
		var filename = '${login_info.u_profileimg}';
		var ext = filename.substring( filename.lastIndexOf('.')+1 ).toLowerCase();
		var imgs = [ 'jpg', 'png', 'gif', 'bmp', 'jpeg' ];
		if( imgs.indexOf(ext) > -1 ){
			var img = '<img id="preview-img" class="' + (id=='#popup' ? 'popup' : 'file-img') + '" '
					+ 'style="border-radius:50%;     width: 100px;   height: 100px;" src="'
					+ 'profile/${login_info.u_profileimg}' +' "/>';
			$( id ).html( img );
		}
	}
	
</script>