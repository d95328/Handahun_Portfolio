package android.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import android.common.Common;
import android.user.UserServiceImpl;
import android.user.UserVO;

@Controller
public class UserController {
	
	@Autowired private UserServiceImpl service;
	
	//로그인 처리
	@ResponseBody @RequestMapping(value = "/userLoginAction.mo", produces = "application/json; charset=UTF-8")
	public JSONObject userLogin(@RequestBody HashMap<String, Object> params) {
		System.out.println("Controller ============= > /userLoginAction.mo");
		System.out.println(params.get("u_userid"));
		
		//파싱한 jsonObject 에서 userid, userpw 값 취득.
		String userid = (String) params.get("u_userid");
		String userpw = (String) params.get("u_userpw");
		
		//조회위해 취득한 값 다시 map에 담아줌
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpwd", userpw);
		
		//service userLogin 메소드에 userid, userpw 값을 넣어 쿼리문 조회. DB 받은 값 vo에 저장.
		UserVO userInfo = service.userLogin(map);
		
		//vo에 받은 정보 JsonObject로 변환하여 안드로이드에 보내줌.
		JSONObject data = new JSONObject();
		
		if(userInfo != null) {
			data.put("result", "success");
			data.put("u_userid", userInfo.getU_userid());
			data.put("u_userpw", userInfo.getU_userpw());
			data.put("u_name", userInfo.getU_name());
			data.put("u_nick", userInfo.getU_nick());
			data.put("u_local", userInfo.getU_local());
			data.put("u_profileimg", Common.PROFILE_IMG_PATH+userInfo.getU_profileimg());
		}else {
			data.put("result", "fail");
		}
		
		System.out.println("================ User Login Result ================");
		System.out.println(data.toString());
		
		return data;
		
	}
	
	//회원정보 수정처리
	@ResponseBody @RequestMapping(value = "/userModifyAction.mo", produces = "application/json; charset=UTF-8")
	public JSONObject userModify(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Controller ============= > /userModifyAction.mo");
		JSONObject result = service.userModify(request, response);
		return result;
	}
	
	//회원가입 처리
	@ResponseBody @RequestMapping(value = "/userSignUpAction.mo", produces = "application/json; charset=UTF-8")
	public JSONObject userSignUp(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Controller ============= > /userSignUpAction.mo");
		JSONObject result = service.userSignUp(request, response);
		return result;
	}
	
	//유저 아이디 중복확인
	@ResponseBody @RequestMapping(value = "/userIdDuplicateCheck.mo", produces = "application/json; charset=UTF-8")
	public JSONObject userIdDuplicateCheck(HttpServletRequest request) {
		System.out.println("Controller ============= > /userIdDuplicateCheck.mo");
		String u_userid = request.getParameter("u_userid");
		
		JSONObject result = service.userIdDuplicateCheck(u_userid);
		return result;
	}
	
	//유저 닉네임 중복확인
	@ResponseBody @RequestMapping(value = "/userNickDuplicateCheck.mo", produces = "application/json; charset=UTF-8")
	public JSONObject userNickDuplicateCheck(HttpServletRequest request) {
		System.out.println("Controller ============= > /userNickDuplicateCheck.mo");
		String u_nick = request.getParameter("u_nick");
		
		JSONObject result = service.userNickDuplicateCheck(u_nick);
		return result;
	}
}
