package web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.map.MapServiceImpl;


@Controller
public class WebMapController {

	@Autowired private MapServiceImpl service;
	
	// 지도화면 요청
	@RequestMapping("/map.moment")
	public String mylist(HttpSession session) {
		session.setAttribute("category", "ma");
		return "map/map";
	}
		
	// 주변에 있는 게시물 마커 찾기 
	@ResponseBody
	@RequestMapping("/nearMarker.moment")
	public JSONArray nearmarker(double b_latitude, double b_longitude) {

		System.out.println("검색 위치 : lat : "+b_latitude);
		System.out.println("검색 위치 : lng : "+b_longitude);
		
		HashMap<String, Double> map = new HashMap<>();
		map.put("b_latitude", b_latitude);
		map.put("b_longitude", b_longitude);		
		
		//System.out.println(map.toString());
	    JSONArray data = (service.nearmarker(map));	
	
		
		return data;
	
	}
		
	
}
