package cn.edu.qhnu.qhsfdx.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.edu.qhnu.qhsfdx.R;

public class DBHelper {
	private static final String TAG = "DBHelper";
	private static final String DB_NAME = "qhh.db";
	public static String mp3Url;
	
	public static SQLiteDatabase getDb(Context context) {
		try {
			
			
			//获取应用程序存储的内部路径
			final String DB_PATH = context.getFilesDir().getAbsolutePath();
			mp3Url  = DB_PATH;
			Log.i(TAG, "路径"+DB_PATH);
            //如果文件不存在，重新存储
			if (!(new File(DB_PATH + "/" + DB_NAME)).exists()) {				
				InputStream is = context.getResources().openRawResource(R.raw.qhh);
				FileOutputStream fos = new FileOutputStream(DB_PATH + "/"
						+ DB_NAME);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			//打开数据库
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
					DB_PATH + "/" + DB_NAME, null);
			return database;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
