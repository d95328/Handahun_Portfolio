package android.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired BoardDAO dao;
	
	@Override
	public JSONArray latelyList() {
		return dao.latelyList();
	}

	@Override
	public JSONArray popularList() {
		return dao.popularList();
	}

	@Override
	public JSONArray myBoardList(String userid) {
		return dao.myBoardList(userid);
	}

	@Override
	public JSONObject boardWrite(HttpServletRequest request, HttpServletResponse response) {
		return dao.boardWrite(request, response);
	}

	@Override
	public JSONObject boardModify(Map<String, Object> data) {
		return dao.boardModify(data);
	}

	@Override
	public int boardDelete(int no) {
		return dao.boardDelete(no);
	}

	@Override
	public JSONObject boardDetail(int no, String userid) {
		return dao.boardDetail(no, userid);
	}

	@Override
	public void boardDdabong(FavoriteVO vo) {
		dao.boardDdabong(vo);
	}

	@Override
	public void boardDdabongUpdate(FavoriteVO vo) {
		dao.boardDdabongUpdate(vo);
	}

	@Override
	public JSONArray myFavoritesList(String userid) {
		return dao.myFavoritesList(userid);
	}

	@Override
	public JSONArray markerNearby(double latitude, double longitude) {
		return dao.markerNearby(latitude, longitude);
	}

	@Override
	public JSONArray searchList(String choice, String search) {
		return dao.searchList(choice, search);
	}
	
	
}
