package android.user;

public class UserVO {
	
	private String u_userid;
	private String u_userpw;
	private String u_name;
	private String u_nick;
	private String u_local;
	private String u_profileimg;
	
	public UserVO() {
		
	}
	
	public UserVO(String u_userid, String u_userpw, String u_name, String u_nick, String u_local, String u_profileimg) {
		super();
		this.u_userid = u_userid;
		this.u_userpw = u_userpw;
		this.u_name = u_name;
		this.u_nick = u_nick;
		this.u_local = u_local;
		this.u_profileimg = u_profileimg;
	}
	
	public String getU_userid() {
		return u_userid;
	}
	public void setU_userid(String u_userid) {
		this.u_userid = u_userid;
	}
	public String getU_userpw() {
		return u_userpw;
	}
	public void setU_userpw(String u_userpw) {
		this.u_userpw = u_userpw;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_nick() {
		return u_nick;
	}
	public void setU_nick(String u_nick) {
		this.u_nick = u_nick;
	}
	public String getU_local() {
		return u_local;
	}
	public void setU_local(String u_local) {
		this.u_local = u_local;
	}
	public String getU_profileimg() {
		return u_profileimg;
	}
	public void setU_profileimg(String u_profileimg) {
		this.u_profileimg = u_profileimg;
	}
	@Override
	public String toString() {
		return "UserVO [u_userid=" + u_userid + ", u_userpw=" + u_userpw + ", u_name=" + u_name + ", u_nick=" + u_nick
				+ ", u_local=" + u_local + ", u_profileimg=" + u_profileimg + "]";
	}
	
	
}
