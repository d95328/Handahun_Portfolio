package android.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface BoardService {
	
	//최신글 목록 조회 
	JSONArray latelyList();					
	//인기글 목록 조회
	JSONArray popularList();					
	//내가 작성한 글 목록 조회
	JSONArray myBoardList(String userid);		
	//신규글 작성
	JSONObject boardWrite(HttpServletRequest request, HttpServletResponse response);
	//게시글 수정
	JSONObject boardModify(Map<String, Object> data);						
	//게시글 삭제
	int boardDelete(int no);							
	//글 상세 조회
	JSONObject boardDetail(int no, String userid);
	
	//조회시 발생하는 이벤트는 쿼리로 처리 (조회수증가, 추천여부조회, 즐겨찾기여부조회) 
	//글 조회수 증가
//	void boardReadCnt(int no);							
//	//즐겨찾기 테이블 데이터 조회 (게시글 디테일 확인 시 로그인한 사용자가 좋아요누른 이력있는지 조회)
//	FavoriteVO boardFavorite(int no, String userid);
//	//즐겨찾기 업데이트 (즐겨찾기테이블조회 후 일치하는 데이터가 없으면 -> 데이터추가)
//	void boardAddFavorite(int no, String userid);		
	
	//추천조회
	void boardDdabong(FavoriteVO vo);
	//추천 업데이트(변경이벤트처리)
	void boardDdabongUpdate(FavoriteVO vo);			
	//내 즐겨찾기 목록 조회
	JSONArray myFavoritesList(String userid);	
	//좌표 및 게시글 정보 불러오기
	JSONArray markerNearby(double latitude, double longitude); 
	//검색 글 조회
	JSONArray searchList(String choice, String search);		
	
	
}
