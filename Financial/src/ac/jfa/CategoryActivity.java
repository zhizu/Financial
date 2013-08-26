package ac.jfa;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.adapter.CategoryItemAdapter;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.modal.CategoryItem;
import ac.jfa.util.JsonUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

public class CategoryActivity extends Activity{

	private String dl_sid;
	private List<CategoryItem> list;
	private CategoryItemAdapter adapter;
	private ListView listView = null;
	private ImageButton imageButton = null;
	private ProgressBar progress = null;
	private Button button = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.category);
		button = (Button)findViewById(R.id.warn);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("from", "category");
				intent.setClass(CategoryActivity.this, ReadItemActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		progress = (ProgressBar)findViewById(R.id.progress);
		progress.setVisibility(View.VISIBLE);
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CategoryActivity.this.finish();
				overridePendingTransition(0, R.anim.out_to_right);
			}
		});
		Intent intent = getIntent();
		dl_sid = intent.getStringExtra("dl_sid");
		listView = (ListView)findViewById(R.id.listView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setDividerHeight(0);
		getCategorys(dl_sid);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CategoryItem currentItem = (CategoryItem) listView.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.setClass(CategoryActivity.this, AnimActivity.class);
				intent.putExtra("dl_sid", dl_sid);
				intent.putExtra("movie_category", currentItem.getMovie_category());
				CategoryActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
	}
	private void getCategorys(String dl) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(CategoryActivity.this,
				"https://www.f-academy.jp/sp/app/api/user_movie_list.cgi?dl_sid=" + dl,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {
						// TODO Auto-generated method stub
						try {
							progress.setVisibility(View.GONE);
							JSONArray array = jsonObject
									.getJSONArray("video_data");
							
							JsonUtils jsonUtiles = new JsonUtils();
							list = jsonUtiles.parseCategoryItemFromJson(array
									.toString());
							adapter = new CategoryItemAdapter(list,
									CategoryActivity.this);
							listView.setAdapter(adapter);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						super.onSuccess(jsonObject);
					}

				});
	}
}
