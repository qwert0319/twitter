package data;

import java.util.Date;

public class User {
	String id;
	String pass;
	String nick;
	String profile;
	String img;
	Date joined;

	int follwerCnt;
	
	
	public int getFollwerCnt() {
		return follwerCnt;
	}
	public void setFollwerCnt(int follwerCnt) {
		this.follwerCnt = follwerCnt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Date getJoined() {
		return joined;
	}
	public void setJoined(Date joined) {
		this.joined = joined;
	}
	
	
	
}
