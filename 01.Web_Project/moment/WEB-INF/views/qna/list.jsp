<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link>
<style>
@import url('https://fonts.googleapis.com/css2?family=Suez+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap');
.menu-box{ width: 80%; height: 50px; margin: 10px auto; text-align: center; }
.menu ul{ margin: 0 auto; }
.menu li{ float: none; margin: 0 10px;}
#content { margin: 100px auto; width:1130px; border-radius:35px;  box-shadow: 0 0 20px 0 rgba(12, 12, 12, 0.65), 0 5px 5px 0 rgba(0, 0, 0, 0.44);
 position: relative; z-index: 1; padding-bottom: 50px; padding-top:20px; background-color:white;}
#title { width: 100%; height: auto; font-family: 'Suez One', serif; color: black; font-size: 2em; }
#qna-body { margin: 0 auto; text-align: center; background-color: #f56437fa; }
input[type=radio], input[type=checkbox]{ width: 18px; margin: 0 5px 3px; vertical-align: middle; }

.menu-box { height: inherit; }
select { height: 32px; font-size: 1em; font-family: 'Sriracha', cursive; padding: 0 0 1px 3px; borr: 1px solid white; border-radius: 5px; }
#keyBox { border: 1px solid black; border-radius: 5px; }

/* 테이블 스타일 */
#qna-table th {
  border: 2px solid #ddd;
  padding: 8px;
  text-align: center;
  color: white;
  font-family: 'Jua', sans-serif;
}
#qna-table td {
	color: black;
	border: 2px solid #ddd;
	font-family: 'Jua', sans-serif;
}
/* #fc7703 */
#qna-table tr:nth-child(even){ background-color: white; }
#qna-table tr:hover {background-color: #fc7703;}
#qna-table tr:nth-child(even):hover { background-color: #fc7703; }
#qna-table th {
  padding-top: 12px;
  padding-bottom: 12px;
  background-color: black;
  font-size: 1.3em;
  font-weight: normal;
}
#question_write {
	border: 1px solid: black;
	color: black;
		font-family: 'Jua', sans-serif;
	
}

