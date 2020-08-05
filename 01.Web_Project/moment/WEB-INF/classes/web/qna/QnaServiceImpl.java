package web.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnaServiceImpl implements QnaService {
	@Autowired private QnaDAO dao;
	
	@Override
	public void question_insert(QnaVO vo) {
		dao.question_insert(vo);
	}

	@Override
	public void answer_insert(QnaVO vo) {
		dao.answer_insert(vo);
	}

	@Override
	public QnaPage faq_list(QnaPage page) {
		return dao.faq_list(page);
	}

	@Override
	public QnaPage question_list(QnaPage page) {
		return dao.question_list(page);
	}

	@Override
	public QnaPage my_question_list(QnaPage page) {
		return dao.my_question_list(page);
	}

	@Override
	public QnaVO qna_detail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void question_update(QnaVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void question_delete(int id) {
		dao.question_delete(id);

	}

	@Override
	public QnaPage all_list(QnaPage page) {
		return dao.all_list(page);
	}

	@Override
	public QnaPage answer_list(QnaPage page) {
		return dao.answer_list(page);
	}

}
