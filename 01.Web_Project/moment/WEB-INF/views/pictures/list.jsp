<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Suez+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap');

/* 사진효과 */
@import url(https://fonts.googleapis.com/css?family=Roboto:300,400);
@import url(https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css);
figure.snip1368 {
  font-family: 'Roboto', Arial, sans-serif;
  position: relative;
  overflow: hidden;
  margin: 10px;
  min-width: 230px;
  max-width: 315px;
  width: 100%;
  color: #ffffff;
  line-height: 1.4em;
}
figure.snip1368 * {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: all 0.4s ease;
  transition: all 0.4s ease;
}
figure.snip1368 img {
  opacity: 1;
  width: 100%;
  vertical-align: top;
}
figure.snip1368 h5 {
  position: absolute;
  top: 0%;
  width: 100%;
  background-color: #212121;
  z-index: 1;
  text-align: right;
  padding: 15px 25px 0px;
  margin: 0;
  font-weight: 300;
  font-size: 1.3em;
  -webkit-transform: translateY(-200%);
  transform: translateY(-200%);
}
figure.snip1368 h5:before {
  position: absolute;
  content: '';
  top: 100%;
  left: 0;
  width: 0;
  height: 0;
  border-style: solid;
  border-width: 0 315px 55px 0;
  border-color: transparent #212121 transparent transparent;
}
figure.snip1368 figcaption {
  padding: 5px 25px 15px;
  position: absolute;
  width: 100%;
  z-index: 2;
  bottom: 0%;
  background-color: #141414;
  -webkit-transform: translateY(200%);
  transform: translateY(200%);
}
figure.snip1368 figcaption:before {
  position: absolute;
  content: '';
  bottom: 100%;
  left: 0;
  width: 0;
  height: 0;
  border-style: solid;
  border-width: 55px 0 0 315px;
  border-color: transparent transparent transparent #141414;
}
figure.snip1368 .icons {
  margin-top: -10px;
  text-align: left;
}
figure.snip1368 i {
  padding: 0px 8px;
  display: inline-block;
  font-size: 24px;
  color: #ffffff;
  text-align: center;
  opacity: 0.7;
}
figure.snip1368 i:hover {
  opacity: 1;
  -webkit-transition: all 0.35s ease;
  transition: all 0.35s ease;
}
figure.snip1368:hover h5,
figure.snip1368.hover h5 {
  -webkit-transform: translateY(0%);
  transform: translateY(0%);
}
figure.snip1368:hover figcaption,
figure.snip1368.hover figcaption {
  -webkit-transform: translateY(0%);
  transform: translateY(0%);
}
/* 여기까지 */

#footer-wrap { display: none; z-index: 3; }
.contentBody { overflow-y: auto; margin-top: 50px; }
#title { width: 100%; height: auto; font-family: 'Suez One', serif; color: white; font-size: 2em; margin-top: 100px; }
#content { width: 100%; height: 850px; color: black; margin: 0 auto -100; background-color: black; }
#contentBox { margin-bottom: 200px; }
.pictureBox { display: inline-block; }
.pictures { width: 500px; height: 250px; border-radius: 10px; }
#confirm { margin-top: 30px; border-radius: 5px; color: gray; }


/* 검색창  */
#list-top { width: 80%; padding: 40px 0; padding-left: 10%; }
#list-top ul { margin: 0; display: flex; }
#list-top ul li *{ vertical-align: middle; }
#list-top ul li:not(:first-child){ margin-left: 2px; }

ul { list-style: none; padding: 0; }
#list-top div { height: 32px; }
#list-top ul:first-child { float: left; }
#list-top ul:last-child { float: right; }
#selectBox {  border-radius: 10px; }
#keyBox { border: 1px solid white; border-radius: 5px; }


#btn { height: 280px;}

select { font-size: 1em; width: 100px; height: 50px; font-family: 'Gothic A1', sans-serif; padding: 0 0 0 3px; font-weight: bold; }

.pictureContent { box-sizing: content-box; min-width: 1914px; max-width: 2395px; margin: 0 auto; }
.pictureBox { min-width: 335px; }
</style>

<!-- search버튼 애니메이션 -->
<style type="text/css">
.search-wrapper {
    position: absolute;
    transform: translate(-50%, -50%);
}
.search-wrapper.active {}

.search-wrapper .input-holder {    
    height: 50px !important;
    width:50px !important;
    background: rgba(255,255,255,0);
    border-radius:6px;
    transition: all 0.3s ease-in-out;
}

.search-input{
	background-color: white !important;
	width: 310px;
	color: black !important;
	border-radius: 10px;
}
.search-wrapper.active .input-holder {
    width:450px !important;
    border-radius: 50px !important;
    background: rgba(0,0,0,0.5);
    transition: all .5s cubic-bezier(0.000, 0.105, 0.035, 1.570);
}
.search-wrapper .input-holder .search-input {
    height: 50px;
    padding:0px 70px 0 20px;
    opacity: 0;
    position: absolute;
    top:0px;
    left:60%;
    background: transparent;
    box-sizing: border-box;
    border:none;
    outline:none;
    font-family:"Open Sans", Arial, Verdana;
    font-size: 16px;
    font-weight: 400;
    line-height: 70px;
    color:#FFF;
    transform: translate(0, 60px);
    transition: all .3s cubic-bezier(0.000, 0.105, 0.035, 1.570);
    transition-delay: 0.3s;
}
.search-wrapper.active .input-holder .search-input {
    opacity: 1;
    transform: translate(0, 10px);
}
.search-wrapper .input-holder .search-icon {
    width:50px;
    height:50px;
    border:none;
    border-radius:6px;
    background: #FFF;
    padding:0px;
    outline:none;
    position: relative;
    z-index: 2;
    float:right;
    cursor: pointer;
    transition: all 0.3s ease-in-out;
}
.search-wrapper.active .input-holder .search-icon {
    width: 50px;
    height: 50px;
    margin: 10px;
    border-radius: 30px;
    left: 35%;
}
.search-wrapper .input-holder .search-icon span {
    width:22px;
    height:22px;
    display: inline-block;
    vertical-align: middle;
    position:relative;
    transform: rotate(45deg);
    transition: all .4s cubic-bezier(0.650, -0.600, 0.240, 1.650);
}
.search-wrapper.active .input-holder .search-icon span {
    transform: rotate(-45deg);
}
.search-wrapper .input-holder .search-icon span::before, .search-wrapper .input-holder .search-icon span::after {
    position: absolute; 
    content:'';
}
.search-wrapper .input-holder .search-icon span::before {
    width: 4px;
    height: 11px;
    left: 9px;
    top: 18px;
    border-radius: 2px;
    background: #fc7703;
}
.search-wrapper .input-holder .search-icon span::after {
    width: 14px;
    height: 14px;
    left: 0px;
    top: 0px;
    border-radius: 16px;
    border: 4px solid #fc7703;
}
.search-wrapper .close {
    position: absolute;
    z-index: 1;
    top:24px;
    right:10px;
    width:25px;
    height:25px;
    cursor: pointer;
    transform: rotate(-180deg);
    transition: all .3s cubic-bezier(0.285, -0.450, 0.935, 0.110);
    transition-delay: 0.2s;
}
.search-wrapper.active .close {
    right:-180px;
    transform: rotate(45deg);
    transition: all .6s cubic-bezier(0.000, 0.105, 0.035, 1.570);
    transition-delay: 0.5s;
}
.search-wrapper .close::before, .search-wrapper .close::after {
    position:absolute;
    content:'';
    background: #fc7703;
    border-radius: 2px;
}
.search-wrapper .close::before {
    width: 5px;
    height: 25px;
    left: 10px;
    top: 0px;
}
.search-wrapper .close::after {
    width: 25px;
    height: 5px;
    left: 0px;
    top: 10px;
}
.selectDIV { position: absolute; display: none; left: 35%; top: 30%; }

.newBtn { 
	width: 40px; height: 40px ; 
	position: relative; top: 15%; right: -15px;
}

/* 플로팅 스크롤버튼 */
#scrolldown { 
	display: block; width: 50px; height: 50px; background: url("icons/ArrowDown.png"); background-color: #fc7703; background-size: 100%; 
	position: fixed; cursor: pointer; bottom: 100px; right: 20px; z-index: 3; border-radius: 50px; opacity: 0.6;
}
#scrolldown:hover { opacity: 1; width: 55px; height: 55px; }
#scrolltop { 
	display: block; width: 50px; height: 50px; background: url("icons/ArrowUp.png"); background-color: #fc7703; background-size: 100%; 
	position: fixed; cursor: pointer; bottom: 200px; right: 20px; z-index: 3; border-radius: 50px; display: none; opacity: 0.6;
}
#scrolltop:hover { opacity: 1; width: 55px; height: 55px; }

