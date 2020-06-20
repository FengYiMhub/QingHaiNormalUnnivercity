package cn.edu.qhnu.qhsfdx;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.LocationManagerProxy;
import android.app.Application;

public class MyApplication extends Application{
	private static MyApplication instance = null;
	private LocationManagerProxy aMapManager;
	private AMapLocation myAMapLocation = null;//定位结果对象
	private  double geoLat;//纬度
	private  double geoLng;//精度
	public   static   boolean      my_autoflag = false;
	
	public static MyApplication getInstance(){
		if(instance == null){
			instance = new MyApplication();
		}
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();      
	}

	public LocationManagerProxy getaMapManager() {
		return aMapManager;
	}

	public void setaMapManager(LocationManagerProxy aMapManager) {
		this.aMapManager = aMapManager;
	}

	public AMapLocation getMyAMapLocation() {
		return myAMapLocation;
	}

	public void setMyAMapLocation(AMapLocation myAMapLocation) {
		this.myAMapLocation = myAMapLocation;
	}

	public double getGeoLat() {
		return geoLat;
	}

	public void setGeoLat(double geoLat) {
		this.geoLat = geoLat;
	}

	public double getGeoLng() {
		return geoLng;
	}

	public void setGeoLng(double geoLng) {
		this.geoLng = geoLng;
	}

	public static void setInstance(MyApplication instance) {
		MyApplication.instance = instance;
	}

}
