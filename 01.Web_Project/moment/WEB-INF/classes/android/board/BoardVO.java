package android.board;


public class BoardVO {
	private int b_no;
    private int b_ddabong;
    private int b_readcnt;
    private String b_userid;
    private String b_local;
    private String b_coment;
    private String b_imgpath;
    private String b_tag;
    private String b_title;
    private String b_writedate;
    private String b_profileimg;
    private String b_nick;
    private double b_latitude;
    private double b_longitude;
    private String f_ddabong;
    private String f_favorites;
    private String b_pano;
    
    public BoardVO() {}
    
    public BoardVO(int b_no, int b_ddabong, int b_readcnt, String b_userid, String b_local, String b_coment,
			String b_imgpath, String b_tag, String b_title, String b_writedate, String b_profileimg, String b_nick,
			double b_latitude, double b_longitude, String f_ddabong, String f_favorites) {
		super();
		this.b_no = b_no;
		this.b_ddabong = b_ddabong;
		this.b_readcnt = b_readcnt;
		this.b_userid = b_userid;
		this.b_local = b_local;
		this.b_coment = b_coment;
		this.b_imgpath = b_imgpath;
		this.b_tag = b_tag;
		this.b_title = b_title;
		this.b_writedate = b_writedate;
		this.b_profileimg = b_profileimg;
		this.b_nick = b_nick;
		this.b_latitude = b_latitude;
		this.b_longitude = b_longitude;
		this.f_ddabong = f_ddabong;
		this.f_favorites = f_favorites;
	}

	public int getB_no() {
		return b_no;
	}

	public void setB_no(int b_no) {
		this.b_no = b_no;
	}

	public int getB_ddabong() {
		return b_ddabong;
	}

	public void setB_ddabong(int b_ddabong) {
		this.b_ddabong = b_ddabong;
	}

	public int getB_readcnt() {
		return b_readcnt;
	}

	public void setB_readcnt(int b_readcnt) {
		this.b_readcnt = b_readcnt;
	}

	public String getB_userid() {
		return b_userid;
	}

	public void setB_userid(String b_userid) {
		this.b_userid = b_userid;
	}

	public String getB_local() {
		return b_local;
	}

	public void setB_local(String b_local) {
		this.b_local = b_local;
	}

	public String getB_coment() {
		return b_coment;
	}

	public void setB_coment(String b_coment) {
		this.b_coment = b_coment;
	}

	public String getB_imgpath() {
		return b_imgpath;
	}

	public void setB_imgpath(String b_imgpath) {
		this.b_imgpath = b_imgpath;
	}

	public String getB_tag() {
		return b_tag;
	}

	public void setB_tag(String b_tag) {
		this.b_tag = b_tag;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_writedate() {
		return b_writedate;
	}

	public void setB_writedate(String b_writedate) {
		this.b_writedate = b_writedate;
	}

	public String getB_profileimg() {
		return b_profileimg;
	}

	public void setB_profileimg(String b_profileimg) {
		this.b_profileimg = b_profileimg;
	}

	public String getB_nick() {
		return b_nick;
	}

	public void setB_nick(String b_nick) {
		this.b_nick = b_nick;
	}

	public double getB_latitude() {
		return b_latitude;
	}

	public void setB_latitude(double b_latitude) {
		this.b_latitude = b_latitude;
	}

	public double getB_longitude() {
		return b_longitude;
	}

	public void setB_longitude(double b_longitude) {
		this.b_longitude = b_longitude;
	}

	public String getF_ddabong() {
		return f_ddabong;
	}

	public void setF_ddabong(String f_ddabong) {
		this.f_ddabong = f_ddabong;
	}

	public String getF_favorites() {
		return f_favorites;
	}

	public void setF_favorites(String f_favorites) {
		this.f_favorites = f_favorites;
	}
	
	public String getB_pano() {
		return b_pano;
	}

	public void setB_pano(String b_pano) {
		this.b_pano = b_pano;
	}

	@Override
	public String toString() {
		return "BoardVO [b_no=" + b_no + ", b_ddabong=" + b_ddabong + ", b_readcnt=" + b_readcnt + ", b_userid="
				+ b_userid + ", b_local=" + b_local + ", b_coment=" + b_coment + ", b_imgpath=" + b_imgpath + ", b_tag="
				+ b_tag + ", b_title=" + b_title + ", b_writedate=" + b_writedate + ", b_profileimg=" + b_profileimg
				+ ", b_nick=" + b_nick + ", b_latitude=" + b_latitude + ", b_longitude=" + b_longitude + ", f_ddabong="
				+ f_ddabong + ", f_favorites=" + f_favorites + "]";
	}
    
    
    
}
