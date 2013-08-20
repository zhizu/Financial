package ac.jfa.modal;

import java.io.Serializable;

public class NoticeItem implements Serializable{

	private static final long serialVersionUID = 272481499536161310L;

	private String send_date;
	private String title;
	private String news_type;
	private String message;
	private String news_id;
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNews_type() {
		return news_type;
	}
	public void setNews_type(String news_type) {
		this.news_type = news_type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	
}
