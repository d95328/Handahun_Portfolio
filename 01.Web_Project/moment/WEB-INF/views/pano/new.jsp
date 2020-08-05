<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDrfll4QoaTNLPA3Zhpd0P_72bmSVjqNYk&libraries=places&callback=initAutocomplete" 
async defer></script>
<style>

.push { display: none; }
#attach-file, #delete-file {display:none;}
.file-img {width:25px; height:25px;}
.need:focus {outline:none;}
.need {border-radius :8px; padding: 5px; border: 1px solid black;}
.btnSet {padding-left: 20px;}
p {font-family: 'Suez One', serif; font-size: 38px; padding: 0 0 40px 60px; }
#delete-file:hover {cursor: pointer;}


/* 지도 CSS */
#map { height: 250px; width: 100%; padding-top:10px; color:black;  border: 1px solid gray; }
#autocomplete {width:500px; height: 40px; border: 1px solid gray; border-radius :15px; box-shadow: 1px 1px 2px black; padding:0 15px; }
#address {padding-top: 10px ; }


body {background-color: #f56437fa;  padding: 150px 0 250px 0; color: black;  /*  height: 1000px; */font-family: 'Gothic A1', sans-serif;}
#content { width:850px; margin: 0 auto;  padding: 50px 0px;  border-radius:15px; 
 box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
  box-shadow: 0 0 20px 0 rgba(12, 12, 12, 0.65), 0 5px 5px 0 rgba(0, 0, 0, 0.44);  
 background-color : white;} /* 하얀바탕 */
/* form { width: 100%;} */
table { width: 90%; border-collapse:collapse; margin: 0 auto;}
table td { padding : 20px; } 
input { height:22px; /*  padding:3px 5px; */ font-size:15px; width:99%}
#coment {height: 150px;}



</style>


<link rel="stylesheet" href="css/login1.css">
</head>

<body>
<div class="content">
<div style="height: 50px; padding-bottom: 10px; "> <p>PANORAMA</p> </div>
<form method="post" action="insert.pa" enctype="multipart/form-data">
	<table>
		<tr><th>제목</th>
			<td><input type='text' name='b_title' class='need' title='제목'/></td>
		</tr>
		<tr><th>작성자</th><td class="left">${login_info.u_nick}</td>
		</tr>
		<tr id="coment"><th>내용</th>
			<td><textarea name='b_coment' class='need' title='내용' style="width: 100%; height: 200px; resize:none;"></textarea></td>
		</tr>
		
		<tr ><th>위치검색</th>
			<td id="search" style="padding-bottom: 10px;">
				<div style="padding: 0 0 10px 0; float:left;">	
					<input type= "text" id="location" placeholder="지역을 검색하세요" style="width:400px; " class='need' title='위치'>				 
	    			<input type= "hidden" id="b_latitude" name="b_latitude" > 
					<input type= "hidden" id="b_longitude" name="b_longitude" >
					<input type= "hidden" id="b_local" name="b_local" >
					<input type= "hidden" id="b_pano" name="b_pano" value="Y" >
				  	<a class="btn-fill" onclick="where()"> SEARCH </a>
			  	</div>
			  	<!-- <div id="address" style="color:white; height: 20px; padding: 10px;"></div> -->
				<div id="map"></div>
				<div id="address" name="b_local" ></div> <!-- 주소표시 부분 -->
			</td>
		</tr>
		<tr><th>사진</th>
			<td class='td-img left'>
				<label><input type="file" name='file' id='attach-file' class='pic' title='사진'/>
					   <img src='img/icons/select.png' class='file-img'/>
				</label>
				<img id='board_preview' style="width:650px; height:300px; display:none; border: 1px solid gray;" src="#" >  <!-- 파일 미리보기  -->
				<div>
				<span id='delete-file' style="width: 10px; float: right; padding-right: 10px;"><i class="far fa-trash-alt" style=" color:black;"></i></span> <!-- 파일삭제  -->
				<span id='file-name' style="font-size: 80%; float: right; padding-right: 10px;"></span> <!-- 파일이름  -->
				</div>
			</td>
		</tr>	
	</table>		
