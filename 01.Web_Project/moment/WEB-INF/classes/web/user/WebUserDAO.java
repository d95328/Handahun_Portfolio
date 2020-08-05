package web.user;


import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
@Repository
public class WebUserDAO implements WebUserService {
	@Autowired @Qualifier("moment")private SqlSession sql;
	WebUserVO vo = new WebUserVO();
	
	@Override
	public boolean user_insert(WebUserVO vo) {
		// TODO Auto-generated method stub
			System.out.println(vo.getU_userid()+"getU_userid");
			System.out.println(vo.getU_userpw()+"getU_userpw");
			System.out.println(vo.getU_profileimg()+"getU_profileimg");
		
		return sql.insert("web.user.mapper.insert",vo) > 0 ? true:false;
	}

	@Override
	public WebUserVO user_login(HashMap<String, String> map) {
		// TODO Auto-generated method stub		
		System.out.println(map+"dao");
		return sql.selectOne("web.user.mapper.login", map);
	}
	@Override
	public boolean user_nick_check(String u_nick) {
		// TODO Auto-generated method stub
		return (Integer)sql.selectOne("web.user.mapper.nick_check", u_nick)==0 ? true: false;
	}

	@Override
	public boolean user_id_check(String u_userid) {
		// TODO Auto-generated method stub
		return (Integer)sql.selectOne("web.user.mapper.idcheck", u_userid)==0 ? true : false;
	}

	@Override
	public boolean user_update(WebUserVO vo) {
		return (Integer)sql.update("web.user.mapper.update",vo) == 0 ? false : true;
	}

	@Override
	public boolean user_delete(String u_userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createAuthKey(String u_userid, String u_authkey) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("u_userid", u_userid);
		map.put("u_authkey", u_authkey);

		sql.selectOne("web.user.mapper.createAuthKey", map);	
	}

	@Override
	public void userAuth(String u_userid, String u_authkey) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("u_userid", u_userid);
		map.put("u_authkey", u_authkey);
		
		sql.update("web.user.mapper.userAuth", map);
	}

	
}