</style>
</head>

<body class="contentBody">

<div id="title">
<h2>POST</h2>
</div>
<div id="list-top">
<form action="list.bo" method="post" id="list_form">
<input type="hidden" name="curPage" value="1" >
	<div>
		<div class="search-wrapper">
			<div class="selectDIV">
			<ul>
				<li>
					<select id="selectBox" name="search" class="w-px100" style="">
						<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
						<option value="title" ${page.search eq 'title' ? 'selected' : '' }>제목</option>
						<option value="coment" ${page.search eq 'coment' ? 'selected' : '' }>내용</option>
						<option value="writer" ${page.search eq 'writer' ? 'selected' : '' }>작성자</option>
					</select>
				</li>
			</ul>
			</div>
			<div class="input-holder">
			    <input id="keyBox" type="text" name="keyword" class="search-input" placeholder="Type to search" />
			    <button class="search-icon" onclick="searchToggle(this, event); $('form'.submit);"><span></span></button>
			</div>
			<span class="close" onclick="closeToggle(this, event);"></span>
		</div>
		<ul>
			<li>
				<select id="selectBox" name="viewType" class="w-px80" onchange="$('#list_form').submit()" >
					<option value="lately" ${page.viewType eq 'lately' ? 'selected' : '' } >최신순</option>
					<option value="ddabong" ${page.viewType eq 'ddabong' ? 'selected' : '' } >추천순</option>
				</select>
			</li>
			<c:if test="${ !empty login_info }">
				<li><a href="new.bo"><img class="newBtn" src="img/floating-icon.png"/></a></li>
			</c:if>
		</ul>
	</div>
	<input type="hidden" name="id" />
