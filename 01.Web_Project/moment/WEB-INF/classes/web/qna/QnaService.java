package web.qna;


public interface QnaService {
	//CRUD
	void question_insert(QnaVO vo); 			//질문하기
	void answer_insert(QnaVO vo);				//답변하기
	QnaPage all_list(QnaPage page);				//자주하는 질문 리스트
	QnaPage faq_list(QnaPage page);				//자주하는 질문 리스트
	QnaPage question_list(QnaPage page);		//질문 리스트
	QnaPage answer_list(QnaPage page);			//답변 리스트
	QnaPage my_question_list(QnaPage page);		//내 질문 리스트
	QnaVO qna_detail(int id);					//질문 상세 조회
	void question_update(QnaVO vo);			//질문 수정
	void question_delete(int id);				//질문 삭제
}
