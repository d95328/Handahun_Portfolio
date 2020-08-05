package web.qna;

import java.util.List;

import org.springframework.stereotype.Component;

import web.common.PageVO;

@Component
public class QnaPage extends PageVO {
	private List<QnaVO> list;
	private String userid;

	public List<QnaVO> getList() {
		return list;
	}

	public void setList(List<QnaVO> list) {
		this.list = list;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
