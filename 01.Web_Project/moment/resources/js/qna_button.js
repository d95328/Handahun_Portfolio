/**
 * 	QnA 버튼 
 */
	$('#all_list').on('click', function(){
		$('#all_list').removeClass();
		$('#faq').removeClass();
		$('#myq').removeClass();
		$('#question').removeClass();
		$('#qlist').removeClass();
		$('#alist').removeClass();
		$('#all_list').addClass('qna-btn-fill');
		$('#faq').addClass('qna-btn-empty');
		$('#myq').addClass('qna-btn-empty');
		$('#question').addClass('qna-btn-empty');
		$('#qlist').addClass('qna-btn-empty');
		$('#alist').addClass('qna-btn-empty');
		$('[name=keyword]').val('');
	});
	
	$('#faq').on('click', function(){
		$('#all_list').removeClass();
		$('#faq').removeClass();
		$('#myq').removeClass();
		$('#question').removeClass();
		$('#qlist').removeClass();
		$('#alist').removeClass();
		$('#faq').addClass('qna-btn-fill');
		$('#all_list').addClass('qna-btn-empty');
		$('#myq').addClass('qna-btn-empty');
		$('#question').addClass('qna-btn-empty');
		$('#qlist').addClass('qna-btn-empty');
		$('#alist').addClass('qna-btn-empty');
	});

	$('#myq').on('click', function(){
		$('#myq').removeClass();
		$('#faq').removeClass();
		$('#question').removeClass();
		$('#qlist').removeClass();
		$('#alist').removeClass();
		$('#all_list').removeClass();
		$('#all_list').addClass('qna-btn-empty');
		$('#myq').addClass('qna-btn-fill');
		$('#faq').addClass('qna-btn-empty');
		$('#question').addClass('qna-btn-empty');
		$('#qlist').addClass('qna-btn-empty');
		$('#alist').addClass('qna-btn-empty');
	});
	
	$('#question').on('click', function(){
		$('#question').removeClass();
		$('#faq').removeClass();
		$('#myq').removeClass();
		$('#qlist').removeClass();
		$('#alist').removeClass();
		$('#all_list').removeClass();
		$('#all_list').addClass('qna-btn-empty');
		$('#question').addClass('qna-btn-fill');
		$('#faq').addClass('qna-btn-empty');
		$('#myq').addClass('qna-btn-empty');
		$('#qlist').addClass('qna-btn-empty');
		$('#alist').addClass('qna-btn-empty');
	});
	
	$('#qlist').on('click', function(){
		$('#qlist').removeClass();
		$('#faq').removeClass();
		$('#myq').removeClass();
		$('#qlist').removeClass();
		$('#alist').removeClass();
		$('#all_list').removeClass();
		$('#all_list').addClass('qna-btn-empty');
		$('#qlist').addClass('qna-btn-fill');
		$('#faq').addClass('qna-btn-empty');
		$('#myq').addClass('qna-btn-empty');
		$('#question').addClass('qna-btn-empty');
		$('#alist').addClass('qna-btn-empty');
	});
	
	$('#alist').on('click', function(){
		$('#alist').removeClass();
		$('#faq').removeClass();
		$('#myq').removeClass();
		$('#qlist').removeClass();
		$('#alist').removeClass();
		$('#all_list').removeClass();
		$('#all_list').addClass('qna-btn-empty');
		$('#alist').addClass('qna-btn-fill');
		$('#faq').addClass('qna-btn-empty');
		$('#myq').addClass('qna-btn-empty');
		$('#question').addClass('qna-btn-empty');
		$('#qlist').addClass('qna-btn-empty');
	});