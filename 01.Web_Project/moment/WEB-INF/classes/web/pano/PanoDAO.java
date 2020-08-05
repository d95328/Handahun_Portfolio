package web.pano;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import android.board.BoardVO;
import android.common.Common;
import web.board.WebBoardVO;

@Repository
public class PanoDAO implements PanoService {
	@Autowired private SqlSession sql;
	
	@Override
	public PanoPage pano_list(PanoPage page) {
		page.setTotalList((Integer)sql.selectOne("pano.mapper.total_list"));
		
		page.setList(sql.selectList("pano.mapper.list", page));
		return page;
	}

	@Override
	public void pano_fileUpload(String userid, String imgpath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("imgpath", imgpath);
		sql.insert("pano.mapper.img_upload", map);
	}

	@Override
	public JSONObject pano_write(HashMap<String, Object> param) {
		Map<String, Object> map = param;
		int result = (Integer)sql.insert("pano.mapper.write",map);
		JSONObject json = new JSONObject();
		
		if(result > 0) {
			json.put("result", "success");
		}else {
			json.put("result", "fail");
		}
		return json;
	}

	@Override
	public JSONArray pano_android_list() {
		List<BoardVO> list = sql.selectList("pano.mapper.android_list");
		JSONArray datas = Common.boardListParser(list);
		return datas;
	}
		
	public int pano_insert(WebBoardVO vo) {
		// TODO Auto-generated method stub
		return sql.insert("pano.mapper.insert",vo);
	}

}
