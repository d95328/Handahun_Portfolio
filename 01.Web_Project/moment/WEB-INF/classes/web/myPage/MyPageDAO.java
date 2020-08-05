package web.myPage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyPageDAO implements MyPageService {

	@Autowired private SqlSession sql;


	@Override
	public MyPagePage myList(MyPagePage page) {

		page.setTotalList((Integer)sql.selectOne("mypage.mapper.total", page));	
		System.out.println("총 게시글 수 : totallist: "+ page.getTotalList());	
		page.setList(sql.selectList("mypage.mapper.mylist",page));
		System.out.println(page.getEndList());	
		return page;
	}


	@Override
	public MyPagePage myDdabong(MyPagePage page) {	
	
		//System.out.println("dao 로 들어온 curPage "+ page.getCurPage());	
		//System.out.println("dao 로 들어온 userid "+ page.getUserid());	
		page.setTotalList((Integer)sql.selectOne("mypage.mapper.myddabongtotal", page));		
		//System.out.println("dao 좋아요 총 게시글 수 : totallist: "+ page.getTotalList());	
		page.setList(sql.selectList("mypage.mapper.myddabong",page));


		return page;
	}

	
	@Override
	public MyPagePage myFavorite(MyPagePage page) {		
		page.setTotalList((Integer)sql.selectOne("mypage.mapper.myfavoritetotal", page));		
		page.setList(sql.selectList("mypage.mapper.myfavorite", page));
		return page;
	}

	


	@Override
	public MyPageVO myList_detail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void myList_delete(int id) {
		sql.delete("mypage.mapper.mylistdelete", id);
		
	}


	@Override
	public MyPagePage memberpostlist(MyPagePage page) {
		page.setTotalList((Integer)sql.selectOne("mypage.mapper.memberpostlisttotal", page));		
		page.setList(sql.selectList("mypage.mapper.memberpostlist", page));
		return page;
	}






}
