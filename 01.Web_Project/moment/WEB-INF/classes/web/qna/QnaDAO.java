package web.qna;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QnaDAO implements QnaService {
	@Autowired @Qualifier("moment") private SqlSession sql;
	
	@Override
	public void question_insert(QnaVO vo) {
		sql.insert("qna.mapper.q_insert", vo);
	}

	@Override
	public void answer_insert(QnaVO vo) {
		sql.update("qna.mapper.a_update", vo);
	}

	@Override
	public QnaPage faq_list(QnaPage page) {
		page.setTotalList( (Integer)sql.selectOne("qna.mapper.total_faq", page) );
		page.setList( sql.selectList("qna.mapper.faqlist", page) );
		return page;
	}

	@Override
	public QnaPage question_list(QnaPage page) {
		page.setTotalList( (Integer)sql.selectOne("qna.mapper.total_q", page) );
		page.setList( sql.selectList("qna.mapper.question", page) );
		return page;
	}

	@Override
	public QnaPage my_question_list(QnaPage page) {
		page.setTotalList( (Integer)sql.selectOne("qna.mapper.total_my", page) );
		page.setList( sql.selectList("qna.mapper.mylist", page) );
		return page;
	}
	@Override
	public QnaPage answer_list(QnaPage page) {
		page.setTotalList( (Integer)sql.selectOne("qna.mapper.total_a", page) );
		page.setList( sql.selectList("qna.mapper.answer", page) );
		return page;
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
		sql.delete("qna.mapper.delete",id);

	}

	@Override
	public QnaPage all_list(QnaPage page) {
		page.setTotalList( (Integer)sql.selectOne("qna.mapper.total_all", page) );
		page.setList( sql.selectList("qna.mapper.all", page) );
		return page;
	}

	

}
