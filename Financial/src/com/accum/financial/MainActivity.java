package com.accum.financial;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accum.adapter.NewsItemAdapter;
import com.accum.modal.NewsItem;
import com.accum.util.FaRestClient;
import com.accum.util.JsonUtils;
import com.accum.xListView.XListView;
import com.accum.xListView.XListView.IXListViewListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity implements IXListViewListener{

	private TextView textCommunication,textMoneyLecture,textSchool,textSeminar,textfeed,textVip = null;
	private SlidingMenu menu = null;
	private ImageView menuButton = null;
	private XListView listView = null;
	private List<NewsItem> myListNewsItem;
	private NewsItemAdapter adapter = null;
	private LinearLayout linearfeed = null;
	private NewsItem currentItem = null;
	private Handler mHandler = null;
	private int start = 0;
	private static int refreshCnt = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set the content view
        setContentView(R.layout.main);
        LinearInit();
        NewsItem item = new NewsItem();
        NewsItem item2 = new NewsItem();
        NewsItem item3 = new NewsItem();
        List<NewsItem> infos = new ArrayList<NewsItem>();
        infos.add(item);
        infos.add(item2);
        infos.add(item3);
        adapter = new NewsItemAdapter(infos, MainActivity.this);
        listView = (XListView)findViewById(R.id.xListView);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setDividerHeight(0);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setAdapter(adapter);
        listView.setXListViewListener(this);
        mHandler = new Handler();
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				currentItem = (NewsItem)listView.getItemAtPosition(position);
//				currentItem.getContent_url();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url", "http://www.f-academy.jp/course_index.html");
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

        textCommunication = (TextView)findViewById(R.id.textCommunication);
        textCommunication.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url", "http://www.f-academy.jp/course_index.html");
				
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
        textMoneyLecture = (TextView)findViewById(R.id.textMoneyLecture);
        textMoneyLecture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url", "http://www.f-academy.jp/sp/school/kyouyousemi.html");
				
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
        textSchool = (TextView)findViewById(R.id.textSchool);
        textSchool.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url", "http://www.f-academy.jp/school_index.html");
				
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
        textSeminar = (TextView)findViewById(R.id.textSeminar);
        textSeminar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, KouzaActivity.class);
				intent.putExtra("url", "http://www.f-academy.jp/seminar_index.html");
				
				menu.toggle();
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
        menuButton = (ImageView)findViewById(R.id.newsfeed_flip);
        menuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
			}
		});
        
        textfeed = (TextView)findViewById(R.id.textfeed);
        textfeed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
				linearfeed.setVisibility(View.VISIBLE);
			
//				AsyncHttpClient client = new AsyncHttpClient();
//				 client.get(MainActivity.this, "https://www.f-academy.jp/sp/app/api/feed_json.cgi", new JsonHttpResponseHandler(){
//
//
//					@Override
//					public void onSuccess(JSONObject jsonObject) {
//						// TODO Auto-generated method stub
//						try {
//							JSONArray array = jsonObject.getJSONArray("feed_data");
//							 JsonUtils jsonUtiles = new JsonUtils();
//							 jsonUtiles.parseItemFromJson(array.toString());
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//						super.onSuccess(jsonObject);
//					}
//					 
//				 });
			}
		});
        
        textVip = (TextView)findViewById(R.id.textVip);
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
    }

    public void LinearInit(){
    	 linearfeed = (LinearLayout)findViewById(R.id.linearfeed);
    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
//				items.clear();
//				geneItems();
				// mAdapter.notifyDataSetChanged();
//				mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.list_item, items);
//				mListView.setAdapter(mAdapter);
				listView.stopRefresh();
				listView.stopLoadMore();
				listView.setRefreshTime("먼먼");
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
//				geneItems();
//				mAdapter.notifyDataSetChanged();
//				onLoad();
				listView.stopRefresh();
				listView.stopLoadMore();
				listView.setRefreshTime("먼먼");
			}
		}, 2000);
	}

}
