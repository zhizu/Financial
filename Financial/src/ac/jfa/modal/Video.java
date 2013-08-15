package ac.jfa.modal;

import java.io.Serializable;

public class Video implements Serializable{

	private static final long serialVersionUID = 2882303961882633472L;
 
	private String url;
	private String title;
	private String videourl;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVideourl() {
		return videourl;
	}
	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}
	
}
