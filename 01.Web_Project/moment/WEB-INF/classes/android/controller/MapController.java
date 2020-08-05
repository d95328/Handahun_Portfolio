package android.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MapController {

	@RequestMapping("/list.map")
	public String map() {
		
		return "map/map";
	}
}
