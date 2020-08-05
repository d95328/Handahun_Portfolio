package web.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebBoardServiceImpl implements WebBoardService {

	@Autowired private WebBoardDAO dao;
	
	@Override
	public int board_insert(WebBoardVO vo) {
		// TODO Auto-generated method stub
		return dao.board_insert(vo);
	}

	@Override
	public WebBoardPage board_list(WebBoardPage page) {
		return dao.board_list(page);
	}

	@Override
	public WebBoardVO board_detail(int no, String userid) {
		return dao.board_detail(no, userid);
	}

	@Override
	public int board_update(WebBoardVO vo) {
		return dao.board_update(vo);
	}

	@Override
	public int board_delete(int id) {
		return dao.board_delete(id);
	}

	@Override
	public void boardDdabong(WebFavoriteVO fvo) {
		dao.boardDdabong(fvo);
	}

	@Override
	public void boardDdabongUpdate(WebFavoriteVO fvo) {
		dao.boardDdabongUpdate(fvo);
	}

}