</form>
</div>

<div id="content" style="margin: 0 auto;">
	<div id="contentBox">
	
		<!-- pictureContent가 10개 사진 div 한묶음 -->
		<div class="pictureContent">
			<c:forEach items="${page.list}" var="list">
			<div class="pictureBox" onclick="detail(${list.b_no}, '${login_info.u_userid}' )">
				<figure class="snip1368">
					<img src="background/${list.b_imgpath}" class="pictures" alt="sample30"/>
					<h5 style="font-family: 'Gothic A1', sans-serif;">${list.b_title}</h5>
					<figcaption>
					</figcaption>
				</figure>
			</div>
			</c:forEach>
		</div>
		<!-- 여기부분에 스크롤or버튼클릭시 다음페이지 출력. -->
	</div>
</div>


<div id="detail">
	<div class="detitleBox">
		<h3 class="detitle"></h3>
		
			<img id="modify_open" class="modifyIcon" src="icons/modifyBtn.png" onclick="modify()"/>
			<img id="modify_close" class="modifyIcon" src="icons/close.png" onclick="modify_close()"/>
		
		
		<div class="modiMenu">
			<form action="modify.bo" method="post" id="modify_submit">
				<input type="hidden" value="" class="board_no" name="no"/>
				<input type="hidden" value="" class="board_userid" name="userid"/>
				
				<div id="modiBox" class="menuIconBox" onclick="modify_board()">
					<img src="icons/modify.png" class="modicon"/>
				</div>
			</form>
			<div id="trashBox" class="menuIconBox" onclick="delete_board()">
				<img src="icons/trash.png" class="trashicon"/>
			</div>
		</div>
	</div>
</div>

<div id="detail-background"></div>

<!-- <span title="scroll to top" id="scrolltop"></span> -->
<span title="scroll to down" id="scrolldown" onclick="scrollbtn(1)"></span>
<span title="scroll to top" id="scrolltop" onclick="scrollbtn(2)"></span>
<input type="hidden" id="checkScroll" value="0"/>

<!-- 글라이드 js (슬라이드 이미지) -->
<script type="text/javascript" src="js/banner.js"></script>
<script type="text/javascript" src="js/glidejs.js"></script>

<!-- 디테일 js -->
<script type="text/javascript" src="js/detail.js"></script>

<script type="text/javascript">

//스크롤 플로팅 버튼
function scrollbtn(action) {
	var checkScroll = $('#checkScroll').val();
	var scroll = $(window).scrollY + $(window).height();
// 	console.log("scroll : " + scroll);	
	if(action == 1) {
		//초기값 checkScroll이 0이면
		if(checkScroll == 0) {
			//맨처음 스크롤이동 220으로 2페이지 로딩
			//checkScroll값이 초기값이 아니라면 계속 증가
			$('#checkScroll').val('1');
			$('html').animate({scrollTop: '500'}, 1000);
		}	else if(checkScroll == 1){
			$('#checkScroll').val('2');
			$('html').animate({scrollTop: '1050'}, 1000);
		} else if(checkScroll == 2) {
			$('#checkScroll').val('3');
			$('html').animate({scrollTop: '1605'}, 1000);
		} else if(checkScroll == 3) {
			$('#checkScroll').val('0');
			$('html').animate({scrollTop: '2050'}, 1000);
			//알럿 너무빨리뜸 시간주기.
			setTimeout(function() {
				alert('페이지 최하단입니다.');
			}, 1500);
		}
	} else if(action == 2) {
		$('#checkScroll').val('0');
		$('html').animate({scrollTop: '0'}, 700);
	}
}

