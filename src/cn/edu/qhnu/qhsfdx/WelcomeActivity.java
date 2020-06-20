package cn.edu.qhnu.qhsfdx;

import cn.edu.qhnu.qhsfdx.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wlcome);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(WelcomeActivity.this, MainActivity.class);
				startActivity(i);
				WelcomeActivity.this.finish();
			}
		}, 3*1000);
	}
}
