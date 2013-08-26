package ac.jfa;

import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.adapter.NewsItemAdapter;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.modal.UserItem;
import ac.jfa.util.JsonUtils;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity {
	private UserItem userItem = null;
	private ImageButton imageButton = null;
	private RelativeLayout changerpassLayout, ConfirmLayout, VedioLayout,
			CardLayout = null;
	private TextView nameText, monText, pointText = null;
	private ImageView point = null;
	private Button logout_btn = null;
	private String phone_no,phone_key,device_type,token,push_flag,pass_code = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user);
		logout_btn = (Button) findViewById(R.id.logout_btn);
		logout_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SQLiteDatabase db = UserActivity.this.openOrCreateDatabase(
						"inform", Context.MODE_PRIVATE, null);
				db.delete("user_inform", null, null);

				Cursor cursor = db.rawQuery("select * from information", null);

				if (cursor.moveToNext()) {
					phone_no = cursor.getString(cursor
							.getColumnIndex("phone_no"));
					phone_key = cursor.getString(cursor
							.getColumnIndex("phone_key"));
					device_type = cursor.getString(cursor
							.getColumnIndex("device_type"));
					token = cursor.getString(cursor
							.getColumnIndex("token"));
					push_flag = cursor.getString(cursor
							.getColumnIndex("push_flag"));
					pass_code = cursor.getString(cursor
							.getColumnIndex("pass_code"));
				}
				ContentValues cv = new ContentValues();  
		        cv.put("ko_no", "null");  
		        String[] args = {String.valueOf(phone_no)};  
		        db.update("information", cv, "phone_no=?",args); 
				cursor.close();
				db.close();
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(UserActivity.this,
						"https://www.f-academy.jp/sp/app/api/user_login.cgi?phone_no="
								+ phone_no + "&phone_key=" + phone_key
								+ "&logout=1", new JsonHttpResponseHandler() {

							@Override
							public void onSuccess(JSONObject jsonObject) {
								// TODO Auto-generated method stub
								try {
									String status = jsonObject
											.getString("status");
									if (status.equals("success")) {
										AsyncHttpClient client = new AsyncHttpClient();
										client.get(UserActivity.this,
												"https://www.f-academy.jp/sp/app/api/device_json.cgi?phone_no="
														+ phone_no + "&phone_key=" + phone_key
														+ "&device_type=" + device_type + "&token=" + token + "&push_flag=" + push_flag + "&pass_code=" + pass_code, new JsonHttpResponseHandler() {

													@Override
													public void onSuccess(JSONObject jsonObject) {
														// TODO Auto-generated method stub
														try {
															String status = jsonObject
																	.getString("status");
															if (status.equals("success")) {
																
																Intent intent = new Intent();
																intent.setClass(UserActivity.this,
																		MainActivity.class);
																startActivity(intent);
																overridePendingTransition(0,
																		R.anim.out_to_right);
																finish();
															}else{
																Toast.makeText(UserActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
															}
														} catch (JSONException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}

														super.onSuccess(jsonObject);
													}

												});
									}else{
										Toast.makeText(UserActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								super.onSuccess(jsonObject);
							}

						});

			}
		});
		Intent intent = getIntent();
		userItem = (UserItem) intent.getSerializableExtra("userItem");
		nameText = (TextView) findViewById(R.id.nameText);
		monText = (TextView) findViewById(R.id.monText);
		pointText = (TextView) findViewById(R.id.pointText);
		point = (ImageView) findViewById(R.id.point);

		nameText.setText(userItem.getKo_name1() + " 様");
		DecimalFormat df = new DecimalFormat("##,###,###");
		monText.setText(df.format(Integer.parseInt(userItem.getKo_point()))
				+ "pt(" + df.format(Integer.parseInt(userItem.getKo_point()))
				+ ")");
		pointText.setText("現在" + userItem.getKo_tani() + "単位取得");
		int tani = Integer.parseInt(userItem.getKo_tani());
		if (tani > 0 && tani < 23) {
			point.setImageResource(R.drawable.column_status_1);
		} else if (tani > 23 && tani < 45) {
			point.setImageResource(R.drawable.column_status_2);
		} else if (tani > 46 && tani < 71) {
			point.setImageResource(R.drawable.column_status_3);
		} else if (tani > 72) {
			point.setImageResource(R.drawable.column_status_4);
		}

		imageButton = (ImageButton) findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(0, R.anim.out_to_right);
				finish();
			}
		});

		changerpassLayout = (RelativeLayout) findViewById(R.id.changerpassLayout);
		changerpassLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, RegistActivity.class);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		ConfirmLayout = (RelativeLayout) findViewById(R.id.ConfirmLayout);
		ConfirmLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, HistoryActivity.class);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		VedioLayout = (RelativeLayout) findViewById(R.id.VedioLayout);
		VedioLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("dl_sid", userItem.getDl_sid());
				intent.setClass(UserActivity.this, CategoryActivity.class);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		CardLayout = (RelativeLayout) findViewById(R.id.CardLayout);
		CardLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, CardActivity.class);
				intent.putExtra("user", userItem);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
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
