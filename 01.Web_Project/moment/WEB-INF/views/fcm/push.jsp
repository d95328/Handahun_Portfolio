<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table {
	width: 600px;
	height: 650px;
	margin: 50px auto;
	background-color: white;
	border-radius: 25px;
	color: black;
	
}
.fcm{
  position: relative;
  z-index: 1;
  background: #FFFFFF;
  max-width: 650px;
  width:100%;
  margin: 4% auto ;
  color: black;
  padding: 10px;
  text-align: center;
  border-radius: 25px;
  box-shadow: 0 0 20px 0 rgba(5, 5, 5, 0.56), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
  
}
.fcm input[class='topictitle'] {
  font-family: "Roboto", sans-serif;
  outline: 0;
  background: #f2f2f2;
  width: 85%;
  border: 0;
  padding: 15px;
  margin: 0 0 10px;
  box-sizing: border-box;
  font-size: 14px;
  border-radius: 6px;
}
.fcm input[class='topicbody'] {
  font-family: "Roboto", sans-serif;
  outline: 0;
  background: #f2f2f2;
  width: 100%;
  border: 0;
  margin: 0 0 10px;
  padding: 15px;
  box-sizing: border-box;
  font-size: 14px;
  border-radius: 6px;
}
.fcm input[class='topictitle'] {
  font-family: "Roboto", sans-serif;
  outline: 0;
  background: #f2f2f2;
  width: 85%;
  border: 0;
  margin: 0 0 15px;
  padding: 15px;
  box-sizing: border-box;
  font-size: 14px;
  border-radius: 6px;
}
.topiclist {
	border: 1px solid #ffBB00;
}
.topictitle {
	width: 80px;
}
.topicbody {
	width: 200px;
}
/* box-shadow: 0px 7px 7px rgba(255, 236, 230, 0.36) */
#sendBtn{
 text-transform: uppercase;
  outline: 0;
  background: #4CAF50;
  width: 66px;
  border: 0;
  border-radius:10px;
  padding: 15px;
  color: #FFFFFF;
  font-size: 14px;
  -webkit-transition: all 0.3 ease;
  transition: all 0.3 ease;
  cursor: pointer;
}
#sendBtn:hover,#sendBtn:active,#sendBtn:focus {
  background: #43A047;
}
#sendBtn:hover{
  background-color: #fb5f2e;
  box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
  border-radius:3px;
  color: #fff;
  transform: translateY(-7px);
  font-weight: bold;
}
/* .fcm { */
/* width: 650px; */
/*   padding: 7% 0 0; */
/*   margin: auto; */
  
/* } */

body{
background: #f56437fa;
overflow-y: hidden;
}
</style>
</head>
<body>
<div class="fcm">
<h1 style="font-weight: bolder">Push Service</h1>
	<table class="pushbox">
	
	<tr><th style="width:50px;">지역</th><th style="width:150px;">제목</th><th style="width:300px;">내용</th><th>전송</th></tr>
	<tr>
		<td>광주</td>
		<td><input type="text" class="topictitle" id="gwangju_title" value="광주 알림" /></td>
		<td><input type="text" class="topicbody" id="gwangju_body" name="content" /></td>	
		<td><button onclick="gwangjufcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>서울</td>
		<td><input type="text" class="topictitle" id="seoul_title" value="서울 알림"/></td>
		<td><input type="text" class="topicbody" id="seoul_body" name="content" /></td>	
		<td><button onclick="seoulfcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>부산</td>
		<td><input type="text" class="topictitle" id="busan_title" value="부산 알림"/></td>
		<td><input type="text" class="topicbody" id="busan_body" name="content" /></td>	
		<td><button onclick="busanfcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>경기</td>
		<td><input type="text" class="topictitle" id="kyungki_title" value="경기도 알림"/></td>
		<td><input type="text" class="topicbody" id="kyungki_body" name="content" /></td>	
		<td><button onclick="kyungkifcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>강원</td>
		<td><input type="text" class="topictitle" id="kangwon_title" value="강원도 알림"/></td>
		<td><input type="text" class="topicbody" id="kangwon_body" name="content" /></td>	
		<td><button onclick="kangwonfcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>충청</td>
		<td><input type="text" class="topictitle" id="chungchung_title" value="충청도 알림"/></td>
		<td><input type="text" class="topicbody" id="chungchung_body" name="content" /></td>	
		<td><button onclick="chungchungfcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>전라</td>
		<td><input type="text" class="topictitle" id="junra_title" value="전라도 알림"/></td>
		<td><input type="text" class="topicbody" id="junra_body" name="content" /></td>	
		<td><button onclick="junrafcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>경상</td>
		<td><input type="text" class="topictitle" id="kyungsang_title" value="경상도 알림"/></td>
		<td><input type="text" class="topicbody" id="kyungsang_body" name="content" /></td>	
		<td><button onclick="kyungsangfcm()" id="sendBtn">전송</button></td>
	</tr>
	<tr class="topiclist">
		<td>제주</td>
		<td><input type="text" class="topictitle" id="jeju_title" value="제주도 알림"/></td>
		<td><input type="text" class="topicbody" id="jeju_body" name="content" /></td>	
		<td><button onclick="jejufcm()" id="sendBtn">전송</button></td>
	</tr>

</table>
<script type="text/javascript">
	$('#footer-wrap').css('display', 'none');

</script>
</div>
<script type="text/javascript" src="js/fcmtest.js"></script>
</body>
</html>