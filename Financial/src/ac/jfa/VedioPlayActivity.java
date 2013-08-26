package ac.jfa;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VedioPlayActivity extends Activity{
	private  VideoView mVideoView;
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vedioview);
		
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		mVideoView.setVideoPath(url);
		MediaController controller = new MediaController(this);
		mVideoView.setMediaController(controller);
		mVideoView.start(); 
		mVideoView.requestFocus();
	} 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			moveTaskToBack(true);
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mVideoView.pause();
		}
		return super.onKeyDown(keyCode, event);
	}  
}
