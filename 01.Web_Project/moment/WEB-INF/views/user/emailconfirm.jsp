<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<h3>인증 함</h3>
	<script type="text/javascript">
// 		var userid = '${userid}';
		alert('회원가입을 축하합니다. 이제 로그인이 가능 합니다.');
// 		"<a href='http://localhost/iot/member/emailConfirm?userid=" + vo.getUserid() +

// 		window.opener.location.href="http://localhost/iot"; // 브라우저창 닫기
		window.open('', '_self', ''); // 브라우저창 닫기
		window.close(); // 브라우저 창 닫기
	</script>
</body>
</html>