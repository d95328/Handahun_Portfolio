package web.pano;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.board.WebBoardVO;

@Service
public class PanoServiceImpl implements PanoService {
	@Autowired PanoDAO dao;
	
	@Override
	public PanoPage pano_list(PanoPage page) {
		return dao.pano_list(page);
	}

	@Override
	public void pano_fileUpload(String userid, String imgpath) {
		dao.pano_fileUpload(userid, imgpath);
	}

	@Override
	public JSONObject pano_write(HashMap<String, Object> param) {
		return dao.pano_write(param);
	}

	@Override
	public JSONArray pano_android_list() {
		return dao.pano_android_list();
	}
	
	public int pano_insert(WebBoardVO vo) {
		// TODO Auto-generated method stub
		return dao.pano_insert(vo);
	}
	
	
	
}
