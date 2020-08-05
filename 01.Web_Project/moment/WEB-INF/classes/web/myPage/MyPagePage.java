package web.myPage;

import java.util.List;

import org.springframework.stereotype.Component;

import web.common.PageVO;


@Component
public class MyPagePage extends PageVO {

	private List<MyPageVO> list;

	public List<MyPageVO> getList() {
		return list;
	}

	public void setList(List<MyPageVO> list) {
		this.list = list;
	}
	
	
	
	
	
}