.qa { background-color: #ff980091  !important; }
.qa:hover { background-color: #fc7703 !important; }
.an { background-color: #FF9800  !important; }
.an:hover { background-color: #fc7703 !important;  }

table { width: 80%; margin: 0 auto; border: 1px solid; border-collapse: collapse; }
table th, table td { border: 1px solid; padding: 5px 10px; }
table td a:hover { font-variant: bold; }
input { height: 22px; padding: 3px 5px; font-size: 15px; }
table tr td label:not(:last-child){ margin-right: 20px; }
textarea { width: 100%; height: 200px; resize: none; }

</style>
</head>
<body id="qna-body">
<div id="title">
<h2>Q & A</h2>
</div>
<div class="menu-box">
	<div id="list-top" >
	<div style="margin-bottom: 20px">
				<select name="search" class="w-px80">
					<option value="title" ${page.search eq title ? 'selected' : '' }>TITLE</option>
					<option value="content" ${page.search eq content ? 'selected' : '' }>CONTENT</option>
					<option value="writer" ${page.search eq writer ? 'selected' : '' }>WRITER</option>
					<option value="all" ${page.search eq all ? 'selected' : '' }>ALL</option>
				</select>
			<input type="text" id="keyBox" name="keyword" class="w-px300" value="${page.keyword}"/>
			<a class="btn-fill" onclick="search_list();">SEARCH</a>
		</div>
		<div style="margin-bottom: 20px; margin-top: 60px;">
		<a class="qna-btn-fill" id="faq">자주 묻는 질문과 답변</a>
		<a class="qna-btn-empty" id="all_list">전체 질문 목록</a>
		<c:if test="${!empty login_info }">
			<a class="qna-btn-empty" id="myq">내 질문 목록</a>
			<a class="qna-btn-empty" id="question">질문하기</a>
		</c:if>
		<c:if test="${login_info.u_admin eq 'M' }">	
			<a class="qna-btn-empty" id="qlist">답변 할 목록</a>
			<a class="qna-btn-empty" id="alist">답변 완료 목록</a>
		</c:if>
		</div>
		</div>
</div>
<div id="contents">
<!-- 자주 묻는 질문과 답변 -->
	<div class="faq_list" style="overflow: hidden;">
	<table id="qna-table">
		<tr>
			<th class="w-px60" >번호</th>
			<th >제목</th>
			<th class="w-px100">작성자</th>
		</tr>
		<c:forEach items="${page.list}" var="vo">
			<tr>
				<td>${vo.no }</td>
				<td class="left"><a class="content_btn${vo.id }" onclick="content_view(${vo.id})">${vo.title }</a></td>
				<td>${vo.name }</td>
			</tr>
			<tr class="content_view${vo.id}" style="display: none; cursor: pointer;" onclick="content_close(${vo.id})">
				<td>답변</td>
				<td colspan="5" class="left">${vo.content }</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<div id="append">
	</div>
	
	<div class="btnSet">
		<jsp:include page="/WEB-INF/views/include/page.jsp" />
	</div>
</div>

<!-- 글작성 -->
<div id="question_write" style="display: none; ">
	<form action="insert.qn" method="post">
	<table>
		<tr>
			<th class="w-px120">제목</th>
			<td><input type="text" name="title" style="width: 99%" class="need" title="제목" onkeypress="if(event.keyCode == 13){if(!necessary()) return false;}" /></td>
		</tr>
		<tr>
			<th>문의 내용</th>
			<td><textarea name="content" style="width: 100%; height: 200px; resize: none;" class="need" title="내용" ></textarea></td>
		</tr>
	</table>
	<div class="btnSet">
		<a class="btn-fill" style="font-family: 'Jua', sans-serif" onclick="if( necessary() ){ $('form').submit() }" >저장</a>
	</div>
	</form>
</div>

<script type="text/javascript" src="js/qna_button.js"></script>
<script type="text/javascript" src="js/need_check.js"></script>
<script type="text/javascript">
function content_view(id){
	$('.content_btn'+id).css('color','black');
	$('.content_view'+id).css('display','table-row');
	$('.content_view'+id).siblings('td').eq(1).attr('colspan','5');
}

function content_close(id){
	$('.content_btn'+id).css('color','black');
	$('.content_view'+id).css('display','none');
}
//날짜 형식 포맷
function formatDate(date) { 
	var d = new Date(date), month = '' + (d.getMonth() + 1), day = '' + d.getDate(), year = d.getFullYear();
	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;
	
	return [year, month, day].join('-'); 
}


//글삭제
function write_delete(qid, result, admin){
	if(admin == 'M'){
		if(result == 'Y') {
			 alert('답변이 완료된 글은 삭제가 불가능합니다. '); return; 
			 }
	}
	
	if(!confirm("정말삭제?")) return;
	$.ajax({
		type: 'post',
		url: 'delete.qn',
		data: {id: qid},
		success: function(data){
			location.reload();
		}, error: function(req, text){
			alert(text+":"+req.status);
		}
	});
}

//답변 등록
function answer(a_id, a_content){
	if(!confirm("답변을 등록 하시겠습니까?")) return;
	$.ajax({
		type: 'post',
		url: 'answer.qn',
		data: { id: a_id, content: a_content },
		success: function(data){
			alert('답변이 등록되었습니다.');
			location.reload();
		}, error: function(req, text){
			alert(text+":"+req.status);
		}
	});
}

$('#faq').on('click', function(){
	$('#question_write').css('display','none');
	$('#contents').css('display','block');
	$('.faq_list').css('width','100%');
	$('.faq_list').css('height','100%');
	$('#append').html('');
});

//내 질문 목록
$('#myq').on('click', function(){
	$('#question_write').css('display','none');
	$('#contents').css('display','block');
	$('.faq_list').css('width','0');
	$('.faq_list').css('height','0');
	$('#append').html('');
	$.ajax({
		type: 'post',
		url: 'myquestion',
		success: function(data){
			var tag = '<table id="qna-table">';
			tag += '<tr><th class="w-px60">번호</th><th>제목</th><th class="w-px100">작성자</th><th class="w-px100">작성일자</th><th class="w-px100">질문 현황</th><th class="w-px100">글삭제</th></tr>';
			$.each(data, function(key, value){
				tag += '<tr><td>'+value.no+'</td>';								
				tag += '<td class="left" onclick="content_view('+value.id+')" ><a class="content_btn'+value.id+'" onclick="content_view('+value.id+')">'+value.title+'</a></td>' ;     
				tag += '<td>'+value.name+'</td><td>'+formatDate(value.writedate)+'</td>';
				tag += '<td>'+ (value.answer_result == 'Y' ? '답변완료' : '진행중') +'</td>';
				tag += '<td><a onclick="write_delete('+value.id+' ,\''+value.answer_result+'\' ,\'${login_info.u_admin}\')">삭제</a></td>';
				tag += '</tr>';
				tag += '<tr class="content_view'+value.id+'" style="display: none; cursor: pointer;" onclick="content_close('+value.id+')">'
					+'<td>질문</td><td colspan="5" class="left">'+value.content.replace(/\n/g, '<br/>')+'</td></tr>'
				tag += '<tr class="content_view'+value.id+'" style="display: none; cursor: pointer;" onclick="content_close('+value.id+')">'
					+'<td>답변</td><td colspan="5" class="left">'+(value.answer_content == null ? '아직 등록된 답변이 없습니다.' : value.answer_content.replace(/\n/g, '<br/>'))+'</td></tr>'
			console.log(value);
			});
			tag += '</table>';
			$('#append').html(tag);
		}, error: function(req, text){
			alert(text+":"+req.status);
		}
	});
});

$('#question').on('click', function(){
	$('#contents').css('display','none');
	$('#question_write').css('display','block');
	$('.faq_list').css('width','0');
	$('.faq_list').css('height','0');
	$('#append').html('');
});

//답변해야하는 목록
$('#qlist').on('click', function(){
	$('#question_write').css('display','none');
	$('#contents').css('display','block');
	$('.faq_list').css('width','0');
	$('.faq_list').css('height','0');
	$('#append').html('');

	$.ajax({
		type: 'post',
		url: 'question_list.qn',
		success: function(data){
			var tag = '<table id="qna-table">';
			tag += '<tr><th class="w-px60">번호</th><th>제목</th><th class="w-px100">작성자</th><th class="w-px100">작성일자</th><th class="w-px100">질문 현황</th><th class="w-px60">글삭제</th></tr>';
			$.each(data, function(key, value){
				tag += '<tr><td>'+value.no+'</td>';								
				tag += '<td class="left" onclick="content_view('+value.id+')" ><a class="content_btn'+value.id+'" onclick="content_view('+value.id+')">'+value.title+'</a></td>' ;     
				tag += '<td>'+value.name+'</td><td>'+formatDate(value.writedate)+'</td>';
				tag += '<td>'+ (value.answer_result == 'Y' ? '답변완료' : '진행중') +'</td>';
				tag += '<td><a onclick="write_delete('+value.id+')">삭제</a></td>';
				tag += '</tr>';
				tag += '<tr class="content_view'+value.id+'" style="display: none; cursor: pointer;" onclick="content_close('+value.id+')">'
					+'<td>질문</td><td colspan="5" class="left">'+value.content+'</td></tr>'
				tag += '<tr class="content_view'+value.id+'" style="display: none;">'
					+'<td>답변</td><td colspan="5" class="left"><textarea name="content" id="content'+value.id+'" class="need" title="내용" ></textarea>'
					+'<a class="btn-fill" onclick="if( $(\'#content'+value.id+'\').val() != \'\'){ answer('+value.id+',$(\'#content'+value.id+'\').val()) } else{ alert(\'내용을 입력 바랍니다.\')}" >답변 등록 하기</a></td></tr>'
			console.log(value);
			});
			tag += '</table>';
			$('#append').html(tag);
		}, error: function(req, text){
			alert(text+":"+req.status);
		}
	});
});

//답변한 목록
$('#alist').on('click', function(){
	$('#question_write').css('display','none');
	$('#contents').css('display','block');
	$('.faq_list').css('width','0');
	$('.faq_list').css('height','0');
	$('#append').html('');
	$.ajax({
		type: 'post',
		url: 'answer_list.qn',
		success: function(data){
			var tag = '<table id="qna-table">';
			tag += '<tr><th class="w-px60">번호</th><th>제목</th><th class="w-px100">작성자</th><th class="w-px100">작성일자</th><th class="w-px100">질문 현황</th><th class="w-px60">글삭제</th></tr>';
			$.each(data, function(key, value){
				tag += '<tr><td>'+value.no+'</td>';								
				tag += '<td class="left" onclick="content_view('+value.id+')" ><a class="content_btn'+value.id+'" onclick="content_view('+value.id+')">'+value.title+'</a></td>' ;     
				tag += '<td>'+value.name+'</td><td>'+formatDate(value.writedate)+'</td>';
				tag += '<td>'+ (value.answer_result == 'Y' ? '답변완료' : '진행중') +'</td>';
				tag += '<td><a onclick="write_delete('+value.id+')">삭제</a></td>';
				tag += '</tr>';
				tag += '<tr class="content_view'+value.id+'" style="display: none; cursor: pointer;" onclick="content_close('+value.id+')">'
					+'<td>질문</td><td colspan="5" class="left">'+value.content.replace(/\n/g, '<br/>')+'</td></tr>'
				tag += '<tr class="content_view'+value.id+'" style="display: none; cursor: pointer;" onclick="content_close('+value.id+')">'
					+'<td>답변</td><td colspan="5" class="left">'+(value.answer_content == null ? '아직 등록된 답변이 없습니다.' : value.answer_content.replace(/\n/g, '<br/>'))+'</td></tr>'
			console.log(value);
			});
			tag += '</table>';
			$('#append').html(tag);
		}, error: function(req, text){
			alert(text+":"+req.status);
		}
	});
});

//전체 목록
$('#all_list').on('click', function(){
	search_list();
});

function search_list(){
	$('#question_write').css('display','none');
	$('#contents').css('display','block');
	$('.faq_list').css('width','0');
	$('.faq_list').css('height','0');

	
	$('#append').html('');
	$.ajax({
		type: 'post',
		url: 'all_list.qn',
		data: { search: $('[name=search]').val(), keyword : $('[name=keyword]').val() },
		success: function(data){
			var tag = '<table id="qna-table">';
			tag += '<tr><th class="w-px60">번호</th><th>제목</th><th class="w-px100">작성자</th><th class="w-px100">작성일자</th><th class="w-px100">질문 현황</th><c:if test="${login_info.u_admin eq \'M\' }"><th class="w-px60">글삭제</th></c:if></tr>';
			$.each(data, function(key, value){
				tag += '<tr><td>'+value.no+'</td>';								
				tag += '<td class="left" onclick="content_view('+value.id+')"><a class="content_btn'+value.id+'" onclick="content_view('+value.id+')">'+value.title+'</a></td>' ;     
				tag += '<td>'+value.name+'</td><td>'+formatDate(value.writedate)+'</td>';
				tag += '<td>'+ (value.answer_result == 'Y' ? '답변완료' : '진행중') +'</td>';
				tag += '<c:if test="${login_info.u_admin eq \'M\' }"><td><a onclick="write_delete('+value.id+')">삭제</a></td></c:if>';
				tag += '</tr>';
				tag += '<tr class="content_view'+value.id+' qa" style="display: none; cursor: pointer;" onclick="content_close('+value.id+')">'
					+'<td>질문</td><td colspan="5" class="left">'+value.content.replace(/\n/g, '<br/>')+'</td></tr>'
				tag += '<tr class="content_view'+value.id+' an" style="display: none; cursor: pointer;" onclick="content_close('+value.id+')">'
					  +'<td>답변</td><td colspan="5" class="left">'+(value.answer_content == null ? '아직 등록된 답변이 없습니다.' : value.answer_content.replace(/\n/g, '<br/>'))+'</td></tr>'
			console.log(value);
			});
			tag += '</table>';
			$('#append').html(tag);
		}, error: function(req, text){
			alert(text+":"+req.status);
		}
	});
		$('#all_list').removeClass();
		$('#faq').removeClass();
		$('#myq').removeClass();
		$('#question').removeClass();
		$('#qlist').removeClass();
		$('#alist').removeClass();
		$('#all_list').addClass('qna-btn-fill');
		$('#faq').addClass('qna-btn-empty');
		$('#myq').addClass('qna-btn-empty');
		$('#question').addClass('qna-btn-empty');
		$('#qlist').addClass('qna-btn-empty');
		$('#alist').addClass('qna-btn-empty');
}
</script>
</body>
</html>