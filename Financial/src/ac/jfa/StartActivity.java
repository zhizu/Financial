package ac.jfa;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import ac.jfa.database.DBHelper;
import ac.jfa.modal.Information;
import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class StartActivity extends Activity{

	private SharedPreferences preferences = null;
	private SQLiteDatabase db = null;
	private DBHelper helper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);
		helper = new DBHelper(this, "inform");
		if (count == 0) {
			
			db = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			Information information = new Information();
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			information.setPhone_no(tm.getDeviceId());
			information
					.setPhone_key(RandomStringUtils
							.random(16,
									"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			information.setKo_no("null");
			information.setDevice_type("2");
			information.setToken("null");
			information.setPush_flag("0");
			information.setPass_code("null");
			values.put("phone_no", information.getPhone_no());
			values.put("phone_key", information.getPhone_key());
			values.put("ko_no", information.getKo_no());
			values.put("device_type", information.getDevice_type());
			values.put("token", information.getToken());
			values.put("push_flag", information.getPush_flag());
			values.put("pass_code", information.getPass_code());
			db.insert("information", null, values);
			 db.close();

			AsyncHttpClient client = new AsyncHttpClient();
			client.get(this,
					"https://www.f-academy.jp/sp/app/api/device_json.cgi?phone_no="
							+ information.getPhone_no() + "&phone_key="
							+ information.getPhone_key() + "&device_type="
							+ information.getDevice_type() + "&push_flag="
							+ information.getPush_flag(),
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(JSONObject jsonObject) {
							// TODO Auto-generated method stub
							System.out.println("yangxu--->" + jsonObject.toString());
							super.onSuccess(jsonObject);
						}

					});
			
			Editor edit = preferences.edit();
			edit.putInt("count", ++count);
			edit.commit();
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			this.finish();
		} else {
			preferences = getSharedPreferences("setting", MODE_WORLD_READABLE);
			if(!preferences.getString("pass_code", "").equals("null")){
				Intent intent = new Intent();
				intent.setClass(this, PassActivity.class);
				intent.putExtra("from", "init");
				this.startActivity(intent);
				this.finish();
			}else{
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				this.startActivity(intent);
				this.finish();
			}
//			db = helper.getReadableDatabase();
//			Cursor cursor = db.rawQuery("select * from information", null);
//			while (cursor.moveToNext()) {
//				String name = cursor.getString(cursor
//						.getColumnIndex("phone_no"));
//				System.out.println("yangxuquery--->" + name);
//			}
//			 cursor.close();
//			 db.close();
		}
	}
	
}
