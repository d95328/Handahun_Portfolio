<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 슬라이더 박스 데이터 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<link href="https://fonts.googleapis.com/css?family=Nunito&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/pano_bottom.css">

<style type="text/css">

#loading-img { position: absolute; width: 100%; height: 100%; background: black; z-index: 999; }
#footer-wrap { display: none; z-index: 3; }
.contentBody { overflow-y: auto; margin-top: 50px; }
#title { width: 100%; height: auto; font-family: 'Suez One', serif; color: white; font-size: 2em; margin-top: 100px; }

#panorama-image { width: 80%; height: 60vh; margin: 0 auto; border: 5px solid #fc7703; }
.psv-navbar, .psv-navbar * { margin: 0 auto; }
body {
  padding: 0;
  box-sizing: border-box;
  font-family: Nunito;
}
.container {
  width: 80%;
  margin: 0 auto;
}
.carousel {
  max-width: 600px;
  margin: 0 auto;
}
.box-img {
  max-width: 100%;
  height: 100%;
  display: block;
}
.example-box {
  margin: 50px auto;
}
.example-box h2 {
  margin: 0 0 20px !important;
}
.box {
	max-width: 407px;
  height: 300px;
  min-height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 3px solid #f56437fa;
  border-radius: 2px;
  font-size: 26px;
}
.box:hover{
	border: 3px solid red; 
}

.text-center {
  text-align: center;
}
.ec__nav {
	color: white;
}
.ec__item{
	max-width: 407px;
}

/* 플로팅바 */
#floating-bars{
	position: absolute;
	top: 20px;
	z-index: 502;
}
.floating{
	position: absolute;
	left: 20px;
	z-index: 501;
	display: none;
	cursor: pointer;
}
.floating-text{
	position: absolute;
	left: 50px;
	z-index: 500;
	width: 200px;
	height: 40px;
	background-color: white;
	border-radius: 2px;
	display: none;
	transition: all ease 0.5s;
	color: black;
	font-weight: bold;
	vertical-align: middle;
}
.floating-icon{
	transition: transform 0.5s linear;
	position: absolute;
	z-index: 502;
	left: 11px;
	cursor: pointer;
}
.floating-title-text{
	top: 70px;
}

.floating-comment-text{
	top: 120px;
}
.floating-user-text{
	top: 170px;
}
.floating-title:hover+.floating-title-text{
	display: block;
}
.floating-comment:hover+.floating-comment-text{
	display: block;
}
.floating-user:hover+.floating-user-text{
	display: block;
} 

#panorama-text:hover{
	opacity: 1.0;
}
.pano-text{
	z-index: 901;
	color: black;
	text-align: left;
	border-bottom: 2px solid black;
}
    
</style>

<!-- 포토스피어 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/photo-sphere-viewer@4/dist/photo-sphere-viewer.min.css"/>
</head>
<body class="contentBody">
<form action="panolist.pa" method="post">
	<input type="hidden" name="curPage" value="1" >
</form>

<div id="title"><h2> PANORAMA 360 VIEW</h2></div>
<div id="loading-img">
	<img src="img/loading.gif" style="">
</div>	
	<c:if test="${ !empty login_info }">		
		<div style="width:80%; height: 20px; margin:0 auto; margin-bottom:40px; ">
			<a class="btn-fill newBtn" style="float:right; margin-bottom: 10px; " href="new.pa">글작성</a>
		</div>		
	</c:if>
	<div id="panorama-image" style="position: relative;">
	
	<div id="floating-bars">
		<div class="floating-icon"><img src="img/floating-icon.png"></div>
		<div class="floating-title floating"><img src="img/floating-title.png"></div>
		<div class="floating-title-text floating-text">${first.b_title}</div>
		<div class="floating-comment floating"><img src="img/floating-comment.png"></div>
		<div class="floating-comment-text floating-text">${first.b_coment}</div>
		<div class="floating-user floating"><img src="img/floating-user.png"></div>
		<div class="floating-user-text floating-text">${first.b_nick}</div>
	</div>
		<!-- 포토스피어 뷰 들어오는 공간 -->
	</div>
	<!-- 하단 슬라이더 박스 -->
	<div class="container">
	  <div class="example-box">
	    <div class="carousel-multiple-items">
	    <c:forEach items="${page.list }"  var="vo">
	    	
	      <div>
	        <div class="box" style="background: white;"><img class="box-img" src="background/${vo.b_imgpath }"  
	        		onclick="change_image('${vo.b_imgpath }','${vo.b_title }','${vo.b_coment }','${vo.b_nick }')" ></div>
	      </div>
	    </c:forEach>
	    </div>
	  </div>
</div>


<!-- 포토 스피어 -->
<script src="https://cdn.jsdelivr.net/npm/three/build/three.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/uevent@2/browser.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/photo-sphere-viewer@4/dist/photo-sphere-viewer.min.js"></script>

<script src="js/pano_bottom.js"></script>
<script type="text/javascript">

$( document ).ready(function() {
	//로딩 이미지 미리 불러오기
	let img = new Image();
	img.src = 'img/loading.gif';

	//로딩중일때 스크롤바 제거
	$('.contentBody').css('overflow-y','hidden');
	$('.contentBody').css('overflow-x','hidden');
	
});


var floating_click = false;

var viewer = new PhotoSphereViewer.Viewer({
	  container: document.querySelector('#panorama-image'),
	  panorama: 'background/${first.b_imgpath}'
});

new ElderCarousel('.carousel-multiple-items', { items: 4 });

//로딩화면 제거
window.onload = function(){
	$('#loading-img').css('display','none');
	//스크롤바 생성
	$('body').css('overflow-y','scroll');
}

function change_image(imgPath, title, comment, user){
	$('.psv-container').remove();
	$('.floating-title-text').html(title);
	$('.floating-comment-text').html(comment);
	$('.floating-user-text').html(user);
	viewer = new PhotoSphereViewer.Viewer({
		  container: document.querySelector('#panorama-image'),
		  panorama: 'background/'+imgPath
	});
}

$('.floating-icon').on('click',function(){
	if(!floating_click){
		$('.floating').css('display','block');
		$('.floating-icon').css('transform','rotate(45deg)');
		$('.floating-title').show().animate({top: 70}, "slow");
		$('.floating-comment').show().animate({top: 120}, "slow");
		$('.floating-user').show().animate({top: 170}, "slow");
		floating_click = true;
	}else{
		$('.floating').show().animate({top: 0}, "slow", function(){
			$('.floating-icon').css('transform','rotate(0deg)');
			$('.floating').css('display','none');
		});
		floating_click = false;
	}
});

</script>
</body>
</html>