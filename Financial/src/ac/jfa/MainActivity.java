package ac.jfa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.adapter.NewsItemAdapter;
import ac.jfa.adapter.RelateAdapter;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.modal.NewsItem;
import ac.jfa.modal.NoticeItem;
import ac.jfa.modal.RelateItem;
import ac.jfa.modal.UserItem;
import ac.jfa.util.JsonUtils;
import ac.jfa.xListView.XListView;
import ac.jfa.xListView.XListView.IXListViewListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends Activity implements IXListViewListener {

	private TextView textCommunication, textMoneyLecture, textSchool,
			textSeminar, textfeed, textVip, textQuestion, textSetting,
			textReview, notice,textSite = null;
	private RelativeLayout textNews = null;
	private SlidingMenu menu = null;
	private ImageView menuButton = null;
	private XListView listView = null;
	private ListView relateListView = null;
	private List<NewsItem> myListNewsItem;
	private NewsItemAdapter adapter = null;
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
		setContentView(R.layout.main);
		
		getNews();
		
		initRelateListView();
		
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
				intent.putExtra("from", "main");
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
		init();

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
							noticeItems = jsonUtiles
									.parseNoticeItemFromJson(array.toString());
							int number = noticeItems.size();
							db = MainActivity.this.openOrCreateDatabase(
									"inform", Context.MODE_PRIVATE, null);
							for (int i = 0; i < noticeItems.size(); i++) {
								Cursor cursor = db.rawQuery(
										"select * from notice where news_id="
												+ noticeItems.get(i)
														.getNews_id()
														.toString(), null);

								if (cursor.moveToNext()) {
									number--;
									cursor.close();
								}
							}
							db.close();
							if (number == 0) {
								notice.setVisibility(View.GONE);
							} else {
								notice.setText("    " + String.valueOf(number));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						super.onSuccess(jsonObject);
					}

				});
	}

	private void init(){
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
				intent.putExtra("from", "main");
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
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
				intent.putExtra("from", "main");
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
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
				intent.putExtra("from", "main");
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
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
				intent.putExtra("from", "main");
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
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
				getNews();
				relateListView.setVisibility(View.GONE);
		        listView.setVisibility(View.VISIBLE);
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
				Intent intent = new Intent();
				db = MainActivity.this.openOrCreateDatabase("inform",
						Context.MODE_PRIVATE, null);
				Cursor cursor = db.rawQuery("select * from user_inform", null);
				if (cursor.moveToNext()) {
					UserItem userItem = new UserItem();
					userItem.setDl_sid(cursor.getString(cursor.getColumnIndex("dl_sid")));
					userItem.setKo_mail(cursor.getString(cursor.getColumnIndex("ko_mail")));
					userItem.setKo_name1(cursor.getString(cursor.getColumnIndex("ko_name1")));
					userItem.setKo_name2(cursor.getString(cursor.getColumnIndex("ko_name2")));
					userItem.setKo_no(cursor.getString(cursor.getColumnIndex("ko_no")));
					userItem.setKo_point(cursor.getString(cursor.getColumnIndex("ko_point")));
					userItem.setKo_tani(cursor.getString(cursor.getColumnIndex("ko_tani")));
					userItem.setSid(cursor.getString(cursor.getColumnIndex("sid")));
					intent.putExtra("userItem", userItem);
					intent.setClass(MainActivity.this, UserActivity.class);
				}else{
					intent.setClass(MainActivity.this, LoginActivity.class);
				}
				cursor.close();
				db.close();
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
			}
		});

		textNews = (RelativeLayout) findViewById(R.id.textNews);
		getNotices();

		textNews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				Intent intent = new Intent();
				intent.putExtra("noticeItems", (Serializable) noticeItems);
				intent.setClass(MainActivity.this, NoticeActivity.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
			}
		});

		textQuestion = (TextView) findViewById(R.id.textQuestion);
		textQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MailActivity.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
			}
		});
		textSetting = (TextView) findViewById(R.id.textSetting);
		textSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingActivity.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
				finish();
			}
		});

		textReview = (TextView) findViewById(R.id.textReview);
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

		textSite = (TextView)findViewById(R.id.textSite);
		textSite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				menu.toggle();
		        relateListView.setVisibility(View.VISIBLE);
		        listView.setVisibility(View.GONE);
			}
		});
		
		notice = (TextView) findViewById(R.id.notice);
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
	
	private void initRelateListView(){
		relateListView = (ListView)findViewById(R.id.listView);
		relateListView.setCacheColorHint(Color.TRANSPARENT);
		relateListView.setDividerHeight(0);
		List<RelateItem> relateList = new ArrayList<RelateItem>();
		RelateItem item1 = new RelateItem();
		RelateItem item2 = new RelateItem();
		RelateItem item3 = new RelateItem();
		RelateItem item4 = new RelateItem();
		RelateItem item5 = new RelateItem();
	
		item1.setName("ファイナンシャルマガジン");
		item1.setNumber(1);
		item1.setUrl("http://www.f-mag.jp/");
		item2.setName("マネログ");
		item2.setNumber(2);
		item2.setUrl("http://moneylog.jp/");
		item3.setName("マネーマネジメント検定");
		item3.setNumber(3);
		item3.setUrl("http://www.moneymanagement.jp/");
		item4.setName("accum");
		item4.setNumber(4);
		item4.setUrl("http://accum.jp/");
		item5.setName("セミスタイル");
		item5.setNumber(5);
		item5.setUrl("http://semistyle.jp/");
		
		relateList.add(item1);
		relateList.add(item2);
		relateList.add(item3);
		relateList.add(item4);
		relateList.add(item5);
		
		RelateAdapter relateAdapter = new RelateAdapter(relateList, MainActivity.this);
		relateListView.setAdapter(relateAdapter);
		relateListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				RelateItem relateItem = (RelateItem) relateListView.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("from", "main");
				intent.putExtra("url", relateItem.getUrl());
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
	}
}
