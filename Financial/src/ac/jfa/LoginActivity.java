package ac.jfa;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import ac.jfa.database.DBHelper;
import ac.jfa.modal.UserItem;
import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity{
	private ImageButton imageButton = null;
	private Button forget_passwd,login_btn = null; 
	private EditText login_user_edit,login_passwd_edit = null;
	private SQLiteDatabase db = null;
	private DBHelper helper = null;
	private String phone_no,phone_key;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		login_user_edit = (EditText)findViewById(R.id.login_user_edit);
		login_passwd_edit = (EditText)findViewById(R.id.login_passwd_edit);
		
		forget_passwd = (Button)findViewById(R.id.forget_passwd);
		forget_passwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		forget_passwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("url",
						"https://www.f-academy.jp/pass.html");
				intent.setClass(LoginActivity.this, KouzaActivity.class);
				LoginActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,R.anim.out_to_right);
			}
		});
		login_btn = (Button)findViewById(R.id.login_btn);
		login_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(login_user_edit.getText().toString().trim().equals("") || login_passwd_edit.getText().toString().trim().equals("")){
					Toast.makeText(LoginActivity.this, "メールとパスワードを全部入力してください！", Toast.LENGTH_SHORT).show();
				}else{
					 String format = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?";
					if(!login_user_edit.getText().toString().trim().matches(format)){
						Toast.makeText(LoginActivity.this, "正確なメールを入力してください！", Toast.LENGTH_SHORT).show();
					}else{
						helper = new DBHelper(LoginActivity.this, "inform");
						db = helper.getWritableDatabase();
						Cursor cursor = db.rawQuery("select * from information", null);
						while (cursor.moveToNext()) {
							phone_no = cursor.getString(cursor
									.getColumnIndex("phone_no"));
							phone_key = cursor.getString(cursor
									.getColumnIndex("phone_key"));
						}
						cursor.close();
						db.close();
						AsyncHttpClient client = new AsyncHttpClient();
						client.get(LoginActivity.this,
								"https://www.f-academy.jp/sp/app/api/user_login.cgi?phone_no="
										+ phone_no + "&phone_key="
										+ phone_key + "&id="
										+ login_user_edit.getText().toString() + "&pass="
										+ login_passwd_edit.getText().toString(),
								new JsonHttpResponseHandler() {

									@Override
									public void onSuccess(JSONObject jsonObject) {
										// TODO Auto-generated method stub
										try {
											String status = jsonObject.getString("status");
											if(status.equals("success")){
												JSONObject json = (JSONObject) jsonObject.get("user_data");
												UserItem userItem = new UserItem();
												userItem.setSid(json.getString("sid"));
												userItem.setKo_tani(json.getString("ko_tani"));
												userItem.setKo_point(json.getString("ko_point"));
												userItem.setKo_no(json.getString("ko_no"));
												userItem.setKo_mail(json.getString("ko_mail"));
												userItem.setKo_name1(json.getString("ko_name1"));
												userItem.setKo_name2(json.getString("ko_name2"));
												Intent intent = new Intent();
												intent.setClass(LoginActivity.this, UserActivity.class);
												intent.putExtra("userItem", userItem);
												LoginActivity.this.startActivity(intent);
												overridePendingTransition(R.anim.in_from_right, R.anim.out);
											}else{
												Toast.makeText(LoginActivity.this,jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										super.onSuccess(jsonObject);
									}

								});

					}
					
					
				}
				
			}
		});
	}
}
