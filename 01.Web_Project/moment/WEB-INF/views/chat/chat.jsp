<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<meta charset="UTF-8">
	<title>Chating</title>
	<style>
		*{
			margin:0;
			padding:0;
		}
		body{
		background-color: #f56437fa;
		overflow-y: hidden !imortant; 
		}
		.container{
		position: relative;
		z-index:1;
		width: 550px;
		margin: 130px auto;
		border-radius: 40px;
		background-color: #00000099;
		color: black;
		padding: 18px;
    box-shadow: 0 0 20px 0 rgba(5, 5, 5, 0.56), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
		}
		.container1{
			background: #fffffffc;
			border-radius: 40px;
			padding: 20px 20px 10px 20px;
			 margin-top: 22px;
			
		}
		.container h1{
			text-align: left;
			padding: 5px 5px 5px 15px;
			border-left: 3px solid #ff6032;
			margin-bottom: 20px;
			margin-top: 20px;
		}
		.chating{
/* 			background-color: #fff; */
/* 			width: 457px; */
/* 			height: 495px; */
/* 			overflow: auto; */
/* 			color: black; */
/* 			margin-bottom: 26px; */
/* 			padding: 20px; */
/* 			border: 1px solid black; */
/* 			border-radius: 30px; */
				width: 500px;
			height: 500px;
			overflow: auto;
			
			border: 1px solid black;
		}
		.chating p{
			margin-left:10px;
			color: black;
			text-align: left;
			font-size: larger;
		}
		input{
			width: 330px;
			height: 25px;
			font-family: "Roboto", sans-serif;
  			outline: 0;
 			background: #dddddd;
			border: 0;
			padding: 20px;
 			box-sizing: border-box;
 			font-size: 14px;
  			border-radius:7px;
		}
		#yourName{
					margin: 15px 10px 15px 0;
		
		}
		#yourMsg{
			display: none;
			margin: 10px 10px 10px 0;
					}
		button{
			background-color: #fff;
			margin: 3px;
  			text-transform: uppercase;
  			outline: 0;
  			background: #fb5f2e;
  			width: 88%;
  			border: 0;
  			border-radius:23px;
  			padding: 10px;
  			color: #FFFFFF;
  			font-size: 14px;
  			cursor: pointer;
		}
					
	</style>
</head>

<script type="text/javascript">
	var ws;

	function wsOpen(){
		ws = new WebSocket("ws://" + location.host + "/moment/chating");
		wsEvt();
	}
		
	function wsEvt() {
		ws.onopen = function(data){
			//소켓이 열리면 초기화 세팅하기
		}
		
		ws.onmessage = function(data) {
			var msg = data.data;
			if(msg != null && msg.trim() != ''){
				$("#chating").append("<p>" + msg + "</p>");
			}
		}

		document.addEventListener("keypress", function(e){
			if(e.keyCode == 13){ //enter press
				send();
			}
		});
	}

	function chatName(){
		var userName = $("#userName").val();
		if(userName == null || userName.trim() == ""){
			alert("사용자 이름을 입력해주세요.");
			$("#userName").focus();
		}else{
			wsOpen();
			$("#yourName").hide();
			$("#yourMsg").show();
		}
	}

	function send() {
		var uN = $("#userName").val();
		var msg = $("#chatting").val();
		ws.send(uN+" : "+msg);
		$('#chatting').val("");
	}
</script>
<body>
	<div id="container" class="container">
	<div class="container1">
	
		<h1>Chat</h1>
		<div id="chating" class="chating">
		</div>
		
		<div id="yourName">
			<table class="inputTable">
				<tr>
					<th style="width:65px;">사용자명</th>
					<th><input type="text" name="userName" style="font-size: 20px; font-weight: bolder"id="userName" value="${login_info.u_nick}" ></th>
					<th><button onclick="chatName()" id="startBtn" style="width:90px;">이름 등록</button></th>
				</tr>
			</table>
		</div>
		<div id="yourMsg">
			<table class="inputTable">
				<tr>
					<th style="width:65px;">메시지</th>
					<th><input id="chatting"  style="font-size: 20px;" placeholder="보내실 메시지를 입력하세요." maxlength="30"></th>
					<th><button onclick="send()" id="sendBtn" style="width:90px;">보내기</button></th>
				</tr>
			</table>
		</div>
		</div>
	</div>
</body>