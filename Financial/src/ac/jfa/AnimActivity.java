package ac.jfa;

import java.util.ArrayList;
import java.util.List;

import ac.jfa.adapter.VideoAdapter;
import ac.jfa.modal.Video;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;


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
//					Uri uri = Uri.parse("http://iphone.moneylog.jp/school_jukousei.mp4");	
//					//调用系统自带的播放器
//						Intent intent = new Intent(Intent.ACTION_VIEW);
//						intent.setDataAndType(uri, "video/mp4");
//						startActivity(intent);
					
			
					
					finish();
					overridePendingTransition(0,R.anim.out_to_right);
				}
			});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
