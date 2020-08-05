package web.user;

import java.util.HashMap;

public interface WebUserService {

	
	boolean user_insert(WebUserVO vo);
	WebUserVO user_login(HashMap<String, String> map);
	boolean user_id_check(String u_userid);
	boolean user_nick_check(String u_usernick);
	boolean user_update(WebUserVO vo);
	boolean user_delete(String u_userid);
	void createAuthKey(String u_userid, String u_authkey)throws Exception;
	void userAuth(String u_userid, String u_authkey)throws Exception;
}
