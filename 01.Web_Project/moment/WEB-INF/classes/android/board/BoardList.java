package android.board;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BoardList extends BoardVO {
	
	private List<BoardVO> list;

	public List<BoardVO> getList() {
		return list;
	}

	public void setList(List<BoardVO> list) {
		this.list = list;
	}
	
	
}
