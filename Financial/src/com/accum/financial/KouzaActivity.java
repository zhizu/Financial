package com.accum.financial;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class KouzaActivity extends Activity{

	private String Url;
	private ImageButton imageButton = null;
	private WebView webView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	       requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.kouza);
		
		Intent intent = getIntent();
		Url = intent.getStringExtra("url");
		imageButton = (ImageButton)findViewById(R.id.img_search);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,R.anim.out_to_right);
			}
		});
		
		webView = (WebView)findViewById(R.id.webView);
		webView.setScrollBarStyle(0);
		WebSettings settings = webView.getSettings(); 
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true); 
		settings.setJavaScriptEnabled(true);
		webView.loadUrl(Url);
		webView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				webView.loadUrl(url);
				return true;
			}
			
		});
	}
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {

				if (webView.canGoBack()) {
					webView.goBack();
					return true;
				}
			
			}
			return true;
		}
	

}
