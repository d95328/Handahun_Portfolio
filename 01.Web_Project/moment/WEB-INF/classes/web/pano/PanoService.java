package web.pano;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import web.board.WebBoardVO;

public interface PanoService {
	PanoPage pano_list(PanoPage page);
	void pano_fileUpload(String userid, String imgpath);
	JSONObject pano_write(HashMap<String, Object> param);
	JSONArray pano_android_list();
	int pano_insert( WebBoardVO vo );			//파노라마 글 저장
}
