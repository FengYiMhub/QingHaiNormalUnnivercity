package cn.edu.qhnu.qhsfdx.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.MyApplication;
import cn.edu.qhnu.qhsfdx.bean.Scene;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.util.BitmapUtil;
import cn.edu.qhnu.qhsfdx.util.GpsDistance;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.TopBar.OnLeftButtonClickListener;

public  class SceneListActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "qhsfdx.SimpleScene";
	private List<Scene> sceneList;
	private SQLiteDatabase db;
	private TopBar topBar;
	AMapLocation location = null;
	private double geoLat1 ;
	private double geoLng1 ;
	private MyApplication app;
	private double distance ;


	
	public void onCreate(Bundle savedInstanceState) {
		db = DBHelper.getDb(this);
		sceneList = DBDao.findAllScene(db);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		topBar = (TopBar) findViewById(R.id.topBar);
		topBar.setTitle("青海师范大学楼宇列表");
		topBar.setLeftButton("", new OnLeftButtonClickListener() {
			
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
			finish();
			}
		});	
		topBar.hiddenRightButton();		
		app = (MyApplication) getApplication(); // 获得MyApplication对象
		geoLat1 = app.getGeoLat();
		geoLng1 = app.getGeoLng();
		//创建一个List集合，List集合的元素是Map
		final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		if (sceneList != null && !sceneList.isEmpty()) {
			for (Scene scene : sceneList) {//动态添加布局
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("header", BitmapUtil.convertFromByteToBitmap(scene.getIcon()));
				if(geoLng1==0.0)
				{
				listItem.put("personName", scene.getName()+"  正在定位...");
				}
				else{
					distance=GpsDistance.getDistance(geoLng1, geoLat1, scene.getLon(), scene.getLat());
					distance=distance*1000-10;
					 BigDecimal bg = new BigDecimal(distance);
				     double distance1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				     
				listItem.put("personName", scene.getName()+"   距"+distance1+"米");	
				}
				
				listItem.put("desc", scene.getIntro_short());
				listItems.add(listItem);
				Log.i(TAG, scene.getName());
			}
			// 创建一个SimpleAdapter
			SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
					R.layout.simple_item, new String[] { "personName",
							"header", "desc" }, new int[] { R.id.name,
							R.id.header, R.id.desc });
			simpleAdapter.setViewBinder(new ViewBinder() {				
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView iv=(ImageView)view;
                        iv.setImageBitmap((Bitmap) data);
                        return true;
                }else{
                        return false;
                }	
					
				}
			});
			ListView list = (ListView) findViewById(R.id.mylist);
			// 为ListView设置Adapter
			list.setAdapter(simpleAdapter);

			// 为ListView的列表项单击事件绑定事件监听器
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					//Toast.makeText(SimpleScene.this, "你点击的是第" + arg3 + "项",  
				    //Toast.LENGTH_SHORT).show();  
					Intent i = new Intent(SceneListActivity.this , DetailListSceneMedia.class);
					i.putExtra("scene_id",sceneList.get(arg2).get_id());  					
			        startActivity(i); 
				}
			});
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
