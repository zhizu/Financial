package ac.jfa;

import java.util.ArrayList;
import java.util.List;

import ac.jfa.adapter.NoticeItemAdapter;
import ac.jfa.modal.NoticeItem;
import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class NoticeActivity extends Activity{

	private ListView listView = null;
	private ImageButton imageButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notice);
		listView = (ListView)findViewById(R.id.listView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setDividerHeight(0);
		
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,R.anim.out_to_right);
			}
		});
		
		List<NoticeItem> item = new ArrayList<NoticeItem>();
		NoticeItem item1 = new NoticeItem();
		NoticeItem item2 = new NoticeItem();
		NoticeItem item3 = new NoticeItem();
		item.add(item1);
		item.add(item2);
		item.add(item3);
		NoticeItemAdapter adapter = new NoticeItemAdapter(item, NoticeActivity.this);
		listView.setAdapter(adapter);
		
	}
}
