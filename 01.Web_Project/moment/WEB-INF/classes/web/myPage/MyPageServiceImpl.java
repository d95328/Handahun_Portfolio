package web.myPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired private MyPageDAO dao;
	
	@Override
	public MyPagePage myList(MyPagePage page) {
		// TODO Auto-generated method stub
		return dao.myList(page);
	}
	


	@Override
	public MyPageVO myList_detail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void myList_delete(int id) {
		dao.myList_delete(id);
		
	}

	
	@Override
	public MyPagePage myFavorite(MyPagePage page) {
		return dao.myFavorite(page);
	}



	@Override
	public MyPagePage myDdabong(MyPagePage page) {
		// TODO Auto-generated method stub
		return dao.myDdabong(page);
	}



	@Override
	public MyPagePage memberpostlist(MyPagePage page) {
		// TODO Auto-generated method stub
		return dao.memberpostlist(page);
	}







}
