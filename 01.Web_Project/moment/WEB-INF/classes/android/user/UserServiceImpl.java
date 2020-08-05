package android.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired private UserDAO dao;
	
	@Override
	public UserVO userLogin(HashMap<String, String> map) {
		return dao.userLogin(map);
	}

	@Override
	public JSONObject userSignUp(HttpServletRequest request, HttpServletResponse response) {
		return dao.userSignUp(request, response);
	}

	@Override
	public JSONObject userModify(HttpServletRequest request, HttpServletResponse response) {
		return dao.userModify(request, response);
	}

	@Override
	public JSONObject userIdDuplicateCheck(String u_userid) {
		return dao.userIdDuplicateCheck(u_userid);
	}

	@Override
	public JSONObject userNickDuplicateCheck(String u_nick) {
		return dao.userNickDuplicateCheck(u_nick);
	}

}
