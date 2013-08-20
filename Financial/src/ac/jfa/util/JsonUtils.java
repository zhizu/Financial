package ac.jfa.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.modal.CategoryItem;
import ac.jfa.modal.NewsItem;
import ac.jfa.modal.NoticeItem;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

	public List<NewsItem> parseItemFromJson(String jsonData){
		List<NewsItem> list = new ArrayList<NewsItem>();
		Type listType = new TypeToken<LinkedList<NewsItem>>(){}.getType();
		Gson gson = new Gson();
		LinkedList<NewsItem> items = gson.fromJson(jsonData, listType);
		for(Iterator iterator = items.iterator(); iterator.hasNext();){
			NewsItem item = (NewsItem)iterator.next();
           list.add(item);
		}
		return list;
	}
	
	public List<NoticeItem> parseNoticeItemFromJson(String jsonData){
		List<NoticeItem> list = new ArrayList<NoticeItem>();
		Type listType = new TypeToken<LinkedList<NoticeItem>>(){}.getType();
		Gson gson = new Gson();
		LinkedList<NoticeItem> items = gson.fromJson(jsonData, listType);
		for(Iterator iterator = items.iterator(); iterator.hasNext();){
			NoticeItem item = (NoticeItem)iterator.next();
           list.add(item);
		}
		return list;
	}
	
	public List<LatLng> parsePoilyFromJson(JSONArray jsonData) throws JSONException{
		List<LatLng> list = new ArrayList<LatLng>();
		
		for(int i=0; i<jsonData.length();i++){
			try {
				JSONObject json = jsonData.getJSONObject(i);
				JSONObject jsonObject = json.getJSONObject("start_location");
				String lng = jsonObject.getString("lng");
				String lat = jsonObject.getString("lat");
				LatLng laLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
				list.add(laLng);			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public List<CategoryItem> parseCategoryItemFromJson(String jsonData){
		List<CategoryItem> list = new ArrayList<CategoryItem>();
		Type listType = new TypeToken<LinkedList<CategoryItem>>(){}.getType();
		Gson gson = new Gson();
		LinkedList<CategoryItem> items = gson.fromJson(jsonData, listType);
		for(Iterator iterator = items.iterator(); iterator.hasNext();){
			CategoryItem item = (CategoryItem)iterator.next();
           list.add(item);
		}
		return list;
	}
}
