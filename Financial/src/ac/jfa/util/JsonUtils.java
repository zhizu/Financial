package ac.jfa.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.modal.NewsItem;

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
}
