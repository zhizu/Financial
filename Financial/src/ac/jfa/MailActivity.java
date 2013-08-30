package ac.jfa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.util.JsonUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MailActivity extends Activity {

	private Button button = null;
	private ImageButton imageButton = null;
	private EditText mail, content = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mail);

		mail = (EditText) findViewById(R.id.mail);
		content = (EditText) findViewById(R.id.content);

		button = (Button) findViewById(R.id.decide);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!mail.getText().toString().trim().equals("")) {
					if (!content.getText().toString().trim().equals("")) {
						AsyncHttpClient client = new AsyncHttpClient();
						client.get(MailActivity.this,
								"https://www.f-academy.jp/sp/app/api/user_inquery.cgi?mail="
										+ mail.getText().toString().trim()
										+ "&text="
										+ content.getText().toString().trim(),
								new JsonHttpResponseHandler() {

									@Override
									public void onSuccess(JSONObject jsonObject) {
										// TODO Auto-generated method stub
										try {
											String status = jsonObject
													.getString("status");
											if (status.equals("success")) {
												Toast.makeText(MailActivity.this, "送信完了", Toast.LENGTH_SHORT).show();
											}else{
												Toast.makeText(MailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										super.onSuccess(jsonObject);
									}

								});
					}else{
						Toast.makeText(MailActivity.this, "内容を入力してください！", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(MailActivity.this, "メールアドレスを入力してください！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		imageButton = (ImageButton) findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MailActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(0, R.anim.out_to_right);
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
