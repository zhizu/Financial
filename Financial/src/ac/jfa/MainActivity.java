package ac.jfa;

import java.io.Serializable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.adapter.NewsItemAdapter;
import ac.jfa.modal.NewsItem;
import ac.jfa.modal.NoticeItem;
import ac.jfa.util.JsonUtils;
import ac.jfa.xListView.XListView;
import ac.jfa.xListView.XListView.IXListViewListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity implements IXListViewListener {

	private TextView textCommunication, textMoneyLecture, textSchool,
			textSeminar, textfeed, textVip,textNews,textQuestion,textSetting,textReview = null;
	private SlidingMenu menu = null;
	private ImageView menuButton = null;
	private XListView listView = null;
	private List<NewsItem> myListNewsItem;
	private NewsItemAdapter adapter = null;
	private LinearLayout linearfeed = null;
	private NewsItem currentItem = null;
	private Handler mHandler = null;
	private int start = 1;
	private SQLiteDatabase db = null;

	private List<NoticeItem> noticeItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set the content view
		setContentView(R.layout.main);
		LinearInit();
		getNews();
		listView = (XListView) findViewById(R.id.xListView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setDividerHeight(0);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);

		listView.setXListViewListener(this);
		mHandler = new Handler();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				currentItem = (NewsItem) listView.getItemAtPosition(position);
				// currentItem.getContent_url();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url", currentItem.getContent_url());
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});

		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidth(50);
		menu.setBehindOffset(110);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.behind_view);

		textCommunication = (TextView) findViewById(R.id.textCommunication);
		textCommunication.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url",
						"http://www.f-academy.jp/course_index.html");

				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		textMoneyLecture = (TextView) findViewById(R.id.textMoneyLecture);
		textMoneyLecture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url",
						"http://www.f-academy.jp/sp/school/kyouyousemi.html");

				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		textSchool = (TextView) findViewById(R.id.textSchool);
		textSchool.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url",
						"http://www.f-academy.jp/school_index.html");

				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		textSeminar = (TextView) findViewById(R.id.textSeminar);
		textSeminar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url",
						"http://www.f-academy.jp/seminar_index.html");

				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		menuButton = (ImageView) findViewById(R.id.newsfeed_flip);
		menuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
			}
		});

		textfeed = (TextView) findViewById(R.id.textfeed);
		textfeed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				linearfeed.setVisibility(View.VISIBLE);

				// AsyncHttpClient client = new AsyncHttpClient();
				// client.get(MainActivity.this,
				// "https://www.f-academy.jp/sp/app/api/feed_json.cgi", new
				// JsonHttpResponseHandler(){
				//
				//
				// @Override
				// public void onSuccess(JSONObject jsonObject) {
				// // TODO Auto-generated method stub
				// try {
				// JSONArray array = jsonObject.getJSONArray("feed_data");
				// JsonUtils jsonUtiles = new JsonUtils();
				// jsonUtiles.parseItemFromJson(array.toString());
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				//
				// super.onSuccess(jsonObject);
				// }
				//
				// });
			}
		});

		textVip = (TextView) findViewById(R.id.textVip);
		textVip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, LoginActivity.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		
		textNews = (TextView)findViewById(R.id.textNews);
		getNotices();
		
		textNews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				Intent intent = new Intent();
				intent.putExtra("noticeItems", (Serializable)noticeItems);
				intent.setClass(MainActivity.this, NoticeActivity.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		
		textQuestion = (TextView)findViewById(R.id.textQuestion);
		textQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MailActivity.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		textSetting = (TextView)findViewById(R.id.textSetting);
		textSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingActivity.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		
		textReview = (TextView)findViewById(R.id.textReview);
		textReview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setData(Uri.parse("market://details?id=com.bookeep"));
				intent.setAction(Intent.ACTION_VIEW);
				MainActivity.this.startActivity(intent);
			}
		});
		
	}

	public void LinearInit() {
		linearfeed = (LinearLayout) findViewById(R.id.linearfeed);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getNews();
				listView.stopRefresh();
				listView.stopLoadMore();

			}
		}, 3000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				++start;
				// getNews(start);
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(MainActivity.this,
						"https://www.f-academy.jp/sp/app/api/feed_json.cgi?page_no="
								+ start, new JsonHttpResponseHandler() {

							@Override
							public void onSuccess(JSONObject jsonObject) {
								// TODO Auto-generated method stub
								try {
									JSONArray array = jsonObject
											.getJSONArray("feed_data");
									JsonUtils jsonUtiles = new JsonUtils();
									myListNewsItem.addAll(jsonUtiles
											.parseItemFromJson(array.toString()));
									adapter.notifyDataSetChanged();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								super.onSuccess(jsonObject);
							}

						});
				listView.stopRefresh();
				listView.stopLoadMore();
			}
		}, 3000);
	}
	
	
	
	public void getNews() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(MainActivity.this,
				"https://www.f-academy.jp/sp/app/api/feed_json.cgi?page_no=1",
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {
						// TODO Auto-generated method stub
						try {
							JSONArray array = jsonObject
									.getJSONArray("feed_data");
							JsonUtils jsonUtiles = new JsonUtils();
							myListNewsItem = jsonUtiles.parseItemFromJson(array
									.toString());
							adapter = new NewsItemAdapter(myListNewsItem,
									MainActivity.this);
							listView.setAdapter(adapter);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						super.onSuccess(jsonObject);
					}

				});
	}
	
	private void getNotices() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(MainActivity.this,
				"https://www.f-academy.jp/sp/app/api/news_json.cgi",
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {
						// TODO Auto-generated method stub
						try {
							JSONArray array = jsonObject
									.getJSONArray("news_data");
							JsonUtils jsonUtiles = new JsonUtils();
							noticeItems = jsonUtiles.parseNoticeItemFromJson(array
									.toString());
							int number = noticeItems.size();
							db = MainActivity.this.openOrCreateDatabase("inform", Context.MODE_PRIVATE, null);
							for(int i=0; i<noticeItems.size(); i++){
								Cursor cursor = db.rawQuery("select * from notice where news_id="+noticeItems.get(i).getNews_id().toString(), null);
								
								if(cursor.moveToNext()){
									number--;
									cursor.close();
								}
							}
							db.close();
							textNews.setText("¤ªÖª¤é¤»                                    " + number);
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
