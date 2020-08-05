<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.page_on, .page_off, .page_prev, .page_first, .page_next, .page_last { display: inline-block; line-height: 30px; margin: 0 !important; }

.page_on { color: #fff; font-weight: bold; background-color: gray; border:1px solid gray; } 
.page_off, .page_on { background-color: black; border: 1px solid white; min-width:22px; padding: 0 5px 2px; cursor: pointer; }
.page_prev, .page_first, .page_next, .page_last {width: 30px; border:1px solid #d0d0d0; text-indent: -999999px; }

.page_prev {background: url("img/page_next.jpg") center no-repeat;}
.page_first {background: url("img/page_first.jpg") center no-repeat;}
.page_next {background: url("img/page_next.jpg") center no-repeat;}
.page_last {background: url("img/page_last.jpg") center no-repeat;}

</style>


<div class="page_list">
	<c:if test="${page.curBlock gt 1}">
		  <a class = "page_first" onclick = "go_page(1)">처음</a>
		  <a class = "page_prev" onclick="go_page(${page.beginPage - page.blockPage})">이전</a>
	</c:if>
	
	
	<c:forEach var="no"  begin="${page.beginPage}" end="${page.endPage}">
		<c:if test="${no eq page.curPage}"> <!-- 선택한 페이지가 현재페이지면 클릭할 수 없게 -->
			<span class="page_on">${no}</span>
		</c:if>
		
		<c:if test="${no ne page.curPage}">
			<a class="page_off" onclick="go_page(${no})">${no}</a>
		</c:if>
		
	
	</c:forEach>


	<c:if test="${page.curBlock lt page.totalBlock}">
		<a class="page_next" onclick="go_page(${page.endPage+1})">다음</a>
		<a class="page_last"  onclick="go_page(${page.totalPage})">마지막</a>
	</c:if>


</div>

<script>
	function go_page(no){
		$('[name=curPage]').val(no);
		$('form').submit();		
	}


</script>
    