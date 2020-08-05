package web.myPage;

import java.util.List;


public interface MyPageService {

	
	//내가 쓴글 리스트 
	MyPagePage myList(MyPagePage page);
	
	//내가 쓴글 상세보기 
	MyPageVO myList_detail(int id);	
	
	//내가 쓴글 삭제
	void myList_delete(int id);
		
	//즐겨 찾기 리스트 	
	MyPagePage myFavorite(MyPagePage page);
	
	//좋아요한 리스트
	MyPagePage myDdabong(MyPagePage page);
		
	
	MyPagePage memberpostlist (MyPagePage page);
	
}
