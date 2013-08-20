package ac.jfa;

import ac.jfa.util.Manager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PassActivity extends Activity implements OnClickListener{

	private ImageButton imageButton = null;
	private StringBuffer sb = new StringBuffer();
	private String pass_1;//设定密码时第一次输入的密码
	private Button one,two,three,four,five,six,seven,eight,nine,zero,delete;
	private int i = 0;//密码的长度
	private int flag = 0;//判断是否重复输入密码进行验证
	private ImageView view_1,view_2,view_3,view_4 = null;
	private TextView textView = null;//控制标题
	private String from;//判断是从哪个Activity跳转过来的
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pass);
		initial();
		Intent intent = getIntent();
		from = intent.getStringExtra("from");
		if(from.equals("set")){
			textView.setText("パスワード");
		}else if(from.equals("init")){
			textView.setText("パスワード");
		}
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.one:
			add(one.getText().toString().trim());
			break;
		case R.id.two:
			add(two.getText().toString().trim());
			break;
		case R.id.three:
			add(three.getText().toString().trim());
			break;
		case R.id.four:
			add(four.getText().toString().trim());
			break;
		case R.id.five:
			add(five.getText().toString().trim());
			break;
		case R.id.six:
			add(six.getText().toString().trim());
			break;
		case R.id.seven:
			add(seven.getText().toString().trim());
			break;
		case R.id.eight:
			add(eight.getText().toString().trim());
			break;
		case R.id.nine:
			add(nine.getText().toString().trim());
			break;
		case R.id.zero:
			add(zero.getText().toString().trim());
			break;
		case R.id.delete:
			if(i != 0){
				if (sb.length() - 1 >= 0) {
					sb.delete(sb.length() - 1, sb.length());
				}
				if(i == 3){
					view_3.setVisibility(View.GONE);
				}else if(i ==2){
					view_2.setVisibility(View.GONE);
				}else if(i == 1){
					view_1.setVisibility(View.GONE);
				}
				i--;
			}
			
			break;
		default:
			break;
		}
	}
	
	public void initial(){
		textView = (TextView)findViewById(R.id.text);
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PassActivity.this.finish();
			}
		});
		one = (Button) findViewById(R.id.one);
		two = (Button) findViewById(R.id.two);
		three = (Button) findViewById(R.id.three);
		four = (Button) findViewById(R.id.four);
		five = (Button) findViewById(R.id.five);
		six = (Button) findViewById(R.id.six);
		seven = (Button) findViewById(R.id.seven);
		eight = (Button) findViewById(R.id.eight);
		nine = (Button) findViewById(R.id.nine);
		zero = (Button) findViewById(R.id.zero);
		delete = (Button) findViewById(R.id.delete);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		six.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		zero.setOnClickListener(this);
		delete.setOnClickListener(this);
		view_1 = (ImageView)findViewById(R.id.view_1);
		view_2 = (ImageView)findViewById(R.id.view_2);
		view_3 = (ImageView)findViewById(R.id.view_3);
		view_4 = (ImageView)findViewById(R.id.view_4);
	}

	public void add(String pass){
		if(i == 0){
			view_1.setVisibility(View.VISIBLE);
			sb.append(pass);
			i++;
		}else if(i == 1){
			view_2.setVisibility(View.VISIBLE);
			sb.append(pass);
			i++;
		}else if(i == 2){
			view_3.setVisibility(View.VISIBLE);
			sb.append(pass);
			i++;
		}else if(i == 3){
			view_4.setVisibility(View.VISIBLE);
			sb.append(pass);
			if(from.equals("init")){
				String password = sb.toString();
				SharedPreferences preferences = getSharedPreferences("setting", MODE_WORLD_READABLE);
				String password2 = preferences.getString("pass_code", "");
				if(password.equals(password2)){
					Intent intent = new Intent();
					intent.setClass(PassActivity.this, MainActivity.class);
					PassActivity.this.startActivity(intent);
					PassActivity.this.finish();
				}else{
					i = 0;
					sb.delete(0,sb.length());
					view_1.setVisibility(View.GONE);
					view_2.setVisibility(View.GONE);
					view_3.setVisibility(View.GONE);
					view_4.setVisibility(View.GONE);
					Toast.makeText(PassActivity.this, "パスコードが間違いでした。再度入力してください。", Toast.LENGTH_SHORT).show();
				}
			}else{
				if(flag == 0){
					i = 0;
					flag = 1;
					pass_1 = sb.toString();
					sb.delete(0,sb.length());
					textView.setText("再度入力してください");
					view_1.setVisibility(View.GONE);
					view_2.setVisibility(View.GONE);
					view_3.setVisibility(View.GONE);
					view_4.setVisibility(View.GONE);
				}else{
					if(pass_1.equals(sb.toString())){
						Intent intent = new Intent();
						intent.putExtra("pass", pass_1);
						PassActivity.this.setResult(1, intent);
						PassActivity.this.finish();
					}else{
						i = 0;
						flag = 0;
						sb.delete(0,sb.length());
						textView.setText("パースワード");
						view_1.setVisibility(View.GONE);
						view_2.setVisibility(View.GONE);
						view_3.setVisibility(View.GONE);
						view_4.setVisibility(View.GONE);
						Toast.makeText(PassActivity.this, "パスコードが一致しませんでした。再度設定してください。", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
			
		}
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
