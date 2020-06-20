/**
 * 
 */
package cn.edu.qhnu.qhsfdx.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	public static Bitmap convertFromByteToBitmap(byte[] icon) {
		if(icon != null && icon.length != 0)
			return BitmapFactory.decodeByteArray(icon, 0, icon.length, null);	
		else return null;
	}
}
