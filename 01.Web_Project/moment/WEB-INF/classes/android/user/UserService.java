package android.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public interface UserService {
	
	UserVO userLogin(HashMap<String, String> map);	//유저 로그인
	JSONObject userSignUp(HttpServletRequest request, HttpServletResponse response);						//유저 회원가입
	JSONObject userModify(HttpServletRequest request, HttpServletResponse response);						//유저 정보수정
	JSONObject userIdDuplicateCheck(String u_userid);		//유저 아이디 중복확인 체크
	JSONObject userNickDuplicateCheck(String u_nick);		//유저 닉네임 중복확인 체크
	
	
	
}
