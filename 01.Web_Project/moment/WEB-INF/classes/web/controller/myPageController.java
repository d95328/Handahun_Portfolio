package web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.myPage.MyPageServiceImpl;
import web.user.WebUserVO;
import web.myPage.MyPagePage;

@Controller
public class myPageController {
	
	@Autowired private MyPageServiceImpl service;
	@Autowired private MyPagePage page;
	
	

	//내가 쓴 게시물 리스트  
	@RequestMapping("/mypostlist.moment")
	public String mylist(@RequestParam(defaultValue = "1") int curPage,	String userid, String search, String keyword,
															Model model, HttpSession session) {
		
		session.setAttribute("category", "my");
		userid = ((WebUserVO) session.getAttribute("login_info")).getU_userid();		
		page.setUserid(userid);
		page.setCurPage(curPage);		
		page.setSearch(search);
		page.setKeyword(keyword);
		System.out.println("컨트롤 : " + userid + ",  curPage: " + curPage + ", keyword: " +keyword);
		model.addAttribute("page",service.myList(page));		
		return "/myPage/mypostlist";
		
	}

	
	//좋아요 게시판 조회 
	@RequestMapping("/myddabong.moment")
	public String myDdabong(@RequestParam(defaultValue="1") int curPage, String userid, Model model, 
												HttpSession session, String search, String keyword) {
		
		userid = ((WebUserVO) session.getAttribute("login_info")).getU_userid();		
		page.setUserid(userid);
		page.setCurPage(curPage);	

		System.out.println("컨트롤 : " + userid + ",  curPage: " + curPage + ", keyword: " +keyword);
		page.setSearch(search);
		page.setKeyword(keyword);
		
		model.addAttribute("page", service.myDdabong(page));	
	
		return "/myPage/myddabonglist";		
	}
	

	
	//즐겨찾기 게시판 리스트 화면
	@RequestMapping("/myfavorite.moment")
	public String myFavorite(@RequestParam(defaultValue="1") int curPage, String userid, Model model, 
												HttpSession session, String search, String keyword) {
		
		userid = ((WebUserVO) session.getAttribute("login_info")).getU_userid();		
		page.setUserid(userid);
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		model.addAttribute("page", service.myFavorite(page));	
	
		return "/myPage/myfavoritelist";		
	} 
	
	
	
	//닉네임 클릭시 닉네임 주인이 쓴 글 보이기
	@RequestMapping("/memberpostlist.moment")
	public String memberpostlist(@RequestParam(defaultValue="1") int curPage, String userid, Model model, HttpSession session) {
		
		session.getAttribute(userid);
		page.setUserid(userid);
		page.setCurPage(curPage);
		model.addAttribute("page", service.memberpostlist(page));	
	
		return "redirect:mypostlist.moment?userid=" + userid;		
	}
	
	
	
	//내가 쓴 게시글 삭제
	@ResponseBody	
	@RequestMapping(value = "/delete.moment" , method = RequestMethod.POST)	
	public String mylistdelete(@RequestParam(value = "ckbox[]") List<String> chArr){
		
		for(String i : chArr) {   
			   int id  = Integer.parseInt(i);	
			   service.myList_delete(id);
		}   		
		
		return "redirect:mylist.moment";
	}
	
	
	
	//내가 쓴 게시물 상세화면 연결
	@RequestMapping("/mylist_detail.moment")
	public String myliSst_detail(@RequestParam int id) {
	
		return "/myList/detail";
	}
	
	
}
