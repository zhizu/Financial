package ac.jfa.modal;

import java.io.Serializable;

public class HistoryItem implements Serializable{

	private static final long serialVersionUID = 1291149321087692374L;

	private String tani;
	private String kaijo_address;
	private String status;
	private String sho_name;
	private String kaijo;
	private String nittei;
	private String kaijo_map_url;
	public String getTani() {
		return tani;
	}
	public void setTani(String tani) {
		this.tani = tani;
	}
	public String getKaijo_address() {
		return kaijo_address;
	}
	public void setKaijo_address(String kaijo_address) {
		this.kaijo_address = kaijo_address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getNittei() {
		return nittei;
	}
	public void setNittei(String nittei) {
		this.nittei = nittei;
	}
	public String getKaijo_map_url() {
		return kaijo_map_url;
	}
	public void setKaijo_map_url(String kaijo_map_url) {
		this.kaijo_map_url = kaijo_map_url;
	}
	
	
}