</form>
<div class='btnSet' style="padding-top:20px; margin: 0px;">
	<a id=login_btn onclick="if( content_check()) $('form').submit()">저장</a>	
	<a id=login_btn href='list.pa'>취소</a>
</div>
</div>
<script type="text/javascript" src="js/need_check.js"></script>


<script type="text/javascript">

/* 
$('#attach-file').on('change', function(){
	if( this.files[0] ) $('#file-name').text(this.files[0].name);
	$('#delete-file').css('display','inline');
}); */

/* $('#delete-file').on('click', function(){
	$('#attach-file').val('');
	$('#file-name').text('');
	$('#delete-file').css('display','none');
});

 */

//게시판 새글 이미지 미리보기
function isImage(filename){
	var ext = filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = ['jpg', 'jpeg', 'gif','png','bmp'];
	if( imgs.indexOf(ext)>-1 )return true;
	else return false;
	
}


function readURL(input) {
	var filename = input.files[0];	
	var _URL = window.URL || window.webkitURL;
	var img = new Image();
	    
    img.src = _URL.createObjectURL(filename);

    if (isImage(filename.name)){  //확장자가 이미지 파일일때 
    	  $('#board_preview').html('');
		  $('#file-name').html('');	
		  $('#delete-file').css('display','none');
		  $('.file-img').css('display','block');
	        img.onload = function() { 	        
			        if(img.width > 3000 && img.height > 1000) {//가로 3000 세로 1000
			            //alert("통과");
			            
			    		if (input.files && input.files[0]) {  
				      		var reader = new FileReader();
					        reader.onload = function(e) {
								  $('#board_preview').attr('src', e.target.result);
						          $('#delete-file').css('display','block');
						          $('.file-img').css('display','none');
								  $('#board_preview').css('display','block');
					    		  $('#file-name').text(filename.name);							
					    		  //document.getElementById("b_pano").value = 'Y';
				 		    }
					      reader.readAsDataURL(input.files[0]);
						
				  	} // if       
			       } else {
			      	  $('#board_preview').html('');
					  $('#file-name').html('');	
					  $('#delete-file').css('display','none');
					  $('.file-img').css('display','block');
					  $('#attach-file').val('');	
			       	  alert("가로 3000 세로 1000 이상의 파노라마 사진이 필요합니다 "); 
					 // $('#file-name').html('*파노라마는 가로 3000 세로 1000 이상의 사진이 필요합니다 ');	
				   }
    		} //img.onload

		

	
	} else {  //확장자가 이미지 파일이 아닐때 
		  alert('첨부파일은 이미지 파일만 가능합니다.')
		  $('#board_preview').html('');
		  $('#file-name').html('');	
		  $('#attach-file').val('');	
		  $('#delete-file').css('display','none');
		  $('.file-img').css('display','block');
	}

		   
}




$("#attach-file").change(function() {

    readURL(this);
});



//삭제처리
$('#delete-file').on('click', function(){
	 $('#board_preview').attr('src', '');
	 $('#board_preview').css('display','none');
	$('.file-img').css('display','inline');
	 $('#delete-file').css('display','none');
	 $('#file-name').html('');	
});




/* 글쓰기 항목 체크 */

