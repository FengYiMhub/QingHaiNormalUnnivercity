package cn.edu.qhnu.qhsfdx.util;
import android.location.LocationManager;

public class GpsDistance {
	//static LocationManager locManager;
	private final static double EARTH_RADIUS = 6378137.0;   
	public static double getDistance(double lng_a, double lat_a, double lng_b, double lat_b) {
	       double radLat1 = (lat_a * Math.PI / 180.0);
	       double radLat2 = (lat_b * Math.PI / 180.0);
	       double a = radLat1 - radLat2;
	       double b = (lng_a - lng_b) * Math.PI / 180.0;
	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
	              + Math.cos(radLat1) * Math.cos(radLat2)
	              * Math.pow(Math.sin(b / 2), 2)));
	       s = s * EARTH_RADIUS;
	       s = Math.round(s * 10000) / 10000.0;
	       s = s/1000.0;
	       return s;
	    }

}
