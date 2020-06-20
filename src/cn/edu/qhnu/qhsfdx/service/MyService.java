package cn.edu.qhnu.qhsfdx.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import cn.edu.qhnu.qhsfdx.MyApplication;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

public class MyService extends Service {
	private MyApplication myApplication;
	private LocationManagerProxy aMapManager;
	protected Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = (MyApplication) getApplication();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startAmap();
		return START_STICKY;
	}

	private void startAmap() {
		aMapManager = LocationManagerProxy.getInstance(this);
		/*
		 * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapManager.requestLocationData(LocationProviderProxy.AMapNetwork,
				2000, 10, mAMapLocationListener);
	}

//	private void stopAmap() {
//		if (aMapManager != null) {
//			aMapManager.removeUpdates(mAMapLocationListener);
//			aMapManager.destory();
//		}
//		aMapManager = null;
//	}

	private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onLocationChanged(Location location) {

		}

		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				Double geoLat = location.getLatitude();//获得经度信息
				Double geoLng = location.getLongitude();//获得纬度信息
				myApplication.setGeoLat(geoLat);//传到MyApplication类里
				myApplication.setGeoLng(geoLng);//传到MyApplication类里
				Log.i("123", geoLat + "");
				Log.i("123", "" + geoLng);
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}