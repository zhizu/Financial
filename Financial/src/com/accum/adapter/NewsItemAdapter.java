package com.accum.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accum.financial.R;
import com.accum.modal.NewsItem;

public class NewsItemAdapter extends BaseAdapter{

	private List<NewsItem>info = null;
	private Map<Integer,View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	
	public NewsItemAdapter(List<NewsItem> info,Context context){
		this.info = info;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return info.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return info.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = rowViews.get(position);
		if(rowView == null){
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			rowView = layoutInflater.inflate(R.layout.newsitem, null);
//			TextView textView = (TextView)rowView.findViewById(R.id.content_title);
//			textView.setText("asdddddddddddddd");
			rowViews.put(position, rowView);
		}
		return rowView;
	}

}
