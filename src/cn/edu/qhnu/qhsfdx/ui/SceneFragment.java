package cn.edu.qhnu.qhsfdx.ui;

import java.util.List;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.MyApplication;
import cn.edu.qhnu.qhsfdx.bean.Scene;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.service.MyService;
import cn.edu.qhnu.qhsfdx.util.GpsDistance;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.mapScene;
import cn.edu.qhnu.qhsfdx.widget.TopBar.OnRightButtonClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SceneFragment extends Fragment {

	private FragmentActivity parentActivity;
	private TopBar topBar;
	private Bitmap gintama;
	private ImageView imgView;
	private List<Scene> sceneList;
	private SQLiteDatabase db;
	private ViewGroup views;
	private mapScene mapscene;
	private RelativeLayout tou;
	private ToggleButton toggleButton_auto;
	private double distance ;
	private int near_id;
	private MyApplication app;
	private GpsDistance gpsdistance;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		views = (ViewGroup) inflater.inflate(R.layout.fragment_scene,
				container, false);
		topBar = (TopBar) views.findViewById(R.id.topBar);
		topBar.setTitle("校园地图");
		topBar.removeLeftButton();
		topBar.setRightButton("列表", new OnRightButtonClickListener() {
			@Override
			public void onClick(View button) {
				Intent i = new Intent(parentActivity, SceneListActivity.class);
				startActivity(i);
			}
		});
		return views;
	}

	
	
	public void onActivityCreated(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onActivityCreated(savedInstanceState);
		parentActivity = getActivity();
		//开启Service
		Intent intent = new Intent(parentActivity, MyService.class);  
		parentActivity.startService(intent); 
	    //显示地图
		tou = (RelativeLayout) views.findViewById(R.id.tou);
		imgView = (ImageView) views.findViewById(R.id.imageView);
		toggleButton_auto = (ToggleButton) views
				.findViewById(R.id.toggleButton_auto);
		gintama = BitmapFactory.decodeResource(getResources(),
				R.drawable.xsdmap);
		mapscene = new mapScene(parentActivity.getApplicationContext());
		mapscene.setLayout(views, imgView, gintama);
		db = DBHelper.getDb(parentActivity.getApplicationContext());
		sceneList = DBDao.findAllScene(db);
		mapscene.setdata(sceneList);
		mapscene.onCreate(
				new cn.edu.qhnu.qhsfdx.widget.mapScene.onClickCallback() {

					@Override
					public void ClickCallback(int id) {
						// TODO Auto-generated method stub

						if (id != 0xff) {
							Intent mIntent = new Intent();
							mIntent.putExtra("scene_id", sceneList.get(id)
									.get_id());
							mIntent.putExtra("scene_name", sceneList.get(id)
									.getName());
							if (sceneList.get(id).getSpot() != 0)
								mIntent.setClass(parentActivity,
										DetailListSceneMedia.class);
							Log.i("dianji", ""+sceneList.get(id));
							startActivityForResult(mIntent, 0);
						}
					}

				}, 60);
		//循环计算各个景点的数据
		app = (MyApplication) parentActivity.getApplication(); // 获得CustomApplication对象
		toggleButton_auto.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				app.my_autoflag = isChecked;
				if(app.getGeoLat()!=0.0 && app.getGeoLng()!=0.0 && isChecked == true )
				{
					Toast.makeText(parentActivity, "正在定位...", Toast.LENGTH_SHORT).show();
					if (sceneList != null && !sceneList.isEmpty()) 
					{
						for (Scene scene : sceneList) 
						{
							
							gpsdistance = new GpsDistance();
							distance = gpsdistance.getDistance(app.getGeoLng(), app.getGeoLat(), scene.getLon(), scene.getLat());
							
							Log.i("经度", ""+app.getGeoLng());
							Log.i("纬度", ""+app.getGeoLat());
							
							distance = distance*1000-10;
							Log.i("sss", ""+distance);
							if(distance <10)
							{
								near_id = scene.get_id();
								//Log.i("sss", ""+near_id);
								Intent mIntent = new Intent();
								mIntent.putExtra("scene_id", near_id);
								mIntent.setClass(parentActivity, DetailListSceneMedia.class);
								startActivity(mIntent);
							}
							}
						
					}
					
				}
				else{
					Toast.makeText(parentActivity, "语音导游结束！", Toast.LENGTH_SHORT).show();
				}
			}
		});
									
	}

	private void requestWindowFeature(int featureNoTitle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
