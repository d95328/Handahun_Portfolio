package web.board;

import java.util.List;

import org.springframework.stereotype.Component;

import web.common.PageVO;

@Component
public class WebBoardPage extends PageVO {
	
	private List<WebBoardVO> list;

	public List<WebBoardVO> getList() {
		return list;
	}

	public void setList(List<WebBoardVO> list) {
		this.list = list;
	}
	
}
