package ac.jfa.modal;

import java.io.Serializable;

public class Video implements Serializable{

	private static final long serialVersionUID = 2882303961882633472L;
 
	private String movie_category_name;
	private String movie_image;
	private String movie_date;
	private String speaker;
	private String text_link_url;
	private String movie_url;
	private String movie_title;
	private String movie_text;
	private String text_link_title;
	private String movie_category;
	public String getMovie_category_name() {
		return movie_category_name;
	}
	public void setMovie_category_name(String movie_category_name) {
		this.movie_category_name = movie_category_name;
	}
	public String getMovie_image() {
		return movie_image;
	}
	public void setMovie_image(String movie_image) {
		this.movie_image = movie_image;
	}
	public String getMovie_date() {
		return movie_date;
	}
	public void setMovie_date(String movie_date) {
		this.movie_date = movie_date;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	public String getText_link_url() {
		return text_link_url;
	}
	public void setText_link_url(String text_link_url) {
		this.text_link_url = text_link_url;
	}
	public String getMovie_url() {
		return movie_url;
	}
	public void setMovie_url(String movie_url) {
		this.movie_url = movie_url;
	}
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	public String getMovie_text() {
		return movie_text;
	}
	public void setMovie_text(String movie_text) {
		this.movie_text = movie_text;
	}
	public String getText_link_title() {
		return text_link_title;
	}
	public void setText_link_title(String text_link_title) {
		this.text_link_title = text_link_title;
	}
	public String getMovie_category() {
		return movie_category;
	}
	public void setMovie_category(String movie_category) {
		this.movie_category = movie_category;
	}
	
}
