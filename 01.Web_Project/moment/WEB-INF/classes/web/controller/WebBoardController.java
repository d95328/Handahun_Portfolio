package web.controller;


import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import web.board.WebBoardPage;
import web.board.WebBoardServiceImpl;
import web.board.WebBoardVO;
import web.board.WebFavoriteVO;
import web.common.Common;
import web.email.CommonService;
import web.user.WebUserVO;

@Controller
public class WebBoardController {
	
	@Autowired private WebBoardServiceImpl service;
	@Autowired private WebBoardPage page;
	@Autowired private CommonService common;

	private String key = "AIzaSyDrfll4QoaTNLPA3Zhpd0P_72bmSVjqNYk";	
	private String map_url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	
	//게시글 수정요청
	@RequestMapping(value="/update.bo", produces="text/html; charset=utf-8")
	public String update(WebBoardVO vo) {
		System.out.println("======== 게시글 수정 요청 ========");
		double lat =  (Double)vo.getB_latitude();
		double lon =  (Double)vo.getB_longitude();
		
		vo.setB_latitude(lat);
		vo.setB_longitude(lon);
		
		service.board_update(vo);
		return "redirect:list.bo";
	}
	
	//글 수정화면 요청
	@RequestMapping("/modify.bo")
	public String modify(Model model, int no, String userid) {
		WebBoardVO vo = service.board_detail(no, userid);
		model.addAttribute("vo", vo);
		
		return "pictures/modify";
	}
	
	//글삭제 요청
	@RequestMapping("/delete.bo")
	public String delete(int no, String userid) {
		System.out.println("======== 게시글 삭제 요청 ========");
		//삭제처리 전에 미리 imgPath 받아놓음.
		WebBoardVO vo = service.board_detail(no, userid);
		String imgPath = Common.IMAGE_PATH_BACKGROUND +"/"+ vo.getB_imgpath();
		System.out.println("삭제요청 게시물 이미지경로 : "+imgPath);
		
		//해당 받아온 게시글번호로 삭제처리
		int result = (Integer)service.board_delete(no);
		
		//삭제가 성공한다면
		if(result > 0) {
			//삭제 요청 시 서버의 이미지도 삭제하는 처리
			//일단 해당글 조회해서 db의 imgPath 얻어옴
			File file = new File(imgPath);
			
			if(file.exists()) {
				file.delete();
			}
		}
		
		return "redirect:list.bo";
	}
	
	//디테일에서 사용자가 즐겨찾기 아이콘 클릭시
	@ResponseBody @RequestMapping("/fav.bo")
	public void fav(int no, String userid, String ddabong, String fav) {
		if(fav.equals("N")) {
			System.out.println(no + " 번 글 즐겨찾기 요청 / 추천한 사용자 ID : " + userid);
		} else {
			System.out.println(no + " 번 글 즐겨찾기 취소 요청 / 추천한 사용자 ID : " + userid);
		}
		
		//쿼리문 처리에 사용할 fVo 객체생성
		WebFavoriteVO fVo = new WebFavoriteVO();
		fVo.setF_bno(no);
		fVo.setF_userid(userid);
		fVo.setF_ddabong(ddabong);
		fVo.setF_favorites(fav);
		service.boardDdabong(fVo);
		service.boardDdabongUpdate(fVo);
		
		System.out.println("변경된 즐겨찾기정보 : " + fVo.getF_ddabong());
	}
	
	//디테일에서 로그인한 사용자가 추천버튼 클릭시
	@ResponseBody @RequestMapping("/ddabong.bo")
	public void ddabong(int no, String userid, String ddabong, String fav) {
		if(ddabong.equals("N")) {
			System.out.println(no + " 번 글 추천 요청 / 추천한 사용자 ID : " + userid);
		} else {
			System.out.println(no + " 번 글 추천 취소 요청 / 추천한 사용자 ID : " + userid);
		}
		
		//쿼리문 처리에 사용할 fVo 객체생성
		WebFavoriteVO fVo = new WebFavoriteVO();
		fVo.setF_bno(no);
		fVo.setF_userid(userid);
		fVo.setF_ddabong(ddabong);
		fVo.setF_favorites(fav);
		service.boardDdabong(fVo);
		service.boardDdabongUpdate(fVo);
		
		System.out.println("변경된 추천정보 : " + fVo.getF_ddabong());
	}
	
	//사진디테일  클릭시
	@ResponseBody @RequestMapping("/detail.bo")
	public WebBoardVO detail(HttpSession session ,Model model, int no, @RequestParam(defaultValue = "bLogin") String userid) {
		//view에서 사용하기 위한 조회한 글 작성자 id 값 session에 담아주기
		session.setAttribute("detailUserid", "");
		
		System.out.println("======== " + no + "번 게시물 상세정보 요청 ========");
		
		System.out.println("조회한 사용자 id : " + userid);
		//처음 조회해온 vo값
		WebBoardVO vo =  service.board_detail(no, userid);
		session.setAttribute("detailUserid", vo.getB_userid());
		System.out.println("세션 작성자아이디값"+ session.getAttribute("detailUserid"));
//		model.addAttribute("detailUserid", vo.getB_userid());
//		System.out.println(vo.getB_userid());
		
		//디테일조회해온 글의 사진파일이 서버에 있는지 확인
		//디테일화면에 리턴해줄 vo 새로생성
		WebBoardVO resultVo = existsDetailImgTest(vo);
		//img 서버존재 검사 후 리턴
		System.out.println("디테일 조회 값 : " + resultVo);
		return resultVo;
	}
	
