package web.user;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class WebUserServiceImpl implements WebUserService {
	@Autowired private WebUserDAO dao;
	
	@Override
	public boolean user_insert(WebUserVO vo) {
		// TODO Auto-generated method stub
		return dao.user_insert(vo);
	}

	@Override
	public WebUserVO user_login(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return dao.user_login(map);
	}
	@Override
	public boolean user_nick_check(String u_nick) {
		// TODO Auto-generated method stub
		return dao.user_nick_check(u_nick);
	}

	@Override
	public boolean user_id_check(String u_userid) {
		// TODO Auto-generated method stub
		return dao.user_id_check(u_userid);
	}

	@Override
	public boolean user_update(WebUserVO vo) {
		return dao.user_update(vo);
	}

	@Override
	public boolean user_delete(String u_userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createAuthKey(String u_userid, String u_authkey) throws Exception {
		// TODO Auto-generated method stub
		dao.createAuthKey(u_userid, u_authkey);
	}

	@Override
	public void userAuth(String u_userid, String u_authkey) throws Exception {
		// TODO Auto-generated method stub
		dao.userAuth(u_userid, u_authkey);
	}

}
