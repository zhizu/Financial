package ac.jfa;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.adapter.VideoAdapter;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.modal.NewsItem;
import ac.jfa.modal.Video;
import ac.jfa.util.JsonUtils;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.ProgressBar;

public class AnimActivity extends ListActivity {

	private String dl_sid;
	private String movie_category;
	private VideoAdapter adapter = null;
	private ListView listView = null;
	// private VideoView videoView = null;
	private ImageButton imageButton = null;
	private List<Video> items;
	private ProgressBar progress = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vedio);
		progress = (ProgressBar)findViewById(R.id.progress);
		progress.setVisibility(View.VISIBLE);
		Intent intent = getIntent();
		dl_sid = intent.getStringExtra("dl_sid");
		movie_category = intent.getStringExtra("movie_category");
		
		listView = getListView();
		listView.setDividerHeight(0);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Video currentItem = (Video) listView.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.putExtra("url", currentItem.getMovie_url());
				intent.setClass(AnimActivity.this, VedioPlayActivity.class);
				AnimActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		imageButton = (ImageButton) findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Uri uri =
				// Uri.parse("http://iphone.moneylog.jp/school_jukousei.mp4");
				// //调用系统自带的播放器
				// Intent intent = new Intent(Intent.ACTION_VIEW);
				// intent.setDataAndType(uri, "video/mp4");
				// startActivity(intent);

				finish();
				overridePendingTransition(0, R.anim.out_to_right);
			}
		});
		movie();
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

	private void movie() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(AnimActivity.this,
				"https://www.f-academy.jp/sp/app/api/user_movie.cgi?dl_sid="
						+ dl_sid + "&movie_category=" + movie_category,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {
						// TODO Auto-generated method stub
						try {
							progress.setVisibility(View.GONE);
							JSONArray array = jsonObject
									.getJSONArray("movie_data");
							JsonUtils jsonUtiles = new JsonUtils();
							items = jsonUtiles.parseMovieFromJson(array
									.toString());
							adapter = new VideoAdapter(items, AnimActivity.this);
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
