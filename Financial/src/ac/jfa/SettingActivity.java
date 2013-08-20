package ac.jfa;

import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends Activity{

	private ImageView pass,push = null;
	private ImageButton imageButton = null;
	private SharedPreferences preferences = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting);
		init();
		pass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				preferences = getSharedPreferences("setting", MODE_WORLD_READABLE);
				if(preferences.getString("pass_code", "").equals("null")){
					Intent intent = new Intent();
					intent.setClass(SettingActivity.this, PassActivity.class);
					intent.putExtra("from", "set");
					startActivityForResult(intent, 1);
				}else{
					preferences.edit().putString("pass_code", "null").commit();
					pass.setImageResource(R.drawable.butn_close);
					Toast.makeText(SettingActivity.this, preferences.getString("pass_code", ""), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}

	public void init(){
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,R.anim.out_to_right);
			}
		});
		preferences = getSharedPreferences("count_2", MODE_WORLD_READABLE);
		String count_2 = preferences.getString("count_2", "0");
		if(count_2.equals("0")){
			preferences = getSharedPreferences("setting", MODE_WORLD_READABLE);
			preferences.edit().putString("push_flag", "1").commit();
			preferences.edit().putString("pass_code", "null").commit();
			preferences = getSharedPreferences("count_2", MODE_WORLD_READABLE);
			preferences.edit().putString("count_2", "1").commit();
		}
		pass = (ImageView)findViewById(R.id.pass);
		push = (ImageView)findViewById(R.id.push);
		
		preferences = getSharedPreferences("setting", MODE_WORLD_READABLE);
		if(preferences.getString("push_flag", "").equals("1")){
			push.setImageResource(R.drawable.butn_close);
		}else{
			push.setImageResource(R.drawable.butn_open);
		}
		if(preferences.getString("pass_code", "").equals("null")){
			pass.setImageResource(R.drawable.butn_close);
		}else{
			pass.setImageResource(R.drawable.butn_open);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 1){
			if(resultCode == 1){
				String password = data.getStringExtra("pass");
			    preferences = getSharedPreferences("setting", MODE_WORLD_READABLE);
			    preferences.edit().putString("pass_code", password).commit();
				pass.setImageResource(R.drawable.butn_open);
				Toast.makeText(SettingActivity.this, preferences.getString("pass_code", ""), Toast.LENGTH_SHORT).show();
			}
		    
		}
		
		
		super.onActivityResult(requestCode, resultCode, data);
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
