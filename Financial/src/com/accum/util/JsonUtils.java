package com.accum.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.accum.modal.LatLong;
import com.accum.modal.NewsItem;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

	public void parseItemFromJson(String jsonData){
		Type listType = new TypeToken<LinkedList<NewsItem>>(){}.getType();
		Gson gson = new Gson();
		LinkedList<NewsItem> items = gson.fromJson(jsonData, listType);
		for(Iterator iterator = items.iterator(); iterator.hasNext();){
			NewsItem item = (NewsItem)iterator.next();
			System.out.println("gaosiwei--->" + item.getContent_date() + item.getContent_detail()+ item.getContent_grp_id()+item.getContent_id()+item.getContent_img()+item.getContent_title()+item.getContent_url());

		}
	}
	
	public List<LatLng> parsePoilyFromJson(JSONArray jsonData) throws JSONException{
		List<LatLng> list = new ArrayList<LatLng>();
		
		LatLong item = new LatLong();
		Type listType = new TypeToken<LinkedList<LatLong>>(){}.getType();
		Gson gson = new Gson();
		
		for(int i=0; i<jsonData.length();i++){
			try {
				JSONObject json = jsonData.getJSONObject(i);
				JSONObject jsonObject = json.getJSONObject("start_location");
				String lng = jsonObject.getString("lng");
				String lat = jsonObject.getString("lat");
				LatLng laLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
				list.add(laLng);
//				LinkedList<LatLong> items = gson.fromJson(jsonObject.toString(), listType);
//				for(Iterator iterator = items.iterator(); iterator.hasNext();){
//					item = (LatLong)iterator.next();
//					System.out.println("niutoumamian--->" + item.getLat() + item.getLng());
//				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println("zhang--->" + "³ö´íÁË£¡£¡£¡");
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
