package web.board;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import android.board.FavoriteVO;

@Repository
public class WebBoardDAO implements WebBoardService {

	@Autowired private SqlSession sql; 
	
	@Override
	public int board_insert(WebBoardVO vo) {
		// TODO Auto-generated method stub
		return sql.insert("webBoard.mapper.insert", vo);
	}

	@Override
	public WebBoardPage board_list(WebBoardPage page) {
		page.setTotalList( sql.selectOne("webBoard.mapper.total", page) );
		page.setList( sql.selectList("webBoard.mapper.list", page));
		return page;
	}

	@Override
	public WebBoardVO board_detail(int no, String userid) {
		//마지막에 리턴시켜줄 최종 vo 객체 생성
		WebBoardVO bVo = new WebBoardVO();
		//추천,즐찾데이터 조회항 fvo 객체 생성
		WebFavoriteVO fVo = null;
		
		//조회수 증가 (조회했으면 무조건 증가)
		sql.update("webBoard.mapper.readCnt", no);
		//일단 bVo에 조회한 글에 대한 정보 담아줌
		bVo = sql.selectOne("webBoard.mapper.detail", no);
		
		//로그인한 사용자가 조회했을경우
		if( !userid.equals("bLogin")) {
			//즐찾여부 데이터 담아줄 vo 객체 새로 생성
			WebBoardVO bfVo = new WebBoardVO();
			bfVo.setB_no(no);
			bfVo.setB_userid(userid);
			fVo = sql.selectOne("webBoard.mapper.boardFavorite", bfVo);
			if(fVo == null) {
				//사용자가 해당글을 처음 조회했을 경우에 favoirte 테이블에 이력 추가(default값 N)
				sql.insert("webBoard.mapper.boardAddFavorite", bfVo);
				//추가된 후 다시 조회해옴
				fVo = sql.selectOne("webBoard.mapper.boardFavorite", bfVo);
			}
		} else {
			//비로그인 사용자가 조회했을 경우 -> 추천즐찾 데이터 N으로 초기화해줌
			fVo = new WebFavoriteVO();
			fVo.setF_favorites("N");
			fVo.setF_ddabong("N");
		}
		
		//마지막으로 위에처리되어 fVo에 담긴 즐찾 데이터를 리턴해줄 bVo 객체에 담아줌!!!
		bVo.setF_ddabong(fVo.getF_ddabong());
		bVo.setF_favorites(fVo.getF_favorites());
		return bVo;
	}

	@Override
	public int board_update(WebBoardVO vo) {
		return sql.update("webBoard.mapper.boardUpdate", vo);
	}

	@Override
	public int board_delete(int id) {
		return sql.delete("webBoard.mapper.boardDelete", id);
	}

	@Override
	public void boardDdabong(WebFavoriteVO fvo) {
		sql.update("webBoard.mapper.boardDdabong", fvo);
	}

	@Override
	public void boardDdabongUpdate(WebFavoriteVO fvo) {
		sql.update("board.mapper.boardDdabongUpdate", fvo);
	}


}
