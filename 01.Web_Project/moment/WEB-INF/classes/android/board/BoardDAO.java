package android.board;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import android.common.Common;
import android.utill.AndroidFileUpload;

@Repository
public class BoardDAO implements BoardService {

	@Autowired private SqlSession sql;
	
	@Override
	public JSONArray latelyList() {
		List<BoardVO> list = sql.selectList("board.mapper.latelyList");
		System.out.println("================ Board Lately List Data ================");
		JSONArray datas = Common.boardListParser(list);
		return datas;
	}

	@Override
	public JSONArray popularList() {
		List<BoardVO> list = sql.selectList("board.mapper.poularList");
		System.out.println("================ Board Popular List Data ================");
		JSONArray datas = Common.boardListParser(list);
		return datas;
	}

	@Override
	public JSONArray myBoardList(String userid) {
		List<BoardVO> list = sql.selectList("board.mapper.myBoardList", userid);
		System.out.println("================ Board MyBoard List Data ================");
		JSONArray datas = Common.boardListParser(list);
		return datas; 
	}

	@Override
	public JSONObject boardWrite(HttpServletRequest request, HttpServletResponse response) {
		final String CHARACTER_SET = "UTF-8";
		BoardVO dto = new BoardVO();
		JSONObject data = new JSONObject();
		
		try {
			Map<String, String> fileUploadParamsMap = AndroidFileUpload.upload(request, response, Common.IMAGE_FILE_SAVE_PATH);
			
			//디코딩 --- 한글 깨져서 들어오는거 변환
			dto.setB_title(URLDecoder.decode(fileUploadParamsMap.get("b_title"),CHARACTER_SET));
			dto.setB_local(URLDecoder.decode(fileUploadParamsMap.get("b_local"),CHARACTER_SET));
			dto.setB_coment(URLDecoder.decode(fileUploadParamsMap.get("b_coment"),CHARACTER_SET));
			dto.setB_imgpath(URLDecoder.decode(fileUploadParamsMap.get("b_imgpath"),CHARACTER_SET));
			dto.setB_userid(URLDecoder.decode(fileUploadParamsMap.get("b_userid"),CHARACTER_SET));
			dto.setB_tag(URLDecoder.decode(fileUploadParamsMap.get("b_tag"),CHARACTER_SET));
			dto.setB_latitude(Double.valueOf(fileUploadParamsMap.get("b_latitude")));
			dto.setB_longitude(Double.valueOf(fileUploadParamsMap.get("b_longitude")));
			
			System.out.println(dto.toString());
			int result = 0;
			
			System.out.println("DAO-DTO >>>" + dto.toString());
			result = sql.insert("board.mapper.boardWrite", dto);
			
			if(result > 0) {
				System.out.println("글 작성 성공");
				data.put("createResult", result);
			}else {
				System.out.println("글 작성 실패");
				data.put("createResult", result);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
		};
		System.out.println("================ Board Write Result ================");
		System.out.println(data.toString());
		return data;
	}

	@Override
	public JSONObject boardModify(Map<String, Object> data) {
		BoardVO vo = new BoardVO();
		vo.setB_no(Integer.parseInt(data.get("b_no").toString()));
		vo.setB_title(data.get("b_title").toString());
		vo.setB_coment(data.get("b_coment").toString());
		vo.setB_local(data.get("b_local").toString());
		vo.setB_latitude(Double.valueOf(data.get("b_latitude").toString()));
		vo.setB_longitude(Double.valueOf(data.get("b_longitude").toString()));
		
		int result = 0;
		result = sql.update("board.mapper.modify", vo);
		
		JSONObject jsonObject = new JSONObject();
		
		if( result > 0 ) {
			jsonObject.put("result", "success");

		} else {
			jsonObject.put("result", "fail");
		}
		System.out.println("================ Board Modify Result ================");
		System.out.println(jsonObject);
		return jsonObject;
	}

	@Override
	public int boardDelete(int no) {
		int result = sql.delete("board.mapper.delete", no);
		return result;
	}

	@Override
	public JSONObject boardDetail(int no, String userid) {
		BoardVO bVo = new BoardVO();
		FavoriteVO fVo = null;
		JSONObject data = null;
		
		//일단 로그인했든 안했든 읽었으니 조회수 증가
		sql.update("board.mapper.readCnt", no);
		//사용자가 조회한 글에대한 정보 조회
		bVo = sql.selectOne("board.mapper.detail", no);
		
		//조회한 글 정보 json형식으로 반환 위한 jo 객체 생성
		data = new JSONObject();
		
		//근데 로그인한 사용자면 그 사용자의 계정이 해당 글에대한 추천과 즐겨찾기 여부를 조회해서 같이 정보에 담아서 리턴
		if( !userid.equals("null")) {
			//즐겨찾기, 추천 여부 조회 (vo 객체 새로 만들어서 파라미터값 던져줌)
			BoardVO bfVo = new BoardVO();
			bfVo.setB_no(no);
			bfVo.setB_userid(userid);
			fVo = sql.selectOne("board.mapper.boardFavorite", bfVo);
			if(fVo == null) {
				//사용자가 해당글에 처음 접근했다면 favorite 테이블에 이력 추가 (디폴트값 N, N으로)
				sql.insert("board.mapper.boardAddFavorite", bfVo);
				//추가되었으니 다시 조회!
				fVo = sql.selectOne("board.mapper.boardFavorite", bfVo);
			}
		} else {
			//비로그인 사용자가 조회시 -> 추천,즐찾 초기화(임의로 디폴트값인 N을 삽입해서 넣어줌)
			fVo = new FavoriteVO();
			fVo.setF_favorites("N");
			fVo.setF_ddabong("N");
		}
		
		//위에서 조회해온 모든 정보 위에 생성해놓은 jo 객체에 담아줌!
		data.put("b_no", bVo.getB_no());
		data.put("b_userid", bVo.getB_userid());
		data.put("b_title", bVo.getB_title());
		data.put("b_coment", bVo.getB_coment());
		data.put("b_local", bVo.getB_local());
		
		data.put("b_ddabong", bVo.getB_ddabong());
		data.put("b_tag", bVo.getB_tag());
		data.put("b_readcnt", bVo.getB_readcnt());
		data.put("b_writedate", bVo.getB_writedate());
		data.put("b_latitude", bVo.getB_latitude());
		
		data.put("b_longitude", bVo.getB_longitude());
		data.put("b_nick", bVo.getB_nick());
		data.put("b_imgpath", Common.IMAGE_FILE_PATH+bVo.getB_imgpath());
		data.put("b_profileimg", Common.PROFILE_IMG_PATH+bVo.getB_profileimg());
		
		
		data.put("f_ddabong", fVo.getF_ddabong());
		data.put("f_favorites", fVo.getF_favorites());
		
		System.out.println("================ Board Detail Data ================");
		System.out.println(data.toString());
		
		return data;
	}

	@Override
	public void boardDdabong(FavoriteVO vo) {
		sql.update("board.mapper.boardDdabong", vo);
	}

	@Override
	public void boardDdabongUpdate(FavoriteVO vo) {
		sql.update("board.mapper.boardDdabongUpdate", vo);
	}

	@Override
	public JSONArray myFavoritesList(String userid) {
		List<BoardVO> list = sql.selectList("board.mapper.myFavoriteList", userid);
		System.out.println("================ MyFavorite List Data ================");
		JSONArray datas = Common.boardListParser(list);
		return datas;
	}

	@Override
	public JSONArray markerNearby(double latitude, double longitude) {
		BoardVO vo = new BoardVO();
		vo.setB_latitude(latitude);
		vo.setB_longitude(longitude);
		
		List<BoardVO> list = sql.selectList("board.mapper.markerNearby", vo);
		System.out.println("================ Marker Nearby Action ================");
		JSONArray datas = Common.boardListParser(list);
		return datas;
	}

	@Override
	public JSONArray searchList(String choice, String search) {
		
		//검색조건 분기
		if(choice.equals("제목")) {
			choice = "b.b_title";
		} else if (choice.equals("닉네임")) {
			choice = "u.u_nick";
		} else if (choice.equals("위치")) {
			choice = "b.b_local";
		}
		
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("choice", choice);
		param.put("search", search);
		
		List<BoardVO> list = sql.selectList("board.mapper.searchList", param);
		System.out.println("================ Board SearchResult List Data ================");
		JSONArray datas = Common.boardListParser(list);
		return datas;
	}
	
	
}