	//더보기 이벤트 ajax 통신시 다음페이지 14개 출력
	@ResponseBody @RequestMapping("/more.bo")
	public List<WebBoardVO> more(Model model, String search, String keyword, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "10") int pageList, @RequestParam(defaultValue = "lately") String viewType ) {
		System.out.println("======== 다음 ("+(page.getCurPage()+1)+")페이지 출력 ========");
		page.setCurPage(page.getCurPage()+curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setPageList(pageList);
		
		WebBoardPage dbLoadPage = service.board_list(page);
		WebBoardPage resultPage = existsImgTest(dbLoadPage);
		
		List<WebBoardVO> list = resultPage.getList();
		
		System.out.println(list);
		return list;
	}
	
	//POST 카테고리로 이동
	@RequestMapping("/list.bo")
	public String board(Model model, HttpSession session, String search, String keyword, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "10") int pageList, @RequestParam(defaultValue = "lately") String viewType) {
		System.out.println("======== 사진 카테고리 이동 ========");
		session.setAttribute("category", "bo");
		
		System.out.println(viewType);
		if(viewType.equals("lately")) {
			System.out.println("ViewType : 최신순 목록 출력");
		} else if(viewType.equals("ddabong")){
			System.out.println("ViewType : 추천순 목록 출력");
		}
		
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setPageList(pageList);
		page.setViewType(viewType);
		
		WebBoardPage dbLoadPage = service.board_list(page);
		
		WebBoardPage resultPage = existsImgTest(dbLoadPage);
		
		
		model.addAttribute( "page", resultPage );
		System.out.println("1페이지 목록출력" + resultPage.getList());
		
		return "pictures/list";
	}
	
	//글쓰기 페이지 요청
	@RequestMapping("/new.bo")
	public String board_new() {
		
		return "pictures/new";		
	}
	
	//새로운 게시글 저장
	@RequestMapping(value="/insert.bo", produces="text/html; charset=utf-8")
	public String insert(WebBoardVO vo, MultipartFile file, HttpSession session) {
		System.out.println("======== 게시글 작성 요청 ========");
		if ( !file.isEmpty()) {
			vo.setB_imgpath(common.uploadpic("background", file, session));
		}
			

		vo.setB_userid( ((WebUserVO)session.getAttribute("login_info")).getU_userid());
		System.out.println("새 글 : " + vo.toString());
		service.board_insert(vo); 
		System.out.println();
		return "redirect:list.bo";		
	}
	
	
	
	//새글 지도 검색
	@ResponseBody @RequestMapping(value="/search_location", produces="application/json; charset=utf-8")
	public JSONObject search_location(String address) {
		System.out.println(address);
		StringBuilder url = new StringBuilder( map_url + address);
		url.append("&key=" + key);		
		url.append("&language=ko");		

			
		return common.json_list(url);
	}
	
	//리스트 목록 조회시 사진파일이 서버에 저장되어있지않으면 imgpath 변경해주는 메소드
	private WebBoardPage existsImgTest(WebBoardPage page) {
		//db에 저장된 imgpath가 서버에 없을경우 default 이미지로 변경해주는 로직
		//가져온 배열 10번돔
		for(int i=0; i<page.getList().size(); i++) {
			//10개의배열들의 imgpath 추출해서 바꿔줘야함
			//(1) 각배열의 img
//			System.out.println("getImgpath : "+page.getList().get(i).getB_imgpath());
			String filePath = Common.IMAGE_PATH_BACKGROUND +"/"+ page.getList().get(i).getB_imgpath();
			
//			System.out.println("resource ["+i+"] : "+filePath);
			try {
				//resource path를 getFile메소드를통해 불러와서 File객체 img에 담아줌
				File img = new File(filePath);
//				System.out.println("img 객체["+i+"] : "+img);
				//img 객체가 존재하지않는다면
				if(!img.exists()) {
					page.getList().get(i).setB_imgpath("404e.png");
				}
			} catch (Exception e) {
				System.out.println("path load error!");
			}
		}
			
//		for(int i=0; i<page.getList().size(); i++) {
//			System.out.println("수정 후 이미지패스["+i+"] : " + page.getList().get(i).getB_imgpath());
//		}
		return page;
	}
	
	private WebBoardVO existsDetailImgTest(WebBoardVO vo) {
		String filePath = Common.IMAGE_PATH_BACKGROUND  +"/"+ vo.getB_imgpath();
		String filePathProfile = Common.IMAGE_PATH_PROFILE  +"/" +vo.getB_profileimg();
		try {
			File img = new File(filePath);
			File img2 = new File(filePathProfile);
			
			//img 파일이 서버에 존재하지 않는다면
			if(!img.exists()) {
				vo.setB_imgpath("404e.png");
			}
			if(!img2.exists()) {
				vo.setB_profileimg("profiledefault.png");
			}
		} catch (Exception e) {
			System.out.println("path load error!");
		}
		return vo;
	}

	
	
	
	
	
	
	
	
	
	
}
