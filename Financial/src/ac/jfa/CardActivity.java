package ac.jfa;

import ac.jfa.modal.UserItem;
import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class CardActivity extends Activity{

	private ImageButton imageButton = null;
	private UserItem user = null;
	private TextView number,name,name_1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.card);
		Intent intent = getIntent();
		user = (UserItem) intent.getSerializableExtra("user");
		
		number = (TextView)findViewById(R.id.number);
		name = (TextView)findViewById(R.id.name);
		name_1 = (TextView)findViewById(R.id.name_1);
		
		number.setText(" ‹÷v…˙No£∫" + user.getKo_no());
		name.setText(user.getKo_name2());
		name_1.setText(user.getKo_name1() + " òî");
		
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
