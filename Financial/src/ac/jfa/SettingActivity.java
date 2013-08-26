package ac.jfa;

import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
	private SQLiteDatabase db = null;
	private String password = null;
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
				db = SettingActivity.this.openOrCreateDatabase("inform", Context.MODE_PRIVATE, null);
				Cursor cursor = db.rawQuery("select * from information", null);
				while (cursor.moveToNext()) {
					password = cursor.getString(cursor
							.getColumnIndex("pass_code"));
				}
				
				if(password.equals("")){
					Intent intent = new Intent();
					intent.setClass(SettingActivity.this, PassActivity.class);
					intent.putExtra("from", "set");
					startActivityForResult(intent, 1);
				}else{
					TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					ContentValues cv = new ContentValues();  
			        cv.put("pass_code", "");  
			        String[] args = {String.valueOf(tm.getDeviceId())};  
			        db.update("information", cv, "phone_no=?",args); 
					pass.setImageResource(R.drawable.butn_close);
				}
				cursor.close();
				db.close();
			}
		});
		
	}

	public void init(){
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, MainActivity.class);
				startActivity(intent);				
				overridePendingTransition(0,R.anim.out_to_right);
				finish();
			}
		});
		pass = (ImageView)findViewById(R.id.pass);
		push = (ImageView)findViewById(R.id.push);
		
		db = SettingActivity.this.openOrCreateDatabase("inform", Context.MODE_PRIVATE, null);
		Cursor cursor = db.rawQuery("select * from information", null);
		while (cursor.moveToNext()) {
			password = cursor.getString(cursor
					.getColumnIndex("pass_code"));
		}
//		if(preferences.getString("push_flag", "").equals("1")){
//			push.setImageResource(R.drawable.butn_close);
//		}else{
//			push.setImageResource(R.drawable.butn_open);
//		}
		if(password.equals("")){
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
				db = SettingActivity.this.openOrCreateDatabase("inform", Context.MODE_PRIVATE, null);
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				ContentValues cv = new ContentValues();  
		        cv.put("pass_code", password);  
		        String[] args = {String.valueOf(tm.getDeviceId())};  
		        db.update("information", cv, "phone_no=?",args); 
				pass.setImageResource(R.drawable.butn_open);
				db.close();
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
