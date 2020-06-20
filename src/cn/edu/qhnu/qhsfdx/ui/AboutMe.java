package cn.edu.qhnu.qhsfdx.ui;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.TopBar.OnLeftButtonClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutMe extends Activity implements View.OnClickListener {

	private TopBar topBar;
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.aboutme);
	topBar = (TopBar) findViewById(R.id.topBar);
	topBar.setTitle("关于我们");
	topBar.removeRightButton();
	topBar.setLeftButton("", new OnLeftButtonClickListener() {

		@Override
		public void onClick(View button) {
			// TODO Auto-generated method stub
			AboutMe.this.finish();
		}
	});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
