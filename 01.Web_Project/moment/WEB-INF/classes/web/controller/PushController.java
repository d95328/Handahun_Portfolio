package web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class PushController {

	@RequestMapping("/push")
	public String push(HttpSession session) {
		session.setAttribute("category", "al");
		return "fcm/push";
	}
	
	@RequestMapping(value ="/sendPush", method = {RequestMethod.GET,RequestMethod.POST})

	public String sendPush(HttpServletRequest request, Model model) {

		System.out.println("sendPush.......");



		String token = "AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D"; // 서버에 저장된 토큰 가져오기

		

		String title = request.getParameter("title");

		if(StringUtils.isEmpty(title)) title = "Push Default title";

		String content = request.getParameter("content");

		if(StringUtils.isEmpty(content)) title = "Push Default content";

		

		try {

			title   = URLEncoder.encode(title  ,"UTF-8"); // 한글깨짐으로 URL인코딩해서 보냄

			content = URLEncoder.encode(content,"UTF-8");

			System.out.println(String.format("title : %s, content : %s", title, content));

		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		

		ResponseEntity<String> result = sendHttp(token, title, content);



		model.addAttribute("serverTime", token);

		model.addAttribute("result", result.getBody());

		

		return "fcm/push";

	}

	private ResponseEntity<String> sendHttp(String token, String title, String content){

		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders(); 
//
		headers.setContentType(MediaType.APPLICATION_JSON);
//
		headers.add("Authorization", "key=AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D"); // API_KEY : Firebase 내 프로젝트의 서버키

		

		JSONObject json  = new JSONObject();

		json.put("to", token);

		

		JSONObject data = new JSONObject();

		data.put("title"  , title);

		data.put("content", content);

		json.put("data"   ,data);

		

		HttpEntity entity = new HttpEntity(json.toJSONString(), headers);

		return template.exchange("https://fcm.googleapis.com/fcm/send", HttpMethod.POST, entity, String.class);

	}



}
