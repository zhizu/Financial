package ac.jfa;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.adapter.CategoryItemAdapter;
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
import android.widget.ImageButton;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CategoryActivity extends Activity{

	private String dl_sid;
	private List<CategoryItem> list;
	private CategoryItemAdapter adapter;
	private ListView listView = null;
	private ImageButton imageButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.category);
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
							System.out.println("yangxu--->"+ jsonObject.toString());
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
