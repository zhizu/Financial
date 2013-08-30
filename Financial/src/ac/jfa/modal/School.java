package ac.jfa.modal;

import java.io.Serializable;

public class School implements Serializable{

	private static final long serialVersionUID = -2706497425689226117L;

	private String kaijo_address;
	private String situmonkai;
	private String chu_no;
	private String sho_no;
	private String key_chu_day;
	private String kaijo;
	private String kaijo_map_url;
	private String sho_name;
	private String sho_zaiko;
	private String nittei;
	private String title;
	private String kigen;
	private boolean flag;
	public String getSitumonkai() {
		return situmonkai;
	}
	public void setSitumonkai(String situmonkai) {
		this.situmonkai = situmonkai;
	}
	public String getChu_no() {
		return chu_no;
	}
	public void setChu_no(String chu_no) {
		this.chu_no = chu_no;
	}
	public String getSho_no() {
		return sho_no;
	}
	public void setSho_no(String sho_no) {
		this.sho_no = sho_no;
	}
	public String getKey_chu_day() {
		return key_chu_day;
	}
	public void setKey_chu_day(String key_chu_day) {
		this.key_chu_day = key_chu_day;
	}
	public String getSho_name() {
		return sho_name;
	}
	public void setSho_name(String sho_name) {
		this.sho_name = sho_name;
	}
	public String getKaijo() {
		return kaijo;
	}
	public void setKaijo(String kaijo) {
		this.kaijo = kaijo;
	}
	public String getSho_zaiko() {
		return sho_zaiko;
	}
	public void setSho_zaiko(String sho_zaiko) {
		this.sho_zaiko = sho_zaiko;
	}
	public String getNittei() {
		return nittei;
	}
	public void setNittei(String nittei) {
		this.nittei = nittei;
	}
	public String getKaijo_address() {
		return kaijo_address;
	}
	public void setKaijo_address(String kaijo_address) {
		this.kaijo_address = kaijo_address;
	}
	public String getKaijo_map_url() {
		return kaijo_map_url;
	}
	public void setKaijo_map_url(String kaijo_map_url) {
		this.kaijo_map_url = kaijo_map_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKigen() {
		return kigen;
	}
	public void setKigen(String kigen) {
		this.kigen = kigen;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}
