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
import ac.jfa.modal.HistoryItem;
import ac.jfa.modal.NewsItem;
import ac.jfa.modal.NoticeItem;
import ac.jfa.modal.School;
import ac.jfa.modal.Video;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

	public List<NewsItem> parseItemFromJson(String jsonData) {
		List<NewsItem> list = new ArrayList<NewsItem>();
		Type listType = new TypeToken<LinkedList<NewsItem>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<NewsItem> items = gson.fromJson(jsonData, listType);
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			NewsItem item = (NewsItem) iterator.next();
			list.add(item);
		}
		return list;
	}

	public List<NoticeItem> parseNoticeItemFromJson(String jsonData) {
		List<NoticeItem> list = new ArrayList<NoticeItem>();
		Type listType = new TypeToken<LinkedList<NoticeItem>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<NoticeItem> items = gson.fromJson(jsonData, listType);
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			NoticeItem item = (NoticeItem) iterator.next();
			list.add(item);
		}
		return list;
	}

	public List<LatLng> parsePoilyFromJson(JSONArray jsonData)
			throws JSONException {
		List<LatLng> list = new ArrayList<LatLng>();

		for (int i = 0; i < jsonData.length(); i++) {
			try {
				JSONObject json = jsonData.getJSONObject(i);
				JSONObject jsonObject = json.getJSONObject("start_location");
				String lng = jsonObject.getString("lng");
				String lat = jsonObject.getString("lat");
				LatLng laLng = new LatLng(Double.parseDouble(lat),
						Double.parseDouble(lng));
				list.add(laLng);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<CategoryItem> parseCategoryItemFromJson(String jsonData) {
		List<CategoryItem> list = new ArrayList<CategoryItem>();
		Type listType = new TypeToken<LinkedList<CategoryItem>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<CategoryItem> items = gson.fromJson(jsonData, listType);
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			CategoryItem item = (CategoryItem) iterator.next();
			list.add(item);
		}
		return list;
	}

	public List<Video> parseMovieFromJson(String jsonData) {
		List<Video> list = new ArrayList<Video>();
		Type listType = new TypeToken<LinkedList<Video>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<Video> items = gson.fromJson(jsonData, listType);
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			Video item = (Video) iterator.next();
			list.add(item);
		}
		return list;
	}

	public List<School> parseSchoolFromJson(String fudo, String fudoTitle,
			String fudoKigen, String kabu, String kabuTitle, String kabuKigen,
			String fx, String fxTitle, String fxKigen, String keizai,
			String keizaiTitle, String keizaiKigen, String kaikei,
			String kaikeiTitle, String kaikeiKigen, String nikkei,
			String nikkeiTitle, String nikkeiKigen, String sikumi,
			String sikumiTitle, String sikumiKigen, String konsin,
			String konsinTitle, String konsinKigen) {
		List<School> list = new ArrayList<School>();
		Type listType = new TypeToken<LinkedList<School>>() {
		}.getType();
		Gson gson = new Gson();
		if (!fudo.equals("")) {
			if (!fudo.equals("[]")) {
				LinkedList<School> fudoItems = gson.fromJson(fudo, listType);
				for (Iterator iterator = fudoItems.iterator(); iterator
						.hasNext();) {
					School fudoItem = (School) iterator.next();
					fudoItem.setTitle(fudoTitle);
					fudoItem.setKigen(fudoKigen);
					list.add(fudoItem);
				}
			} else {
				School fudoItem = new School();
				fudoItem.setFlag(true);
				fudoItem.setTitle(fudoTitle);
				fudoItem.setKigen(fudoKigen);
				list.add(fudoItem);
			}
		}
		if (!kabu.equals("")) {
			if (!kabu.equals("[]")) {
				LinkedList<School> kabuItems = gson.fromJson(kabu, listType);
				for (Iterator iterator = kabuItems.iterator(); iterator
						.hasNext();) {
					School kabuItem = (School) iterator.next();
					kabuItem.setTitle(kabuTitle);
					kabuItem.setKigen(kabuKigen);
					list.add(kabuItem);
				}
			} else {
				School kabuItem = new School();
				kabuItem.setFlag(true);
				kabuItem.setTitle(kabuTitle);
				kabuItem.setKigen(kabuKigen);
				list.add(kabuItem);
			}

		}
		if (!fx.equals("")) {
			if (!fx.equals("[]")) {
				LinkedList<School> fxItems = gson.fromJson(fx, listType);
				for (Iterator iterator = fxItems.iterator(); iterator.hasNext();) {
					School fxItem = (School) iterator.next();
					fxItem.setTitle(fxTitle);
					fxItem.setKigen(fxKigen);
					list.add(fxItem);
				}
			} else {
				School fxItem = new School();
				fxItem.setFlag(true);
				fxItem.setTitle(fxTitle);
				fxItem.setKigen(fxKigen);
				list.add(fxItem);
			}

		}
		if (!keizai.equals("")) {
			if (!keizai.equals("[]")) {
				LinkedList<School> keizaiItems = gson
						.fromJson(keizai, listType);
				for (Iterator iterator = keizaiItems.iterator(); iterator
						.hasNext();) {
					School keizaiItem = (School) iterator.next();
					keizaiItem.setTitle(keizaiTitle);
					keizaiItem.setKigen(keizaiKigen);
					list.add(keizaiItem);
				}
			} else {
				School keizaiItem = new School();
				keizaiItem.setFlag(true);
				keizaiItem.setTitle(keizaiTitle);
				keizaiItem.setKigen(keizaiKigen);
				list.add(keizaiItem);
			}

		}
		if (!kaikei.equals("")) {
			if (!kaikei.equals("[]")) {
				LinkedList<School> kaikeiItems = gson
						.fromJson(kaikei, listType);
				for (Iterator iterator = kaikeiItems.iterator(); iterator
						.hasNext();) {
					School kaikeiItem = (School) iterator.next();
					kaikeiItem.setTitle(kaikeiTitle);
					kaikeiItem.setKigen(kaikeiKigen);
					list.add(kaikeiItem);
				}
			} else {
				School kaikeiItem = new School();
				kaikeiItem.setFlag(true);
				kaikeiItem.setTitle(kaikeiTitle);
				kaikeiItem.setKigen(kaikeiKigen);
				list.add(kaikeiItem);
			}

		}
		if (!nikkei.equals("")) {
			if (!nikkei.equals("[]")) {
				LinkedList<School> nikkeiItems = gson
						.fromJson(nikkei, listType);
				for (Iterator iterator = nikkeiItems.iterator(); iterator
						.hasNext();) {
					School nikkeiItem = (School) iterator.next();
					nikkeiItem.setTitle(nikkeiTitle);
					nikkeiItem.setKigen(nikkeiKigen);
					list.add(nikkeiItem);
				}
			} else {
				School nikkeiItem = new School();
				nikkeiItem.setFlag(true);
				nikkeiItem.setTitle(nikkeiTitle);
				nikkeiItem.setKigen(nikkeiKigen);
				list.add(nikkeiItem);
			}

		}
		if (!sikumi.equals("")) {
			if (!sikumi.equals("[]")) {
				LinkedList<School> sikumiItems = gson
						.fromJson(sikumi, listType);
				for (Iterator iterator = sikumiItems.iterator(); iterator
						.hasNext();) {
					School sikumiItem = (School) iterator.next();
					sikumiItem.setTitle(sikumiTitle);
					sikumiItem.setKigen(sikumiKigen);
					list.add(sikumiItem);
				}
			} else {
				School sikumiItem = new School();
				sikumiItem.setFlag(true);
				sikumiItem.setTitle(sikumiTitle);
				sikumiItem.setKigen(sikumiKigen);
				list.add(sikumiItem);
			}

		}
		if (!konsin.equals("")) {
			if (!konsin.equals("[]")) {
				LinkedList<School> konsinItems = gson
						.fromJson(konsin, listType);
				for (Iterator iterator = konsinItems.iterator(); iterator
						.hasNext();) {
					School konsinItem = (School) iterator.next();
					konsinItem.setTitle(konsinTitle);
					konsinItem.setKigen(konsinKigen);
					list.add(konsinItem);
				}
			} else {
				School konsinItem = new School();
				konsinItem.setFlag(true);
				konsinItem.setTitle(konsinTitle);
				konsinItem.setKigen(konsinKigen);
				list.add(konsinItem);
			}

		}

		return list;
	}
	public List<HistoryItem> parseHistoryItemFromJson(String jsonData) {
		List<HistoryItem> list = new ArrayList<HistoryItem>();
		Type listType = new TypeToken<LinkedList<HistoryItem>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<HistoryItem> items = gson.fromJson(jsonData, listType);
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			HistoryItem item = (HistoryItem) iterator.next();
			list.add(item);
		}
		return list;
	}
}