function content_check() { 
		
		if ($('[name=b_title]').val().replace(/ /gi, '') == '' ) {
			alert("제목을 입력해주세요 ");
			$('[name=b_title]').focus();
			return;
		}
		

		if ($('[name=b_coment]').val().replace(/ /gi, '') == '') {
			alert("코멘트를 입력해주세요 ");
			$('[name=b_coment]').focus();
			return;
		} 

		if ($('[name=file]').val() == '') {
			alert("사진을 첨부해주세요");		
			return;
		} 

		if ($('[name=b_local]').val() == '') {
			alert("위치를 입력해주세요");
			$('[name=b_local]').focus();
			return;
		} 

		return true;
					
}





	//처음 페이지 실행시 현재 위치 
	window.onload = function() {

		function getLocation() {
			if (navigator.geolocation) { // GPS를 지원하면
				navigator.geolocation.getCurrentPosition(function(position) {
					var lat = parseFloat(position.coords.latitude);
					var lng = parseFloat(position.coords.longitude);
					var address = '현재위치';
					initMap(lat, lng, address);			

					
				}, function(error) {
					console.error(error);
				}, {
					enableHighAccuracy : false,
					maximumAge : 0,
					timeout : Infinity
				});
			} else {
				alert('GPS를 지원하지 않습니다');
			}
		}
		getLocation();

	}


	//맵에 마커 표시하기
	function initMap(lat, lng, address) {

		var now = {
			lat : lat,
			lng : lng
		};
		
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom : 15,
			center : now
		});

		var marker = new google.maps.Marker({
			position : now,
			map : map
		
		});

		
		var content = address;
		var infowindow = new google.maps.InfoWindow({
			content : content
		/*  maxWidth: 200, */

		});	
		


		

		//클릭시 해당 좌표값 얻어옴. 
		google.maps.event.addListener(map, 'click', function(event) {

			var lat = event.latLng.lat();
			var lng = event.latLng.lng();
			//alert(lat +"::" + lng);

		var latLng = {lat:lat, lng:lng};
			
			var geocoder = new google.maps.Geocoder; 
			geocoder.geocode({'location':latLng}, function(results, status){
	 			if(status == "OK"){
	 				var mydata = JSON.parse(JSON.stringify(results));	 				
					var address = mydata[0].formatted_address;
	 				//alert(mydata[0].formatted_address);
					$('#address').html(address); //주소 표시 
					document.getElementById("b_local").value = address;
				//	alert("클릭시 주소 값" + address);
					
					document.getElementById("b_latitude").value = lat;
					document.getElementById("b_longitude").value = lng;
	 	 	 	}
	 	 	 	
			});
			
/* 			document.getElementById("b_local").value = address; //주소값 넘기기
			alert(address);
			
			document.getElementById("b_latitude").value = lat;
			document.getElementById("b_longitude").value = lng;
			 */
			initMap(lat, lng, address); 
			
		});	

	}


		
	



	/* 지도 검색 자동완성 */
	function initAutocomplete() {
		var input = document.getElementById('location');
		var autocomplete = new google.maps.places.Autocomplete(input);	
		
		 google.maps.event.addListener(autocomplete, 'place_changed', function () {	    
	          var place = autocomplete.getPlace();
	          var lat = place.geometry.location.lat();
	          var lng = place.geometry.location.lng();
	          var address = place.formatted_address; /* 주소 */
	         // var name = place.name; /* 건물이름  */
	          
	       //   alert(address+"ddd"+name);
	         $('#address').html(address); //주소표시 
			 initMap(lat, lng, address); 

			 document.getElementById("b_local").value = address; //주소값 넘기기
			// alert(address);
			 document.getElementById("b_latitude").value = lat;
			 document.getElementById("b_longitude").value = lng;
		
	 	  });

	}

	google.maps.event.addDomListener(window, 'load', initAutocomplete);





	// 검색 직접 작성 
	function where() {

		var location = $('#location').val().replace(/ /gi, '');

		$.ajax({
			url : 'search_map',
			data : {
				address : location
			},
			success : function(data) {
				if (data) {

					//alert(data.lng+"+"+data.lat+"+"+data.address); // 지도 위도 경도 주소 값
					$('#address').html(data.address);
		 			document.getElementById("b_local").value = data.address; //주소값 넘겨주기
					document.getElementById("b_latitude").value = data.lat;
					document.getElementById("b_longitude").value = data.lng;
 
					var lat = data.lat;
					var lng = data.lng;
					var address = data.address;
					$('#address').html(address); //주소표시 
					initMap(lat, lng, address);
				
				} else {

					alert('정확한 주소를 입력해주세요');
					return;

				}

			},
			error : function(req, text) {
				alert(text + ':' + req.status);
			}

		});
	} 
	
	 

	//스크롤해서 내려서 로딩된 이미지 끝에 다달았을때 다음페이지의 이미지 출력하는 스크립트
	window.onscroll = function() {
		//		console.log('js 흐름탐');

		var scroll = window.scrollY + $(window).height();
		var endY = document.body.scrollHeight;

		console.log(scroll);
		console.log(endY);

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