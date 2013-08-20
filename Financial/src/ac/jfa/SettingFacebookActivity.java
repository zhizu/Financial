package ac.jfa;

import ac.jfa.util.SessionEvents;
import ac.jfa.util.SessionStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
@SuppressWarnings("deprecation")
public class SettingFacebookActivity extends Activity {

	private Facebook mFacebook;
	public static AsyncFacebookRunner mAsyncRunner;
	private static final String[] PERMISSIONS = new String[] {"publish_stream", 
        "read_stream", "offline_access"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.facebook);
         
		mFacebook = new Facebook("497659066988107");
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		if(!mFacebook.isSessionValid()){
			// restore session if one exists
	        SessionStore.restore(mFacebook, this);
	        
	        SessionEvents.addAuthListener(null);//监听登入和登出
	        SessionEvents.addLogoutListener(null);//监听登入和登出
	        
	        mFacebook.authorize(this, PERMISSIONS, 0, new DialogListener() {
				
				@Override
				public void onFacebookError(FacebookError error) {
					// TODO Auto-generated method stub
					 SessionEvents.onLoginError(error.getMessage());
				}
				
				@Override
				public void onError(DialogError error) {
					// TODO Auto-generated method stub
					SessionEvents.onLoginError(error.getMessage());
				}
				
				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					System.out.println("yang-->completed");
					SessionEvents.onLoginSuccess();
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					SessionEvents.onLoginError("Action Canceled");
				}
			});
		}else{
			System.out.println("gaosiwei");
		}
		
//		// start Facebook Login
//	    Session.openActiveSession(this, true, new Session.StatusCallback() {
//
//	      // callback when session changes state
//	      @Override
//	      public void call(Session session, SessionState state, Exception exception) {
//	        if (session.isOpened()) {
//
//	          // make request to the /me API
//	          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
//
//	            // callback after Graph API response with user object
//	            @Override
//	            public void onCompleted(GraphUser user, Response response) {
//	              if (user != null) {
//	                System.out.println("Hello " + user.getName() + "!");
//	              }
//	            }
//	          });
//	        }
//	      }
//	    });
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//		moveTaskToBack(true);
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
