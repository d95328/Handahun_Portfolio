<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>  
<style type="text/css">

table td {
	text-align: left;
}

.valid, .invalid {
	font-size: 13px;
	font-weight: bold;
}

.valid {
	color: green
}

.invalid {
	color: red
}


</style>
<link rel="stylesheet" href="css/join.css">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript">
	
</script>

</head>
<body>
<div class="join_page">
	<div class="form">
	<h1 style="font-weight: bolder">회원 정보 수정</h1>
	<p 	style="margin: 0 auto; font-size: 13px; padding-bottom: 5px; float:right;">
	*은	필수 입력 항목 입니다.</p>

	<form action="userUpdate" method="post"  enctype="multipart/form-data" >
		<table class='joinbox'>
			<tr><th> 프로필</th>
		<td class='td-img left'>
				<input type="file" name='file' id="u_profileimg" maxlength="20"/>
			<label>
				<img src='img/ps.png' class='file-img' style="width:70px; height:70px;" />
			<span id='preview'></span>
			<span id='file-name' ></span>
			<span id='delete-file' style='color:red; margin-left: 20px; display: none;'><i class='fas fa-times font-img'></i></span>
			</label>
		</td>
		</tr>
			<tr>
				<th class="w-px160">성명</th>
				<td><input type="text" name="u_name" maxlength="5" readonly="readonly" value="${vo.u_name }"/></td>
			</tr>
			<tr>
				<th class="w-px160">*닉네임</th>
				<td><input type="text" name="u_nick" class="chk" maxlength="20" value="${vo.u_nick }"/>
				 <a class="check_btn"	id="btn_nick">중복확인</a><br>
					<div class="valid">닉네임을 입력하세요</div></td>
			</tr>
			<tr>
				<th> 아이디</th>
				<td><input type="text" name="u_userid"  value="${vo.u_userid }" maxlength="40" readonly="readonly" class="chked"/> 
			</tr>
			<tr>
				<th>* 비밀번호</th>
				<td><input type="password" name="u_userpw" class="chk" maxlength="20" value="${vo.u_userpw }"/>
					<div class="valid">비밀번호를 입력하세요(영문대/소문자, 숫자를 모두 포함)</div></td>
			</tr>
			<tr>
				<th>* 비밀번호확인</th>
				<td><input type="password" name="u_userpw_ck" class="chk" maxlength="20" value="${vo.u_userpw }"/>
					<div class="valid">비밀번호를 다시 입력하세요.</div></td>
			</tr>
			<tr>
				<th>지역 선택</th>
				<td>
					<select name="u_local" style="width: 288px; height: 47.5px;">
						<option value="" ${vo.u_local eq '' ? 'checked' : '' }>지역 선택</option>
						<option value="서울특별시" ${vo.u_local eq '서울특별시' ? 'checked' : '' }>서울특별시</option>
						<option value="광주광역시" ${vo.u_local eq '광주광역시' ? 'checked' : '' }>광주광역시</option>
						<option value="부산광역시" ${vo.u_local eq '부산광역시' ? 'checked' : '' }>부산광역시</option>
						<option value="경기도" ${vo.u_local eq '경기도' ? 'checked' : '' }>경기도</option>
						<option value="강원도" ${vo.u_local eq '강원도' ? 'checked' : '' }>강원도</option>
						<option value="충청도" ${vo.u_local eq '충청도' ? 'checked' : '' }>충청도</option>
						<option value="전라도" ${vo.u_local eq '전라도' ? 'checked' : '' }>전라도</option>
						<option value="경상도" ${vo.u_local eq '경상도' ? 'checked' : '' }>경상도</option>
						<option value="제주도" ${vo.u_local eq '제주도' ? 'checked' : '' }>제주도</option>
				 	</select>
				</td>
			</tr>
		</table>
	</form>
	<div class="btnSet">
		<a id='login_btn' onclick="gojoin()">회원 정보 수정</a> 
		<a class="btn-empty"
			onclick="history.go(-1)">취 소</a>
	</div>
	</div>
</div>
<script type="text/javascript" src="js/image_preview.js"></script>
<script type="text/javascript" src="js/file_attach.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="js/check_join_1.js"></script>
<script type="text/javascript">
window.onkeydown = function()	{
	if(event.keyCode == 13){
		gojoin();
	}
};
function gojoin() {
	//중복확인 여부에 따라 
	if($('[name=u_userid]').hasClass('chked')){
		if($('[name=u_userid]').siblings('div').hasClass('invalid')){
				alert('회원수정 불가 \n ' + join.u_userid.unusable.desc);
				$('[name=u_userid]').focus();
				return;
			}
	}else{
		if(!item_check($('[name=u_userid]') ) ) return;
		else {
			alert('회원수정 불가 \n ' + join.u_userid.valid.desc);
			$('[name=u_userid]').focus();
			return;
		}
	}
	if($('[name=u_nick]').hasClass('chked')){
		if($('[name=u_nick]').siblings('div').hasClass('invalid')){
				alert('회원수정 불가 \n ' + join.u_nick.unusable.desc);
				$('[name=u_nick]').focus();
				return;
			}
	}else{
		if(!item_check($('[name=u_nick]') ) ) return;
		else {
			alert('회원수정 불가 \n ' + join.u_nick.valid.desc);
			$('[name=u_nick]').focus();
			return;
		}
	}
	
	if(!item_check($('[name=u_userpw]') ) ) return;
	if(!item_check($('[name=u_userpw_ck]') ) ) return;

	alert("회원 정보수정 완료!\n 수정한 정보로 다시 로그인하세요! ");
	$('form').submit();
	
}//gojoin

function item_check(item){
	var data = join.tag_status(item);
	if(data.code =='invalid'){
		alert('회원수정 불가\n' + data.desc);
		item.focus();
		return false;
	}else return true;
}

	$('#btn_nick').on('click', function() {
		nick_check();
	});

	function nick_check() {
		var $u_nick = $('[name=u_nick]');
		var data = join.tag_status($u_nick);
		if(data.code != 'valid'){
			alert('닉네임을 입력하세요 !');
			$u_nick.focus();
			return;
		}
	$.ajax({
		type:'post',
		url: 'usernick_check',
		data: {u_nick: $u_nick.val()},
		success: function(data){
			data = join.nick_usable(data);
			display_status( $u_nick.siblings('div'), data);//상태를 표현
			$u_nick.addClass('chked');
		},error: function(req, text){
			alert(text+":"+req.status);
			}
		});
	}

// 			유효성 검사
			$('.chk').on('keyup', function(){
				if($(this).attr('name')=='u_nick'){
						$(this).removeClass('chked');
						validate($(this));
					
				}else validate($(this));
				
				validate($(this));
			});

			/* 유효성판단할 함수선언 */
			function validate( t ){
				var data = join.tag_status( t );
				display_status( t.siblings('div'), data);
			}
			function display_status(div, data){
				div.text(data.desc);
				div.removeClass('valid invalid');
				div.addClass(data.code );
			}

// 			$('#footer-wrap').css('display', 'none');
</script>


</body>
</html>