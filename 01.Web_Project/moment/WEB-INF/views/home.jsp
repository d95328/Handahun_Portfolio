<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/home.css" />
<style type="text/css">
#content { width: 100%; height: 700px; }
.content { margin: 0 auto; box-sizing: content-box; width: 100%;  }
.title { width: 100%; height:auto; text-align: center; }
.title a { color: white; font-size: 2em; text-align: center; font-family: 'Suez One', serif; }
body {
  font: 400 1em/1.5 "Neuton";
  color: rgba(255, 255, 255, 0.25);
  text-align: center;
  margin: 0;
}

p {
  text-transform: uppercase;
  letter-spacing: 0.5em;
  display: inline-block;
  border: 4px double rgba(255, 255, 255, 0.25);
  border-width: 4px 0;
  padding: 1.5em 0em;
  position: absolute;
  top: 10%;
  left: 50%;
  width: 40em;
  margin: 0 0 0 -20em;
}
p span {
  font: 700 4em/1 "Oswald", sans-serif;
  letter-spacing: 0;
  padding: 0.25em 0 0.325em;
  display: block;
  margin: 0 auto;
  text-shadow: 0 0 80px rgba(255, 255, 255, 0.5);
  /* Clip Background Image */
  background: url(http://f.cl.ly/items/010q3E1u3p2Q0j1L1S1o/animated_text_fill.png) repeat-y;
  -webkit-background-clip: text;
  background-clip: text;
  /* Animate Background Image */
  -webkit-text-fill-color: transparent;
  -webkit-animation: aitf 80s linear infinite;
  /* Activate hardware acceleration for smoother animations */
  -webkit-transform: translate3d(0, 0, 0);
  -webkit-backface-visibility: hidden;
}

/* Animate Background Image */
@-webkit-keyframes aitf {
  0% {
    background-position: 0% 50%;
  }
  100% {
    background-position: 100% 50%;
  }
}

#footer-wrap { display: none; }
</style>
</head>
<body>
<div class="content">
<p>
	&mdash; Your Impressive Memorable Place &mdash;
  <span>
    Keep Your Moment
  </span>
  &mdash; Recommended Tourist Spots &mdash;
</p>
	<div class="options">
	   <div class="option active" style="--optionBackground:url(https://66.media.tumblr.com/6fb397d822f4f9f4596dff2085b18f2e/tumblr_nzsvb4p6xS1qho82wo1_1280.jpg);">
	      <div class="shadow"></div>
	      <div class="label">
	         <div class="icon">
	            <i class="fas fa-walking"></i>
	         </div>
	         <div class="info">
	            <div class="main">Blonkisoaz</div>
	            <div class="sub">Omuke trughte a otufta</div>
	         </div>
	      </div>
	   </div>
	   <div class="option" style="--optionBackground:url(https://66.media.tumblr.com/8b69cdde47aa952e4176b4200052abf4/tumblr_o51p7mFFF21qho82wo1_1280.jpg);">
	      <div class="shadow"></div>
	      <div class="label">
	         <div class="icon">
	            <i class="fas fa-snowflake"></i>
	         </div>
	         <div class="info">
	            <div class="main">Oretemauw</div>
	            <div class="sub">Omuke trughte a otufta</div>
	         </div>
	      </div>
	   </div>
	   <div class="option" style="--optionBackground:url(https://66.media.tumblr.com/5af3f8303456e376ceda1517553ba786/tumblr_o4986gakjh1qho82wo1_1280.jpg);">
	      <div class="shadow"></div>
	      <div class="label">
	         <div class="icon">
	            <i class="fas fa-tree"></i>
	         </div>
	         <div class="info">
	            <div class="main">Iteresuselle</div>
	            <div class="sub">Omuke trughte a otufta</div>
	         </div>
	      </div>
	   </div>
	   <div class="option" style="--optionBackground:url(https://66.media.tumblr.com/5516a22e0cdacaa85311ec3f8fd1e9ef/tumblr_o45jwvdsL11qho82wo1_1280.jpg);">
	      <div class="shadow"></div>
	      <div class="label">
	         <div class="icon">
	            <i class="fas fa-tint"></i>
	         </div>
	         <div class="info">
	            <div class="main">Idiefe</div>
	            <div class="sub">Omuke trughte a otufta</div>
	         </div>
	      </div>
	   </div>
	   <div class="option" style="--optionBackground:url(https://66.media.tumblr.com/f19901f50b79604839ca761cd6d74748/tumblr_o65rohhkQL1qho82wo1_1280.jpg);">
	      <div class="shadow"></div>
	      <div class="label">
	         <div class="icon">
	            <i class="fas fa-sun"></i>
	         </div>
	         <div class="info">
	            <div class="main">Inatethi</div>
	            <div class="sub">Omuke trughte a otufta</div>
	         </div>
	      </div>
	   </div>
	</div>
</div>

<script type="text/javascript">
$(".option").click(function(){
   $(".option").removeClass("active");
   $(this).addClass("active");
});

//스크롤해서 내려서 로딩된 이미지 끝에 다달았을때 다음페이지의 이미지 출력하는 스크립트
window.onscroll = function(){
// 		console.log('js 흐름탐');
		
		var scroll = window.scrollY + $(window).height();
		var endY = document.body.scrollHeight - 40;
		
// 		console.log(scroll);
// 		console.log(endY);
		
		//content 영역의 최상위 위치 값
		var scrollTop = $(this).scrollTop();
		//content 영역의 (패딩영역합산한) content의 높이
		var innerHeight = $(this).innerHeight();
		
		
		//스크롤이 컨텐트아래 50 hegiht 를 넘어서면 이벤트 시작
		if (scroll > endY) {
			$("#confirm").css('color', 'white');
			$("#footer-wrap").slideDown();
		} else {
		 	$("#confirm").css('color', 'gray');
		 	$("#footer-wrap").slideUp();
		}
};
</script>


</body>
</html>
