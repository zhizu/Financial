package ac.jfa.modal;

import java.io.Serializable;

public class UserItem implements Serializable{

	private static final long serialVersionUID = 2884473795377859805L;
	private String sid;
	private String ko_tani;
	private String ko_point;
	private String ko_no;
	private String ko_mail;
	private String ko_name1;
	private String ko_name2;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getKo_tani() {
		return ko_tani;
	}
	public void setKo_tani(String ko_tani) {
		this.ko_tani = ko_tani;
	}
	public String getKo_point() {
		return ko_point;
	}
	public void setKo_point(String ko_point) {
		this.ko_point = ko_point;
	}
	public String getKo_no() {
		return ko_no;
	}
	public void setKo_no(String ko_no) {
		this.ko_no = ko_no;
	}
	public String getKo_mail() {
		return ko_mail;
	}
	public void setKo_mail(String ko_mail) {
		this.ko_mail = ko_mail;
	}
	public String getKo_name1() {
		return ko_name1;
	}
	public void setKo_name1(String ko_name1) {
		this.ko_name1 = ko_name1;
	}
	public String getKo_name2() {
		return ko_name2;
	}
	public void setKo_name2(String ko_name2) {
		this.ko_name2 = ko_name2;
	}
	
}
