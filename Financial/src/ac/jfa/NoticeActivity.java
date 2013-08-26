package ac.jfa;

import java.util.List;

import ac.jfa.adapter.NoticeItemAdapter;
import ac.jfa.modal.NoticeItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class NoticeActivity extends Activity{

	private ListView listView = null;
	private ImageButton imageButton = null;
	private List<NoticeItem> items;
	private NoticeItemAdapter adapter = null;
	private SQLiteDatabase db = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notice);
		Intent intent = getIntent();
		items = (List<NoticeItem>) intent.getSerializableExtra("noticeItems");
		listView = (ListView)findViewById(R.id.listView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setDividerHeight(0);
		adapter = new NoticeItemAdapter(items, NoticeActivity.this);
		listView.setAdapter(adapter);
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				db = NoticeActivity.this.openOrCreateDatabase("inform", Context.MODE_PRIVATE, null);
//				Cursor cursor = db.rawQuery("select * from notice", null);
//				while (cursor.moveToNext()) {
//					String name = cursor.getString(cursor
//							.getColumnIndex("news_id"));
//					System.out.println("yangxuquery--->" + name);
//				}
//				 cursor.close();
//				 db.close();
				Intent intent = new Intent();
				intent.setClass(NoticeActivity.this, MainActivity.class);
				startActivity(intent);				
				overridePendingTransition(0,R.anim.out_to_right);
				finish();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				NoticeItem currentItem = (NoticeItem) listView.getItemAtPosition(position);
				saveItem(currentItem);
				if(currentItem.getNews_type().equals("0")){
					Intent intent = new Intent();
					intent.setClass(NoticeActivity.this, ReadItemActivity.class);
					intent.putExtra("item", currentItem);
					intent.putExtra("from", "notice");
					NoticeActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.in_from_right, R.anim.out);
				}else if(currentItem.getNews_type().equals("1")){
					Intent intent = new Intent();
					intent.setClass(NoticeActivity.this, KouzaActivity.class);
					intent.putExtra("url", currentItem.getMessage());
					intent.putExtra("from", "notice");
					NoticeActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.in_from_right, R.anim.out);
				}
				
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
	
	private void saveItem(NoticeItem item){
		db = NoticeActivity.this.openOrCreateDatabase("inform", Context.MODE_PRIVATE, null);
		db.execSQL("INSERT INTO notice VALUES (?, ?, ?,?,?)", new Object[]{item.getSend_date(), item.getTitle(),item.getNews_type(),item.getMessage(),item.getNews_id()});
	    db.close();
	}
}
