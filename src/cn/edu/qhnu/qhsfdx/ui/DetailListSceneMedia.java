package cn.edu.qhnu.qhsfdx.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.MyApplication;
import cn.edu.qhnu.qhsfdx.bean.Scene;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.util.ExMediaPlayer;
import cn.edu.qhnu.qhsfdx.widget.MyViewPager;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.TopBar.OnLeftButtonClickListener;

public class DetailListSceneMedia extends Activity {
	private TopBar topBar;
	private TextView  introtext;
	private SQLiteDatabase db;
	private List<Bitmap> scenemedia;
	private Scene scene;
	private int scene_id=0;
	private MyApplication app;
	private  ExMediaPlayer    myMediaPlayer = null;
	
	@Override 
	protected void onDestroy() {  
		super.onDestroy();  
		if(myMediaPlayer != null){
			myMediaPlayer.StopPlayer();
			myMediaPlayer.removeMp3File();
		}
		
	} 	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_list);
		app = (MyApplication)getApplication();
		db = DBHelper.getDb(this);
		Intent intent = getIntent();
		scene_id = intent.getIntExtra("scene_id", 0);
		scene = DBDao.findSceneById(db, scene_id);
		topBar = (TopBar) findViewById(R.id.topBar);
		topBar.setTitle(scene.getName());
		introtext = (TextView) findViewById(R.id.textView_intro);
		introtext.setText(scene.getIntro_short());
		Integer main_id=scene_id;
		scenemedia=DBDao.findAllScenemediaIcon(db,main_id);
		topBar.setLeftButton("", new OnLeftButtonClickListener() {
		
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				DetailListSceneMedia.this.finish();//当返回时停止播放
			}
		});	
			
		topBar.hiddenRightButton();
		initViewPager();
		
		//播放语音
		 myMediaPlayer = new ExMediaPlayer(db,this, R.id.seekBar1, R.id.button_player, "media","media", scene_id);
	   	    if(app.my_autoflag){
	   	    	myMediaPlayer.StartPlayer();
	   	    }
		
	}
	private void initViewPager() {

	    ArrayList<View> views = new ArrayList<View>();
	    ImageView image = new ImageView(this);
	    if (scenemedia!=null && !scenemedia.isEmpty()){//加载同一类型的图片
	    for(Bitmap bit :scenemedia)
	    {
	    image.setImageBitmap(bit); 
	    views.add(image);
	    image = new ImageView(this);
	    }
	    }
	    MyViewPager pager = (MyViewPager) findViewById(R.id.my_view_pager);
	    pager.setViewPagerViews(views);
	}
}
