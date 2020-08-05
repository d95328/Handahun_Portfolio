package web.map;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {
	@Autowired private MapDAO dao;


	@Override
	public JSONArray nearmarker(HashMap<String, Double> map) {
		
		return dao.nearmarker(map);
	}

}
