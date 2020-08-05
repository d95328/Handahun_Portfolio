package android.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import android.board.BoardServiceImpl;
import android.board.BoardVO;
import android.board.FavoriteVO;

@Controller
public class BoardController {
	
	@Autowired private BoardServiceImpl service;
	@Autowired private BoardVO vo;
	
	//최신글 목록조회
	@ResponseBody @RequestMapping (value = "/LatelyListAction.mo")
	public JSONArray LatelyList() {
		System.out.println("Controller ============= > /LatelyList.mo");
		
		JSONArray datas = service.latelyList();
		return datas;
	}
	
	//인기글 목록조회
	@ResponseBody @RequestMapping(value = "/PopularListAction.mo", produces = "application/json; charset=UTF-8")
	public JSONArray PopularList() {
		System.out.println("Controller ============= > /PopularList.mo");
		
		JSONArray datas = service.popularList();
		return datas;
	}
	
	//내가 작성한 글 목록조회
	@ResponseBody @RequestMapping(value = "/MyBoardListAction.mo", produces = "application/json; charset=UTF-8")
	public JSONArray MyBoardList(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /MyBoardListAction.mo");
		
		String userid = (String) param.get("b_userid");
		JSONArray datas = service.myBoardList(userid);
		return datas;
		
	}
	
	//신규글 작성
	@ResponseBody @RequestMapping(value = "/BoardWriteAction.mo", produces = "application/json; charset=UTF-8")
	public String BoardWrite(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Controller ============= > /BoardWriteAction.mo");
		
		JSONObject data = service.boardWrite(request, response);
		return "1";
	}	
	
	//게시글 수정
	@ResponseBody @RequestMapping(value = "/BoardModifyAction.mo", produces = "application/json; charset=UTF-8")
	public JSONObject BoardModify(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /BoardModifyAction.mo");
		
		JSONObject result = service.boardModify(param);
		return result;
	}
	
	//게시글 삭제
	@ResponseBody @RequestMapping(value = "/BoardDeleteAction.mo", produces = "application/json; charset=UTF-8")
	public JSONObject BoardDelete(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /BoardDeleteAction.mo");
		
		int no = Integer.parseInt(param.get("b_no").toString());
		int result = service.boardDelete(no);
		JSONObject data = new JSONObject();
		
		if( result > 0 ) {
			//삭제 성공시
			data.put("result", "success");
		} else {
			data.put("result", "fail");
		}
		
		System.out.println("================ Board Delete Result ================");
		System.out.println(data);
		return data;
	}
	
	//글 상세조회 / 조회수 증가
	@ResponseBody @RequestMapping(value = "/BoardDetailAction.mo", produces = "application/json; charset=UTF-8")
	public JSONObject BoardDetail(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /BoardDetailAction.mo");
		
		int no = Integer.parseInt(param.get("b_no").toString());
		String userid = param.get("b_userid").toString();
		JSONObject data = service.boardDetail(no, userid);
		return data;
	}
	
	//추천 & 즐겨찾기 이벤트
	@ResponseBody @RequestMapping(value = "/FavoriteAction.mo", produces = "application/json; charset=UTF-8")
	public void Favorite(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /FavoriteAction.mo");
		
		FavoriteVO vo = new FavoriteVO();
		vo.setF_userid(param.get("u_userid").toString());
		vo.setF_bno(Integer.parseInt(param.get("b_no").toString()));
		vo.setF_ddabong(param.get("f_ddabong").toString());
		vo.setF_favorites(param.get("f_favorites").toString());
		
		service.boardDdabong(vo);
		service.boardDdabongUpdate(vo);
	}
	
	//내 즐겨찾기 목록조회
	@ResponseBody @RequestMapping(value = "/MyFavoritesListAction.mo", produces = "application/json; charset=UTF-8")
	public JSONArray MyFavorite(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /MyFavoritesListAction.mo");
		
		String userid = param.get("b_userid").toString();
		JSONArray datas = service.myFavoritesList(userid);
		return datas;
	}
	
	//내 주변 마커 좌표 목록 요청
	@ResponseBody @RequestMapping(value = "/getMarkerNearbyAction.mo", produces = "application/json; charset=UTF-8")
	public JSONArray getMarkerNearb(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /getMarkerNearbyAction.mo");
		 
		double latitude = Double.parseDouble(param.get("b_latitude").toString());
		double longitude = Double.parseDouble(param.get("b_longitude").toString());
		JSONArray datas = service.markerNearby(latitude, longitude); 
		return datas;
	}
	
	//검색 결과 목록 요청
	@ResponseBody @RequestMapping(value = "/SearchBoardListAction.mo", produces = "application/json; charset=UTF-8")
	public JSONArray SearchBoardList(@RequestBody HashMap<String, Object> param) {
		System.out.println("Controller ============= > /SearchBoardListAction.mo");
		String choice = param.get("choice").toString();
		String search = param.get("b_search").toString();
		
		JSONArray datas = service.searchList(choice, search);
		return datas;
	}
	
}
