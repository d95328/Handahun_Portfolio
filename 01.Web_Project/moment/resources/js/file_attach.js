/**
 * 첨부파일 선택/ 삭제
 */
$('#u_profileimg').on('change', function(){
	if( this.files[0] ){
		$('#file-name').text( this.files[0].name );
		$('#delete-file').css('display', 'inline');
	}
});
$('#delete-file').on('click', function(){
	$('#u_profileimg').val('');
	$('#file-name').text('');
	$('#delete-file').css('display', 'none');
});