<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<meta charset="UTF-8">
	<title>Room</title>
	<style>
		*{
			margin:0;
			padding:0;
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
			padding: 21px;
			
		}
		.container h1{
			text-align: left;
			padding: 5px 5px 5px 15px;
			border-left: 3px solid #ff6032;
			margin-bottom: 20px;
		}
		.roomContainer{
			width: 500px;
			height: 500px;
			overflow: auto;
			border: 1px solid black;
		}
		.roomList{
			border: none;
		}
		.roomList th{
			border: 1px solid black;
		}
		.roomList td{
			border: 1px solid black;
			text-align: center;
		}
		.roomList .num{
			width: 75px;
			text-align: center;
		}
		.roomList .room{
			color: black;			
			width: 350px;
		}
		.roomList .go{
			width: 71px;
			text-align: center;
		}
		.roomList .de{
			width: 71px;
			text-align: center;
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
		.inputTable th{
			padding: 4px;
		}
		.inputTable input{
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
		button:hover{
  background-color: #4CAF50;
  box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
  border-radius:9px;
  color: #fff;
  transform: translateY(-2px);
  font-weight: bold;
}
		body{
		background-color: #f56437fa;
		overflow-y: hidden; 
		}
		#footer-wrap { height: 230px; }
	</style>
</head>

<script type="text/javascript">
	var ws;
	window.onload = function(){
		getRoom();
		createRoom();
	}

	function getRoom(){
		commonAjax('/moment/getRoom', "", 'post', function(result){
			createChatingRoom(result);
		});
	}
	
	function createRoom(){
		$("#createRoom").click(function(){
			console.log("채팅방생성");
			var msg = {	roomName : $('#roomName').val()	};

			commonAjax('/moment/createRoom', msg, 'post', function(result){
				createChatingRoom(result);
			});

			$("#roomName").val("");
		});
	}
	function deleteRoom(roomNumber){
			var msg = {	rNumber : roomNumber	};
			console.log("채팅방삭제"+roomNumber);

			commonAjax('/moment/deleteRoom', msg, 'post', function(result){
				createChatingRoom(result);
			});
			
	}

	function goRoom(number, name){
		location.href="/moment/moveChating?roomName="+name+"&"+"roomNumber="+number;
	}
	

	function createChatingRoom(res){
		if(res != null){
			var tag = "<tr><th class='num'>순서</th><th class='room'>방 이름</th><th class='go'></th><th class='delete'></th></tr>";
			res.forEach(function(d, idx){
				var rn = d.roomName.trim();
				var roomNumber = d.roomNumber;
				tag += "<tr>"+
							"<td class='num'>"+(idx+1)+"</td>"+
							"<td class='room'>"+ rn +"</td>"+
							"<td class='go'><button type='button' onclick='goRoom(\""+roomNumber+"\", \""+rn+"\")'>참여</button></td>";
						if(${login_info.u_admin eq 'M'}){
							tag += "<td class='de'><button type='button' id='deleteRoom'  onclick='deleteRoom(\""+idx+"\")'>삭제</button></td>";
							}
						tag += "</tr>";	
			});
			$("#roomList").empty().append(tag);
		}
	}

	function commonAjax(url, parameter, type, calbak, contentType){
		$.ajax({
			url: url,
			data: parameter,
			type: type,
			contentType : contentType!=null?contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (res) {
				calbak(res);
			},
			error : function(err){
				console.log('error');
				calbak(err);
			}
		});
	}
</script>
<body>
	<div class="container">
		<br>
		<div class="container1">
		<br>
		<h1 style="font-weight: bolder;">채팅방</h1>
		<div id="roomContainer" class="roomContainer">
			<table id="roomList" class="roomList"></table>
		</div>
		<div>
			<table class="inputTable" style="width: 508px;">
				<tr>
					<c:if test="${!empty login_info }">
					<th style="width: 55px;">방 제목</th>
					<th><input type="text" name="roomName" id="roomName"></th>
					<th ><button id="createRoom" style="width: 80px;">방 만들기</button></th>
					</c:if>
				</tr>
			</table>
		</div>
		</div>
	</div>
</body>
</html>