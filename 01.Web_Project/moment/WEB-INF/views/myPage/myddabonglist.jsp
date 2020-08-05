<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>Insert title here</title>

</head>
<style>
.top {margin :0 auto; padding-bottom:0; padding-top: 30px;} 
.grid li div:nth-child(4) { font-size:80%; height:15px; padding: 10 0 0 0; }
.grid li div:nth-child(5) { font-size:80%; height:20px; padding: 0 ;}	

option { width: 200px; color:white; background-color: black;}	

.push { display: none; } 
input:focus {outline:none;}
#footer-wrap { display: none; }
</style>
<body>


<nav id="topMenu">
     <ul>
        <li><a class="menuLink" href="mypostlist.moment"> 내 게시글 </a></li>
        <li><a class="menuLink" href="myddabong.moment" style= "font-weight:bold; color: #f56437fa;"> 좋아요  </a></li>
        <li><a class="menuLink" href="myfavorite.moment"> 즐겨찾기 </a></li>                     
     </ul>
</nav>
	<div class="contentbody" style="padding-top: 50px;">
		<div class="top"></div>

		<!-- 좋아요 리스트 -->
		<c:if test="${empty page.list}">
			<div style="border: 1px solid gray; width: 1237px; height: 400px;">
				<div style="padding-bottom: 150px;"></div>
				<div style="color: gray;">좋아요한 게시물이 없습니다</div>
			</div>

		</c:if>
		<c:if test="${!empty page.list}">
			<div id="data-list" style="clear: both;">
				<ul class="grid">
					<c:forEach items="${page.list}" var="vo">
						<li>

							<div></div>
							<div style="padding: 0; width: 100%; padding-top: 10px;">
								<a onclick="detail(${vo.b_no}, '${login_info.u_userid}')"><img
									src="img/background/${vo.b_imgpath}"
									style="width: 100%; height: 100%;"></a>
							</div>
							<div style="padding: 5px; height: 10px;">
								<a style="font-weight: bold;" onclick="detail(${vo.b_no}, '${login_info.u_userid}')">${vo.b_title}</a>
							</div>
							<div>
								<a href="memberpostlist.moment?userid=${vo.b_userid}">${vo.u_nick}</a>
							</div>
							<div>
								<img src="img/eye.png" style="color: white; width: 15px;">${vo.b_readcnt}
								/ <img src="img/heart.png" style="font-size: 80%; width: 15px">${vo.b_ddabong}
								&nbsp;&nbsp;&nbsp;${vo.b_writedate}
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<!-- 페이지  -->
		<div class='btnSet' class="top">
			<jsp:include page="/WEB-INF/views/include/page.jsp" />
		</div>



		<!-- 검색   -->
		<form method="post" action="myddabong.moment">
			<input type="hidden" name="curPage" value="1" />
			<div class="top"
				style="background-color: black; float: right; padding-top: 30px">
				<select row="2" name="search" class="mypageselect" style="font-family: 'Noto Serif KR', serif !important;">
					<option value="all" ${page.search eq 'all' ? 'selected' : ''}>제목+내용
					</option>
					<option value="title" ${page.search eq 'title' ? 'selected' : ''}>제목</option>
					<option value="content"
						${page.search eq 'content' ? 'selected' : ''}>내용</option>
				</select> <input type="text" id="searchinput" name='keyword'  style="color: white;" 
					value="${page.keyword}" /> <a class="btn-fill"
					style="color: #f56437fa;" onclick="$('form').submit()">SEARCH</a>
			</div>
		</form>
	</div>

	<!-- detail div -->
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

<!-- 디테일 js -->
<script type="text/javascript" src="js/detail.js"></script>

<script type="text/javascript">

/* 그리드 페이지 설정 */
$('#data-list ul').css('height', 
		( ($('.grid li').length%4>0 ? 1 : 0) + Math.floor($('.grid li').length/5) )
				* $('.grid li').outerHeight(true) - 20 );

</script>
</body>
</html>