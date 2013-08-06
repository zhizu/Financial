package com.accum.modal;

import java.io.Serializable;

public class LatLong implements Serializable{

	private static final long serialVersionUID = -5081059595019891977L;
	private String lng;
	private String lat;
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
	
	
}
