package web.myPage;

import java.util.List;

import org.springframework.stereotype.Component;

import web.common.PageVO;


@Component
public class Page extends PageVO {

	private List<PageVO> list;

	public List<PageVO> getList() {
		return list;
	}

	public void setList(List<PageVO> list) {
		this.list = list;
	}
	
	
}
