package web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.qna.QnaPage;
import web.qna.QnaServiceImpl;
import web.qna.QnaVO;
import web.user.WebUserVO;


@Controller
public class WebQnaController {
	@Autowired private QnaServiceImpl service;
	@Autowired private QnaPage page;
	
	//자주하는 질문 목록 & 페이지 이동
	@RequestMapping("/list.qn")
	public String list(Model model, HttpSession session, @RequestParam(defaultValue="1") int curPage, 
			String search, String keyword) {
		session.setAttribute("category", "qa");
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		model.addAttribute("page", service.faq_list(page));
		return "qna/list";
	}
	
	//전체 질문 목록
	@ResponseBody @RequestMapping("/all_list.qn")
	public List<QnaVO> all_list(Model model, HttpSession session, @RequestParam(defaultValue="1") int curPage, 
			String search, String keyword) {
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		List<QnaVO> list = service.all_list(page).getList();
		return list;
	}
	
	//내 질문 목록
	@ResponseBody @RequestMapping("/myquestion")
	public List<QnaVO> my_question(Model model, HttpSession session, @RequestParam(defaultValue="1") int curPage, 
			String search, String keyword) {
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setUserid(((WebUserVO) session.getAttribute("login_info")).getU_userid());
		List<QnaVO> list = service.my_question_list(page).getList();
		return list;
	}
	//답변할 목록
	@ResponseBody @RequestMapping("/question_list.qn")
	public List<QnaVO> question_list(Model model, HttpSession session, @RequestParam(defaultValue="1") int curPage, 
			String search, String keyword) {
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		List<QnaVO> list = service.question_list(page).getList();
		return list; 
	}
	
	//답변된 목록
	@ResponseBody @RequestMapping("/answer_list.qn")
	public List<QnaVO> answer_list(Model model, HttpSession session, @RequestParam(defaultValue="1") int curPage, 
			String search, String keyword) {
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		List<QnaVO> list = service.answer_list(page).getList();
		return list;
	}
	
	//질문 등록
	@RequestMapping("/insert.qn")
	public String insert(QnaVO vo, HttpSession session) {
		vo.setWriter(((WebUserVO) session.getAttribute("login_info")).getU_userid());
		service.question_insert(vo);
		return "redirect:list.qn";
	}
	//답변 등록
	@RequestMapping("/answer.qn")
	public String answer(QnaVO vo, HttpSession session) {
		vo.setWriter(((WebUserVO) session.getAttribute("login_info")).getU_userid());
		service.answer_insert(vo);
		return "redirect:list.qn";
	}
	//질문 삭제
	@ResponseBody @RequestMapping("/delete.qn")
	public void delete(int id) {
		service.question_delete(id);
	}
}
