package ac.jfa;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.adapter.HistoryAdapter;
import ac.jfa.adapter.SchoolAdapter;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.modal.HistoryItem;
import ac.jfa.modal.School;
import ac.jfa.util.JsonUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class HistoryActivity extends Activity {
	private ImageButton imageButton = null;
	private ListView schoolList, historyList = null;
	private SchoolAdapter adapter = null;
	private HistoryAdapter historyAdapter = null;
	private String sid, calendarUrl, dl_sid;
	private ImageView calendar = null;
	private ProgressBar progress = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history);
		progress = (ProgressBar)findViewById(R.id.progress);
		progress.setVisibility(View.VISIBLE);
		Intent intent = getIntent();
		sid = intent.getStringExtra("sid");
		dl_sid = intent.getStringExtra("dl_sid");
		initList();
		initData();

		
		imageButton = (ImageButton) findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0, R.anim.out_to_right);
			}
		});
	}

	private void initList() {
		schoolList = (ListView) findViewById(R.id.schoolList);
		historyList = (ListView) findViewById(R.id.historyList);
		schoolList.setCacheColorHint(Color.TRANSPARENT);
		historyList.setDividerHeight(0);
		historyList.setCacheColorHint(Color.TRANSPARENT);
		schoolList.setDividerHeight(0);
		calendar = (ImageView) findViewById(R.id.calendar);
	}

	private void initData() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(HistoryActivity.this,
				"https://www.f-academy.jp/sp/app/api/mypage.cgi?sid=" + sid,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {
						// TODO Auto-generated method stub

						try {
							JSONObject school_data = jsonObject
									.getJSONObject("school_data");
							calendarUrl = school_data.getString("calendar");
							calendar.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									intent.putExtra("url",
											"http://docs.google.com/gview?embedded=true&url="
													+ calendarUrl);
									intent.putExtra("from", "history");
									intent.setClass(HistoryActivity.this,
											KouzaActivity.class);
									startActivity(intent);
									overridePendingTransition(
											R.anim.in_from_right, R.anim.out);
								}
							});

							String fudoData = "";
							String fudoTitle = "";
							String fudoKigen = "";
							String keizaiData = "";
							String keizaiTitle = "";
							String keizaiKigen = "";
							String fxData = "";
							String fxTitle = "";
							String fxKigen = "";
							String sikumiData = "";
							String sikumiTitle = "";
							String sikumiKigen = "";
							String nikkeiData = "";
							String nikkeiTitle = "";
							String nikkeiKigen = "";
							String kabuData = "";
							String kabuTitle = "";
							String kabuKigen = "";
							String kaikeiData = "";
							String kaikeiTitle = "";
							String kaikeiKigen = "";
							String konsinData = "";
							String konsinTitle = "";
							String konsinKigen = "";

							JSONObject fudo = school_data.getJSONObject("fudo");
							JSONObject keizai = school_data
									.getJSONObject("keizai");
							JSONObject fx = school_data.getJSONObject("fx");
							JSONObject sikumi = school_data
									.getJSONObject("sikumi");
							JSONObject nikkei = school_data
									.getJSONObject("nikkei");
							JSONObject kabu = school_data.getJSONObject("kabu");
							JSONObject kaikei = school_data
									.getJSONObject("kaikei");
							JSONObject konsin = school_data
									.getJSONObject("konsin");
							if (!fudo.toString().equals("{}")) {
								fudoData = fudo.getJSONArray("data").toString();
								fudoTitle = fudo.getString("title");
								fudoKigen = fudo.getString("kigen");
							}

							if (!keizai.toString().equals("{}")) {
								keizaiData = keizai.getJSONArray("data")
										.toString();
								keizaiTitle = keizai.getString("title");
								keizaiKigen = keizai.getString("kigen");
							}

							if (!fx.toString().equals("{}")) {
								fxData = fx.getJSONArray("data").toString();
								fxTitle = fx.getString("title");
								fxKigen = fx.getString("kigen");
							}

							if (!sikumi.toString().equals("{}")) {
								sikumiData = sikumi.getJSONArray("data")
										.toString();
								sikumiTitle = sikumi.getString("title");
								sikumiKigen = sikumi.getString("kigen");
							}

							if (!nikkei.toString().equals("{}")) {
								nikkeiData = nikkei.getJSONArray("data")
										.toString();
								nikkeiTitle = nikkei.getString("title");
								nikkeiKigen = nikkei.getString("kigen");
							}

							if (!kabu.toString().equals("{}")) {
								kabuData = kabu.getJSONArray("data").toString();
								kabuTitle = kabu.getString("title");
								kabuKigen = kabu.getString("kigen");
							}

							if (!kaikei.toString().equals("{}")) {
								kaikeiData = kaikei.getJSONArray("data")
										.toString();
								kaikeiTitle = kaikei.getString("title");
								kaikeiKigen = kaikei.getString("kigen");
							}

							if (!konsin.toString().equals("{}")) {
								konsinData = konsin.getJSONArray("data")
										.toString();
								konsinTitle = konsin.getString("title");
								konsinKigen = konsin.getString("kigen");
							}

							JsonUtils jsonUtils = new JsonUtils();
							List<School> schoolInfos = new ArrayList<School>();

							schoolInfos = jsonUtils.parseSchoolFromJson(fudoData,
									fudoTitle, fudoKigen, kabuData, kabuTitle,
									kabuKigen, fxData, fxTitle, fxKigen,
									keizaiData, keizaiTitle, keizaiKigen,
									kaikeiData, kaikeiTitle, kaikeiKigen,
									nikkeiData, nikkeiTitle, nikkeiKigen,
									sikumiData, sikumiTitle, sikumiKigen,
									konsinData, konsinTitle, konsinKigen);
							adapter = new SchoolAdapter(schoolInfos,
									HistoryActivity.this, dl_sid,sid,progress);
							schoolList.setAdapter(adapter);
							
							
							JSONArray history_data = jsonObject.getJSONArray("history_data");
							List<HistoryItem> historyInfos = new ArrayList<HistoryItem>();
							historyInfos = jsonUtils.parseHistoryItemFromJson(history_data.toString());
							historyAdapter = new HistoryAdapter(historyInfos, HistoryActivity.this);
							historyList.setAdapter(historyAdapter);
							progress.setVisibility(View.GONE);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						super.onSuccess(jsonObject);
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
