package web.board;

import android.board.FavoriteVO;

public interface WebBoardService {
	
		//CRUD
		int board_insert( WebBoardVO vo );					//게시글 글 저장
		
		WebBoardPage board_list( WebBoardPage page );		//게시글 페이지목록 조회
		
		WebBoardVO board_detail( int no , String userid);	//게시글 상세조회
		
		int board_update( WebBoardVO vo );					//게시글 수정
		
		int board_delete( int id );							//게시글 삭제
		
		//추천이벤트처리
		void boardDdabong(WebFavoriteVO fvo);
		void boardDdabongUpdate(WebFavoriteVO fvo);
		
		
}
