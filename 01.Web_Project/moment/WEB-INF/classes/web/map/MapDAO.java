package web.map;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import android.board.BoardVO;

@Repository
public class MapDAO implements MapService {
	@Autowired private SqlSession sql;
	
	@Override
	public JSONArray nearmarker(HashMap<String, Double> map) {
		//System.out.println("dao: 들어옴 " + map);
		//System.out.println("dao : " + sql.selectList("map.mapper.nearmarker",map));
		
		List<MapVO> list = sql.selectList("map.mapper.nearmarker", map);
		System.out.println("db에서 list형식으로 받아옴: " + list.toString());
		
		JSONArray datas = new JSONArray();
		JSONObject data = null;
		for(MapVO vo : list) {
			data = new JSONObject();
			data.put("b_latitude", vo.b_latitude);
			data.put("b_longitude", vo.b_longitude);
			data.put("b_imgpath", vo.b_imgpath);
			data.put("b_title", vo.b_title);
			data.put("b_no", vo.b_no);
			data.put("b_ddabong", vo.b_ddabong);
			data.put("b_userid", vo.b_userid);
			data.put("b_readcnt", vo.b_readcnt);
			datas.add(data);
			
			System.out.println(data.toString());
		}
		
		return datas;
	}


}
