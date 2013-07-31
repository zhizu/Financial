package com.accum.financial;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.accum.adapter.NewsItemAdapter;
import com.accum.modal.NewsItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends ListActivity {

	private TextView textCommunication,textMoneyLecture,textSchool,textSeminar,textfeed,textVip = null;
	private SlidingMenu menu = null;
	private ImageView menuButton = null;
	private ListView listView = null;
	private List<NewsItem> myListNewsItem;
	private NewsItemAdapter adapter = null;
	private LinearLayout linearfeed = null;
	private NewsItem currentItem = null;
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
        listView = getListView();
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        
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
}
