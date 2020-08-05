<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
@import url('https://fonts.googleapis.com/css2?family=Dosis:wght@300&display=swap');
body{ margin: 0 auto; }
#footer-wrap {
	position: fixed;
	left: 0;
	bottom: 0;
	width: 100%;
	height: 180px;
	background-color: #151515;
	margin-top: 30px;
	padding: 0;
	font-family: 'Dosis', sans-serif;
}
.footer-menu-box {
	max-width: 500px;
	width: 100%;
	margin: 0 auto;
	height: 150px;
}
.footer-menu li{
	float: left;
	padding: 0 10px;
	color: #b5b1b1 !important;
}
.footer-menu li a{
	color: #b5b1b1 !important;
}
.footer-menu li a:hover { color: #fc7703; cursor: pointer; }
.footer-menu1 { width: 100%; height: 30px; }
.footer-menu2 { width: 100%; height: 50px; }
.copyright { font-size: 12px !important; text-align: left !important; padding-left: 10px !important; color: #b5b1b1 !important; }
</style>
<footer id="footer-wrap">
		<div class="footer-menu-box">
			<div class="footer-menu1">
				<ul class="footer-menu">
					<li><a href="list.bo">POST</a></li>
					<li style="font-weight: bold;">|</li>
					<li><a href="list.pa">PANORAMA</a></li>
					<li style="font-weight: bold;">|</li>
					<li><a href="list.qn" >QnA</a></li>
					<li style="font-weight: bold;">|</li>
					<li><a href="list.map" >MAP</a></li>
					<li style="font-weight: bold;">|</li>
					<li><a href="room" >CHAT</a></li>
				</ul>
			</div>
			<div class="footer-menu2">
				<ul class="footer-menu">
					<li style="font-weight: bold;">Team  Moment  Developers</li>
				</ul><br>
				<ul class="footer-menu">
					<li>Chihyeon GOD</li>
					<li>Minsaem Jung</li>
					<li>Donghui Im</li>
					<li>Dahun Han</li>
				</ul><br>
				<ul>
					<li class="copyright">COPYRIGHT ⓒ 2020 HANUL VOCATIONAL TRAINING INSTITUTE.ALL RIGHT RESERVED</li>
				</ul>
			</div>
		</div>
</footer>
