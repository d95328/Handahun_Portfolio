package web.common;

public class PageVO {
	private int pageList=12;			//페이지당 보여질 목록 수
	private int blockPage=10;			//블럭당 보여질 페이지 수
	private int totalList;				//총 목록 수
	private int totalPage;				//총 페이지 수
	private int totalBlock;				//총 블럭의 수
	private int curPage;				//현재 페이지 번호
	private int beginList, endList;		//시작&목록번호
	private int curBlock;				//현재 블럭번호
	private int beginPage, endPage;		//시작&끝 페이지 번호
	private String search, keyword;		//검색유형
	private String userid; //
	private String viewType="list"; 	//목록이 보여질 형태 (디폴트 값은 리스트형태)
	
	private String myInfo;				//qna에서 내아이디전송위한 필드
	private int root;					//qna에서 root값 비교위한 필드
	
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	public String getMyInfo() {
		return myInfo;
	}

	public void setMyInfo(String myInfo) {
		this.myInfo = myInfo;
	}

	public int getPageList() {
		return pageList;
	}

	public void setPageList(int pageList) {
		this.pageList = pageList;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public int getTotalList() {
		return totalList;
	}

	public void setTotalList(int totalList) {
		this.totalList = totalList;
		//총 목록 수에 따라 총 페이지 수 결정:
		totalPage = totalList / pageList + (totalList % pageList==0 ? 0 : 1);
		
		//총 페이지 수에 따라 총 블럭 수 결정
		totalBlock = totalPage / blockPage;
		if( totalPage % blockPage > 0 ) ++totalBlock;
		
		//현재 페이지번호에 따라 보여질 목록의 시작/끝 번호가 결정
		//끝 번호 : 총 목록수 - 페이지당 보여질 목록수 * (페이지번호-1)
		endList = totalList - pageList * (curPage-1);
		beginList = endList - (pageList-1);
		
		//현재 페이지번호에 따라 현재 블럭번호 결정
		//페이지번호 / 블럭당 보여질 페이지 수
		curBlock = curPage / blockPage;
		if( curPage % blockPage > 0 ) ++curBlock;
		
		//현재 블럭번호에 따라 보여질 페이지의 시작/끝 번호가 결정
		//끝페이지번호 : 현재블럭번호 * 블럭당 보여질 페이지 수
		endPage = curBlock * blockPage;
		beginPage = endPage - (blockPage-1);
		
		//블럭번호가 마지막 블럭일 경우 끝 페이지번호는 총 페이지번호이다.
		if( endPage > totalPage ) endPage = totalPage;
		
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getBeginList() {
		return beginList;
	}

	public void setBeginList(int beginList) {
		this.beginList = beginList;
	}

	public int getEndList() {
		return endList;
	}

	public void setEndList(int endList) {
		this.endList = endList;
	}

	public int getCurBlock() {
		return curBlock;
	}

	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
