package ac.jfa;

import java.util.ArrayList;
import java.util.List;

import ac.jfa.adapter.HistoryAdapter;
import ac.jfa.adapter.SchoolAdapter;
import ac.jfa.modal.HistoryItem;
import ac.jfa.modal.School;
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


public class HistoryActivity extends Activity{
    private ImageButton imageButton = null;
	private ListView schoolList,historyList = null;
	private SchoolAdapter adapter = null;
	private HistoryAdapter historyAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.history);
	        initList();
	        School item1 = new School();
	        School item2 = new School();
	        School item3 = new School();
	        List<School> infos = new ArrayList<School>();
	        infos.add(item1);
	        infos.add(item2);
	        infos.add(item3);
	        adapter = new SchoolAdapter(infos, HistoryActivity.this);
	        schoolList.setAdapter(adapter);
	       
	        HistoryItem history = new HistoryItem();
	        HistoryItem history1 = new HistoryItem();
	        HistoryItem history2 = new HistoryItem();
	        List<HistoryItem> info = new ArrayList<HistoryItem>();
	        info.add(history);
	        info.add(history1);
	        info.add(history2);
	        historyAdapter= new HistoryAdapter(info, HistoryActivity.this);
	       historyList.setAdapter(historyAdapter);
	       imageButton = (ImageButton)findViewById(R.id.img_search);
			imageButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					overridePendingTransition(0,R.anim.out_to_right);
				}
			});
	}
	
	private void initList(){
		 schoolList = (ListView)findViewById(R.id.schoolList);
	        historyList = (ListView)findViewById(R.id.historyList);
	        schoolList.setCacheColorHint(Color.TRANSPARENT);
	        historyList.setDividerHeight(0);
	        historyList.setCacheColorHint(Color.TRANSPARENT);
	        schoolList.setDividerHeight(0);
	}
}
