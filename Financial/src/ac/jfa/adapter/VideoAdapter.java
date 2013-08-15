package ac.jfa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.jfa.AnimActivity;
import ac.jfa.R;
import ac.jfa.VedioPlayActivity;
import ac.jfa.modal.Video;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class VideoAdapter extends BaseAdapter{

	private List<Video>info = null;
	private Map<Integer,View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	
	public VideoAdapter(List<Video> info,Context context){
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
			rowView = layoutInflater.inflate(R.layout.vedioitem, null);
//			VideoView videoView  = (VideoView)rowView.findViewById(R.id.videoView);
//	        videoView.setMediaController(new MediaController(context));  
//	        Uri uri = Uri.parse("http://iphone.moneylog.jp/school_jukousei.mp4"); 
//	        videoView.setVideoURI(uri);  
////	        videoView.start();  
			ImageView imageView = (ImageView)rowView.findViewById(R.id.videoView);
			imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(context, VedioPlayActivity.class);
					intent.putExtra("url", "http://iphone.moneylog.jp/school_jukousei.mp4");
					context.startActivity(intent);
				}
			});
			rowViews.put(position, rowView);
		}
		return rowView;
	}
}
