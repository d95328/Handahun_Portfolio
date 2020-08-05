package web.map;

import java.util.HashMap;

import org.json.simple.JSONArray;

public interface MapService {

	
	JSONArray nearmarker(HashMap<String, Double> map); 
	
}
