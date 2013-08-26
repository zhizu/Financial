package ac.jfa.modal;

import java.io.Serializable;

public class NewsItem implements Serializable{

	private static final long serialVersionUID = -7175869186982901356L;
    
	private String content_id;
	private String feed_id;
	private String content_detail;
	private String content_title;
	private String content_img;
	private String content_url;
	private String content_grp_id;
	private String content_date;
	private String feed_type;
	
	public NewsItem(){
		
	}
	
	public String getContent_id() {
		return content_id;
	}
	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	public String getFeed_id() {
		return feed_id;
	}
	public void setFeed_id(String feed_id) {
		this.feed_id = feed_id;
	}
	public String getContent_detail() {
		return content_detail;
	}
	public void setContent_detail(String content_detail) {
		this.content_detail = content_detail;
	}
	public String getContent_title() {
		return content_title;
	}
	public void setContent_title(String content_title) {
		this.content_title = content_title;
	}
	public String getContent_img() {
		return content_img;
	}
	public void setContent_img(String content_img) {
		this.content_img = content_img;
	}
	public String getContent_url() {
		return content_url;
	}
	public void setContent_url(String content_url) {
		this.content_url = content_url;
	}
	public String getContent_grp_id() {
		return content_grp_id;
	}
	public void setContent_grp_id(String content_grp_id) {
		this.content_grp_id = content_grp_id;
	}
	public String getContent_date() {
		return content_date;
	}
	public void setContent_date(String content_date) {
		this.content_date = content_date;
	}

	public String getFeed_type() {
		return feed_type;
	}

	public void setFeed_type(String feed_type) {
		this.feed_type = feed_type;
	}

}
