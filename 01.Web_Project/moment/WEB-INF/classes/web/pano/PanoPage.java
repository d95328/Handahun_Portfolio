package web.pano;

import java.util.List;

import org.springframework.stereotype.Component;

import android.board.BoardVO;
import web.common.PageVO;

@Component
public class PanoPage extends PageVO {
	private List<BoardVO> list;
	public List<BoardVO> getList() {
		return list;
	}

	public void setList(List<BoardVO> list) {
		this.list = list;
	}
	
}
