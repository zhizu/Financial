package ac.jfa;

import ac.jfa.modal.NoticeItem;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReadItemActivity extends Activity{
	private ImageButton imageButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.readitem);
		Intent intent = getIntent();
		NoticeItem item = (NoticeItem) intent.getSerializableExtra("item");
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(item.getTitle());
		TextView content = (TextView)findViewById(R.id.message);
		content.setText(item.getMessage());
		imageButton = (ImageButton) findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0, R.anim.out_to_right);
			}
		});
	}

}
