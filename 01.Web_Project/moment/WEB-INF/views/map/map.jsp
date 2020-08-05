<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>        
<!DOCTYPE html>
<html>
 <head>
    <title>Place Searches</title>
<!-- 구글 api  -->
<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyDrfll4QoaTNLPA3Zhpd0P_72bmSVjqNYk"></script> -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDrfll4QoaTNLPA3Zhpd0P_72bmSVjqNYk&libraries=places&callback=initAutocomplete" 
async defer></script>

<style>
body {background-color:#f56437fa; font-family: 'Gothic A1', sans-serif !important;}

#locationField { width:100%; padding: 60px 0 0 0;  /* float: left; */}
#autocomplete {width:500px; height: 40px; border: 1px solid black; border-radius :15px; 
box-shadow: 1px 1px 2px black; padding:0 15px; margin-bottom: 10px;}
p {/* float: left; */ margin: 10px; padding: 0px; font-family: 'Suez One', serif; font-size: 38px;/*  padding-left: 450px; */} 
input:focus {outline:none;}
#map_ma {margin: 0 auto;  width:1250px; height:700px; clear:both; border:solid 2px black; box-shadow: 1px 1px 2px black;}

/* footer */
#footer-wrap { display: none; }
.push { display: none; }


/* 지도 css */
#infoimg {width:119px; height:99px; float:left; } 	
#infocon {width :115px;  margin: 0 ;}    
#infotitle { width :115px;
text-overflow: ellipsis; white-space: nowrap;  
display: block; overflow: hidden; font-size:90%; /* 글줄임 */
font-weight: bolder;  text-align: center;} 

div.gm-style-iw.gm-style-iw-c {
	width: 150px !important; 
	height: 155px;
}


div.gm-style-iw-d {
	width: 99%; 
	height: 99%;
}

</style>
</head>
<body>


<div style="padding-top:10px; height: 1000px; background-color: #f56437fa;">
   <!-- 주소검색  -->
   <div id="locationField">
	   	  <P style="color:black;"> Search by Map.. </P> 
		  <input id="autocomplete" placeholder="지역을 검색하세요" type="text">
   </div>
	 
	<input type="hidden" class="field" id="lat" />
	<input type="hidden" class="field"  id="lng" />	
	
	<!-- 지도  -->
	<div id="map_ma" style="color:black;"></div> 
</div>

<!-- 디테일 js -->
<script type="text/javascript" src="js/detail.js"></script>


<script type="text/javascript">

//처음 페이지 실행시 현재 위치 
window.onload = function() {

	function getLocation() {
		  if (navigator.geolocation) { // GPS를 지원하면
		    navigator.geolocation.getCurrentPosition(function(position) {		   
				var lat = parseFloat(position.coords.latitude);
				var lng = parseFloat(position.coords.longitude);
				initMap(lat, lng);
				console.log("coords.lat: "+position.coords.latitude);		   
				console.log("coords.lng: "+position.coords.longitude);		   
				
			}, function(error) {
		      console.error(error);
		    }, {
		      enableHighAccuracy: false,
		      maximumAge: 0,
		      timeout: Infinity
		    });
		  } else {
		    alert('GPS를 지원하지 않습니다');
		  }
		}
		getLocation();

}


// 마커 표시 
function initMap(lat,lng){
 
	//alert("함수"+lat + ' ' +lng);
	console.log("lat: "+lat);
	console.log("lng: "+lng);		 
	var map = new google.maps.Map(document.getElementById('map_ma'), {
	    
	    center: {
	        lat: lat,
	        lng: lng
	    },
	    
	    //처음 줌 값. 숫자가 작을수록 낮은 배율
	    zoom: 15
	});

	var marker = new google.maps.Marker({
		position: {lat:lat, lng: lng},
		map: map
		
	});
	
}


// 지도 검색시, 자동완성 및 DB에서 주변 정보 가져오기 
var placeSearch, autocomplete;

function initAutocomplete() {
	
  // Create the autocomplete object, restricting the search to geographical 
  autocomplete = new google.maps.places.Autocomplete(
    (document.getElementById('autocomplete')), {
      types: ['geocode']
    
    });

  autocomplete.addListener('place_changed', fillInAddress); //주소 선택시 검색창 채움
}




function fillInAddress() {  //좌표 얻어와서 창에 띄움. 
  
  var place = autocomplete.getPlace();
  document.getElementById("lat").value = place.geometry.location.lat();
  document.getElementById("lng").value = place.geometry.location.lng();

  var lat = parseFloat(document.getElementById("lat").value);
  var lng = parseFloat(document.getElementById("lng").value);
  var locationname = document.getElementById("autocomplete").value;	
  //var marker, i;
  
	$.ajax({
		type : 'post',
		url : 'nearMarker.moment', 
		data : {
			b_latitude : lat,
			b_longitude : lng
		},
		success : function(data) {
		 //alert('주변 정보 전달 성공');
		 00
			var map = new google.maps.Map(document.getElementById('map_ma'), {        	    
        	    center: {
        	        lat: lat,
        	        lng: lng
        	    },        	    
        	   zoom: 17
        	});        		
         
	            for (var i = 0; i < data.length; i++) {	            
	               var marker = new google.maps.Marker({
	                    position: {lat: parseFloat( data[i].b_latitude ), lng: parseFloat(data[i].b_longitude)},
	                    map: map,	                    
	                });	                

	              
	                var img = "<img id='infoimg' onclick='detail("+ data[i].b_no +", \'${login_info.u_userid}\' )' src='img/background/"+data[i].b_imgpath+"'>"
	                +"<div id='infocon'><div id='infotitle'>"+data[i].b_title+"</div><div><img style='width:8%; padding:1px' src='img/background/heart.png'>"
	                +data[i].b_ddabong+"&nbsp;&nbsp;&nbsp;<img style='width:10%; padding-bottom:1px' src='img/background/eyeb.png'>"
	                +data[i].b_readcnt+"</div></div>"; 
	              	// 클릭시 글 번호로 글 상세 화면으로 넘어가게 함            	
	              	                        
	                
		            var infowindow = new google.maps.InfoWindow({
		                content: img,
		               /*  maxWidth: 200, */
		                
		              });

		            infowindow.open(map, marker);  //제일 처음에 info창 띄워주기 
		            bindInfoWindow(marker, map, infowindow); //클릭시 info창 띄워주기    
		        
	            } // for문


		 function bindInfoWindow(marker, map, infowindow ) { 
	 	     google.maps.event.addListener(marker, 'click', function() {
		     // infowindow.setContent(html); 
		    infowindow.open(map, marker); 
		         }); 
		
	     }//bindInfoWindow
	                    
	}, //success 
		error : function(req, text) {
			alert(text + ":" + req.status);

		}
	}); //ajax  
}

</script>
</body>
</html>

