package ac.jfa;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;

import ac.jfa.database.DBHelper;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.modal.Information;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

import com.google.android.gcm.GCMRegistrar;

public class StartActivity extends Activity {

	private SharedPreferences preferences = null;
	private SQLiteDatabase db = null;
	private DBHelper helper = null;
	private String password,phone_no,phone_key,ko_no,device_type,push_flag,regId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
		  GCMRegistrar.register(this, "120385757428");
		}
		
		preferences = getSharedPreferences("setting", MODE_WORLD_READABLE);
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
			information.setKo_no("");
			information.setDevice_type("2");
			information.setToken(regId);
			information.setPush_flag("0");
			information.setPass_code("");
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
			db = StartActivity.this.openOrCreateDatabase("inform",
					Context.MODE_PRIVATE, null);
			Cursor cursor = db.rawQuery("select * from information", null);
			while (cursor.moveToNext()) {
				password = cursor.getString(cursor.getColumnIndex("pass_code"));
				ko_no = cursor.getString(cursor.getColumnIndex("ko_no"));
				phone_no = cursor.getString(cursor.getColumnIndex("phone_no"));
				phone_key = cursor.getString(cursor.getColumnIndex("phone_key"));
				device_type = cursor.getString(cursor.getColumnIndex("device_type"));
				push_flag = cursor.getString(cursor.getColumnIndex("push_flag"));
			}
			cursor.close();
			db.close();
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(this,
					"https://www.f-academy.jp/sp/app/api/device_json.cgi?phone_no="
							+ phone_no + "&phone_key="
							+ phone_key + "&device_type="
							+ device_type + "&push_flag="
							+ push_flag + "&ko_no="
							+ ko_no + "&pass_code="
							+ password + "&token="
							+ regId,
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(JSONObject jsonObject) {
							// TODO Auto-generated method stub
							super.onSuccess(jsonObject);
						}

					});
			if (!password.equals("")) {
				Intent intent = new Intent();
				intent.setClass(this, PassActivity.class);
				intent.putExtra("from", "init");
				intent.putExtra("pass", password);
				this.startActivity(intent);
				this.finish();
			} else {
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				this.startActivity(intent);
				this.finish();
			}
		}
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
