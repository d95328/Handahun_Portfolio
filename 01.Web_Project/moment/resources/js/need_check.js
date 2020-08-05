/**
 * 공지글/방명록 작성시 제목, 내용 필수입력항목 체크
 * 
 */

function necessary(){
	var need = true;
	$('.need').each(function(){
		if($(this).val() == '' ){
			alert( $(this).attr('title') + ' 입력하세요! ');
			$(this).focus();
			need = false;
			return need;
		}	 	
		
	});	
	
	return need;
	
}