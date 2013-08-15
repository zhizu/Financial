package ac.jfa;

import java.text.DecimalFormat;

import ac.jfa.modal.UserItem;
import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserActivity extends Activity {
	private UserItem userItem = null;
	private ImageButton imageButton = null;
	private RelativeLayout changerpassLayout,ConfirmLayout,VedioLayout,CardLayout = null;
	private TextView nameText,monText,pointText = null;
	private ImageView point = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user);
		
		Intent intent = getIntent();
		userItem = (UserItem) intent.getSerializableExtra("userItem");
		nameText = (TextView)findViewById(R.id.nameText);
		monText = (TextView)findViewById(R.id.monText);
		pointText = (TextView)findViewById(R.id.pointText);
		point = (ImageView)findViewById(R.id.point);
		
		nameText.setText(userItem.getKo_name1() + " 様");
		DecimalFormat df = new DecimalFormat("##,###,###");
		monText.setText(df.format(Integer.parseInt(userItem.getKo_point()))+"pt(" + df.format(Integer.parseInt(userItem.getKo_point())) +")");
		pointText.setText("現在"+userItem.getKo_tani()+"単位取得");
		int tani = Integer.parseInt(userItem.getKo_tani());
		if(tani>0 && tani<23){
			point.setImageResource(R.drawable.column_status_1);
		}else if(tani>23 && tani<45){
			point.setImageResource(R.drawable.column_status_2);
		}else if(tani>46 && tani<71){
			point.setImageResource(R.drawable.column_status_3);
		}else if(tani>72){
			point.setImageResource(R.drawable.column_status_4);
		}
		
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,R.anim.out_to_right);
			}
		});
		
		changerpassLayout = (RelativeLayout)findViewById(R.id.changerpassLayout);
		changerpassLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, RegistActivity.class);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		ConfirmLayout = (RelativeLayout)findViewById(R.id.ConfirmLayout);
		ConfirmLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, HistoryActivity.class);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		VedioLayout = (RelativeLayout)findViewById(R.id.VedioLayout);
		VedioLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, AnimActivity.class);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		CardLayout = (RelativeLayout)findViewById(R.id.CardLayout);
		CardLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, CardActivity.class);
				intent.putExtra("user", userItem);
				UserActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
	}
}
