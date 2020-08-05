package android.board;

public class FavoriteVO {
	private String f_userid;
	private int f_bno;
	private String f_favorites;
	private String f_ddabong;
	
	public FavoriteVO() {}
	
	public FavoriteVO(String f_userid, int f_bno, String f_favorites, String f_ddabong) {
		super();
		this.f_userid = f_userid;
		this.f_bno = f_bno;
		this.f_favorites = f_favorites;
		this.f_ddabong = f_ddabong;
	}

	public String getF_userid() {
		return f_userid;
	}

	public void setF_userid(String f_userid) {
		this.f_userid = f_userid;
	}

	public int getF_bno() {
		return f_bno;
	}

	public void setF_bno(int f_bno) {
		this.f_bno = f_bno;
	}

	public String getF_favorites() {
		return f_favorites;
	}

	public void setF_favorites(String f_favorites) {
		this.f_favorites = f_favorites;
	}

	public String getF_ddabong() {
		return f_ddabong;
	}

	public void setF_ddabong(String f_ddabong) {
		this.f_ddabong = f_ddabong;
	}

	@Override
	public String toString() {
		return "FavoriteVO [f_userid=" + f_userid + ", f_bno=" + f_bno + ", f_favorites=" + f_favorites + ", f_ddabong="
				+ f_ddabong + "]";
	}
	
	
}
