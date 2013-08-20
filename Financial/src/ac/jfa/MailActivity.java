package ac.jfa;

import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MailActivity extends Activity{

	private Button button = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mail);
		button = (Button)findViewById(R.id.decide);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();  
				intent.setAction(Intent.ACTION_SEND);  
				intent.putExtra(Intent.EXTRA_EMAIL,"yang.asahi@gmail.com");
				intent.putExtra(Intent.EXTRA_CC,"xuxu_1111@126.com");
				intent.putExtra(Intent.EXTRA_SUBJECT, "test");  
				intent.putExtra(Intent.EXTRA_TEXT, "test mail"); 
				startActivity(Intent.createChooser(intent, "Sending    mail..."));        
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
