/**
 * 
 */
var imgCnt = $(".slider_panel").children().length;
var imgIdx = 1;
var refreshInterval = null;
var timer = null;
var _check = true;

function moveSlider(index) {
	if(_check == true){
		var willMoveLeft = -(index * 1024);
		$(".slider_panel").animate({left: willMoveLeft}, "slow");
		$('.control_button[data-index='+index+']').addClass('active');
		$('.control_button[data-index!='+index+']').removeClass('active');
		$(".slider_text[data-index=" + index + "]").show()
		.animate({left: 0}, "slow");
		
		$(".slider_text[data-index!=" + index + "]").hide("slow", function () {
			$(this).css("left", -300);
		});
	
	}else if(_check == false) {
		var willMoveLeft = (index * 1024)-4096;
		$(".slider_panel").animate({left: willMoveLeft}, "slow");
		$('.control_button[data-index='+(4-index)+']').addClass('active');
		$('.control_button[data-index!='+(4-index)+']').removeClass('active');
		$(".slider_text[data-index=" + (4-index) + "]").show()
		.animate({left: 0}, "slow");
		
		$(".slider_text[data-index!=" + (4-index) + "]").hide("slow", function () {
			$(this).css("left", -300);
		});
	}
}

function moveSliderClick(index) {
	var willMoveLeft = -(index * 1024);
	$(".slider_panel").animate({left: willMoveLeft}, "slow");
	$('.control_button[data-index='+index+']').addClass('active');
	$('.control_button[data-index!='+index+']').removeClass('active');
	$(".slider_text[data-index=" + index + "]").show()
	.animate({left: 0}, "slow");
	
	$(".slider_text[data-index!=" + index + "]").hide("slow", function () {
		$(this).css("left", -300);
	});
}

timer = function () {
	moveSlider(imgIdx);
	
	if(imgIdx < imgCnt - 1 ){
		imgIdx++;
	} else if( _check == true ) {
		_check = false;
		imgIdx = 1;
	} else if( _check == false ) {
		_check = true;
		imgIdx = 1;
	}
}

$(".animation_canvas").on({mouseenter: function() {
		clearInterval(refreshInterval);
	},
	mouseleave: function () {
		refreshInterval = setInterval(timer, 5000);
	}
});



$(".control_button").each(function(index) {
	$(this).attr("data-index", index);
}).click(function () {
	var index = $(this).attr('data-index');
	if( _check == true ){imgIdx = index;}
	if( _check == false ){imgIdx = 4-index;}
	moveSliderClick(index);
});

$(".slider_text").css("left", -300).each(function(index) {
	$(this).attr("data-index", index); // data-index 0 ~ index 넘버링
});
moveSlider(0);
refreshInterval = setInterval(timer, 5000);
	