//search버튼 js
function searchToggle(obj, evt){
    var container = $(obj).closest('.search-wrapper');
    	//첫번째눌렸을때 class추가
        if(!container.hasClass('active')){
            container.addClass('active');
            $('.selectDIV').css('display', 'block');
            evt.preventDefault();
        }
        else if(container.hasClass('active') && $(obj).closest('.input-holder').length == 0){
            //두번째눌렸을때 클래스와 내용제거
            container.removeClass('active');
            // clear input
            container.find('.search-input').val('');
        }
}

//닫기버튼 js
function closeToggle(obj, evt){
    var container = $(obj).closest('.search-wrapper');
        if(!container.hasClass('active')){
            container.addClass('active');
            evt.preventDefault();
        }
        else if(container.hasClass('active') && $(obj).closest('.input-holder').length == 0){
            $('.selectDIV').css('display', 'none');
            container.removeClass('active');
            // clear input
            container.find('.search-input').val('');
        }
}

//사진효과 js
$(".hover").mouseleave(
  function () {
    $(this).removeClass("hover");
  }
);

//스크롤해서 내려서 로딩된 이미지 끝에 다달았을때 다음페이지의 이미지 출력하는 스크립트
window.onscroll = function(){
		var htmlTop = $('html').scrollTop();
		console.log("htmlTop : "+htmlTop);
	
		var scroll = window.scrollY + $(window).height();
		var endY = document.body.scrollHeight + 30;
// 		console.log(scroll);
// 		console.log(endY);
// 		console.log("scroll : " + scroll);
// 	 	console.log("endY : " + endY);
		
		//content 영역의 최상위 위치 값
		var scrollTop = $(this).scrollTop();
		//content 영역의 (패딩영역합산한) content의 높이
		var innerHeight = $(this).innerHeight();
		
		
		
		//스크롤이 컨텐트아래 50 hegiht 를 넘어서면 이벤트 시작
		//scroll > endY
		
		if (scroll > endY) {
			$("#confirm").css('color', 'white');
			//10개의 사진 img 태그를 감싼 각각의 div 태그 10개를 감싼 하나의 div (pictureContent) 의 마지막 div 
			var lastPC = $('#contentBox').last();
			
		 	$.ajax({
		 	 	url: 'more.bo',
		 	 	type: 'post',
		 	 	async: false,
		 	 	data: { curPage: parseInt($('[name=curPage]').val()), search: $('[name=search]').val(), keyword: $('[name=keyword]').val() },
		 	 	success: function(data){
		 	 	 	console.log('ajax success');
		 	 		var tag = '<div class="pictureContent">';
		 			$.each(data, function(key, value){
			 			if(data != null) {
			 	 				tag += '<div class="pictureBox" onclick="detail('+ value.b_no +', \'${login_info.u_userid}\' )">';
			 	 					tag += '<figure class="snip1368">';
			 	 						tag += '<img src="background/'+ value.b_imgpath +'" class="pictures" alt="sample30"/>';
				 	 					tag	+= '<h5 style=&#39; font-family: "Gothic A1", sans-serif; &#39;>'+ value.b_title +'</h5>';
				 	 					tag	+= '<figcaption>';
				 	 					tag += '</figcaption>';
			 	 					tag += '</figure>';
								tag += '</div>';
							console.log(value);
			 			} else if(data == null) {
				 			return false;
			 			}
		 			});
		 					tag += '</div>';
		 	 				lastPC.append(tag);
		 	 	}, error: function(req, text){
		 	 	 	console.log('ajax fail');
				}
		 	});

		 	//눌렀을때 새로운값이 들어오면 바디의 높이 값이 더 증가하도록 #content의 높이를 늘려줌
		 	var bodyH = $('#content').css('height');
		 	var addPCH = lastPC.css('height');
// 		 	console.log("bodyH : " + bodyH);
// 		 	console.log("addPCH : " + addPCH);
// 		 	console.log("scroll : " + scroll);
// 		 	console.log("endY : " + endY);
		 	
		 	bodyH += addPCH;
		 	
		 	if(endY > 2350) { 
			 	$("#footer-wrap").slideDown();
				$("#scrolldown").slideDown();
				$("#scrolldown").css('display', 'none');
				$("#scrolltop").css('display', 'block');
		 	}
		 	
		} else {
		 	$("#confirm").css('color', 'gray');
		 	$("#footer-wrap").slideUp();
		 	$("#scrolltop").slideUp();
		 	$("#scrolltop").css('display', 'none');
		 	$("#scrolldown").css('display', 'block');
		}
};

</script>
</body>
</html>