package com.accum.financial;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.accum.adapter.VideoAdapter;
import com.accum.modal.Video;

public class AnimActivity extends ListActivity{

	private VideoAdapter adapter = null;
	private ListView listView = null;
//	private VideoView videoView = null;
	private ImageButton imageButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vedio);
		listView = getListView();
		  listView.setDividerHeight(0);
	        listView.setCacheColorHint(Color.TRANSPARENT);
	        Video item = new Video();
	        Video item2 = new Video();
	        Video item3 = new Video();
	        List<Video>info = new ArrayList<Video>();
	        info.add(item);
	        info.add(item2);
	        info.add(item3);
	        adapter = new VideoAdapter(info, AnimActivity.this);
	        listView.setAdapter(adapter);
	        imageButton = (ImageButton)findViewById(R.id.img_search);
			imageButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					overridePendingTransition(0,R.anim.out_to_right);
				}
			});
	}

}
