package cn.edu.qhnu.qhsfdx.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import cn.edu.qhnu.qhsfdx.bean.Booking;
import cn.edu.qhnu.qhsfdx.bean.BookingDetail;
import cn.edu.qhnu.qhsfdx.bean.BookingMedia;
import cn.edu.qhnu.qhsfdx.bean.More;
import cn.edu.qhnu.qhsfdx.bean.Scene;
import cn.edu.qhnu.qhsfdx.bean.Scene_media;
import cn.edu.qhnu.qhsfdx.bean.Traval;
import cn.edu.qhnu.qhsfdx.bean.TravalDetail;
import cn.edu.qhnu.qhsfdx.bean.TravelMedia;

public class DBDao {
	private static final String TAG = "qhh.DBDao";

	// 通过ID查找单条Traval数据
	public static Traval findTravalById(SQLiteDatabase db, Integer _id) {

		Cursor cursor = db.rawQuery("select * from department_class where _id=?",
				new String[] { _id.toString() });

		try {
			cursor.moveToFirst();// 指向查询结果的第一个位置

			String name = cursor.getString(cursor.getColumnIndex("name"));
			byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
			Integer tag = cursor.getInt(cursor.getColumnIndex("tag"));
			Traval traval = new Traval(_id, name, icon, tag);

			return traval;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	// 查找所有Traval数据
	public static List<Traval> findAllTraval(SQLiteDatabase db) {
		List<Traval> travalList = new ArrayList<Traval>();
		Cursor cursor = db.rawQuery("select * from department_class", null);
		try {
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
				Integer tag = cursor.getInt(cursor.getColumnIndex("tag"));
				Traval traval = new Traval(_id, name, icon, tag);
				travalList.add(traval);
			}
			return travalList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public static List<TravalDetail> findAllTravalDetailById(SQLiteDatabase db,
			Integer main_id) {
		List<TravalDetail> travalDeailList = new ArrayList<TravalDetail>();
		Cursor cursor = db.rawQuery(
				"select * from traval_detail where main_id=?",
				new String[] { main_id.toString() });
		try {
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				// Integer main_id =
				// cursor.getInt(cursor.getColumnIndex("main_id"));
				String intro_short = cursor.getString(cursor
						.getColumnIndex("intro_short"));
				byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
				Integer ord = cursor.getInt(cursor.getColumnIndex("ord"));

				TravalDetail travaldetail = new TravalDetail(_id, name,
						main_id, intro_short, icon, ord);
				Log.i(TAG, name);
				travalDeailList.add(travaldetail);
			}
			return travalDeailList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public static List<TravelMedia> findTravalmediaById(SQLiteDatabase db,
			Integer main_id, boolean istravalflg) {
		Cursor cursor;
		List<TravelMedia> travalMediaList = new ArrayList<TravelMedia>();
		int traval_detail_id, traval_id;
		if (istravalflg) {
			traval_id = main_id;
			cursor = db.rawQuery("select * from department_detail,department_class where department_class._id=department_detail.main_id and department_class._id=?",
							new String[] { main_id.toString() });
		} else {
			traval_detail_id = main_id;
			cursor = db.rawQuery("select * from department_detail where main_id=?",
					new String[] { main_id.toString() });
		}
		try {
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				byte[] media = cursor.getBlob(cursor.getColumnIndex("media"));
				String introduction = cursor.getString(cursor
						.getColumnIndex("introduction"));
				TravelMedia travalmedia = new TravelMedia(_id, main_id, title,
						media, introduction);
				travalMediaList.add(travalmedia);
			}
			return travalMediaList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	// 通过ID查找单条Booking数据
	public static Booking findBookingById(SQLiteDatabase db, Integer _id) {

		Cursor cursor = db.rawQuery("select * from traffic where _id=?",
				new String[] { _id.toString() });
		try {
			cursor.moveToFirst();
			String name = cursor.getString(cursor.getColumnIndex("name"));
			byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
			Integer tag = cursor.getInt(cursor.getColumnIndex("tag"));
			Booking booking = new Booking(_id, name, icon, tag);

			return booking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	/**
	 * 查找所有Booking
	 * 
	 * @Title: findAllBooking
	 */
	public static List<Booking> findAllBooking(SQLiteDatabase db) {

		List<Booking> bookingList = new ArrayList<Booking>();
		Cursor cursor = db.rawQuery("select * from traffic", null);
		try {
			cursor.moveToFirst();
			while (cursor.moveToNext()) {

				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
				Integer tag = cursor.getInt(cursor.getColumnIndex("tag"));

				Booking booking = new Booking(_id, name, icon, tag);

				bookingList.add(booking);
			}
			return bookingList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public static List<BookingDetail> findAllBookingDetail(SQLiteDatabase db) {

		List<BookingDetail> BookingDetailList = new ArrayList<BookingDetail>();
		Cursor cursor = db.rawQuery("select * from traffic_class", null);
		try {
			// cursor.moveToFirst();
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				Integer main_id = cursor.getInt(cursor
						.getColumnIndex("main_id"));
				String intro_short = cursor.getString(cursor
						.getColumnIndex("intro_short"));
				byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
				Integer ord = cursor.getInt(cursor.getColumnIndex("ord"));

				// Bitmap icon = BitmapFactory.decodeByteArray(ic, 0, ic.length,
				// null);
				BookingDetail bookingdetail = new BookingDetail(_id, name,
						main_id, intro_short, icon, ord);
				BookingDetailList.add(bookingdetail);
				Log.i(TAG, name);
			}
			return BookingDetailList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public static List<BookingMedia> findBookingMediaById(SQLiteDatabase db,
			Integer main_id) {
		List<BookingMedia> BookingDetailList = new ArrayList<BookingMedia>();
		int BookingDetail_id = main_id;
		Cursor cursor = db.rawQuery(
				"select * from traffic_detail where main_id=?",
				new String[] { main_id.toString() });
		try {
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				// Integer main_id =
				// cursor.getInt(cursor.getColumnIndex("main_id"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				byte[] media = cursor.getBlob(cursor.getColumnIndex("media"));
				String introduction = cursor.getString(cursor
						.getColumnIndex("introduction"));

				BookingMedia bookingmedia = new BookingMedia(_id, main_id,
						title, media, introduction);
				BookingDetailList.add(bookingmedia);
			}
			return BookingDetailList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	/**
	 * 
	 */
	public static Scene findSceneById(SQLiteDatabase db, Integer _id) {

		Cursor cursor = db.rawQuery("select * from building_info where _id=?",
				new String[] { _id.toString() });
		try {
			cursor.moveToFirst();

			String name = cursor.getString(cursor.getColumnIndex("name"));
			byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
			// Bitmap icon = BitmapFactory.decodeByteArray(ic, 0, ic.length,
			// null);
			Integer x = cursor.getInt(cursor.getColumnIndex("x"));
			Integer y = cursor.getInt(cursor.getColumnIndex("y"));
			float lon = cursor.getFloat(cursor.getColumnIndex("lon"));
			float lat = cursor.getFloat(cursor.getColumnIndex("lat"));
			String intro_short = cursor.getString(cursor
					.getColumnIndex("intro_short"));
			Integer type = cursor.getInt(cursor.getColumnIndex("type"));
			Integer spot = cursor.getInt(cursor.getColumnIndex("spot"));
			Integer ord = cursor.getInt(cursor.getColumnIndex("ord"));

			Scene scene = new Scene(_id, name, x, y, lon, lat, intro_short,
					icon, type, spot, ord);

			return scene;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	/**
	 * 
	 */
	public static List<Scene> findAllScene(SQLiteDatabase db) {

		List<Scene> sceneList = new ArrayList<Scene>();
		Cursor cursor = db.rawQuery("select * from building_info", null);
		try {
			// cursor.moveToFirst();
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
				Integer x = cursor.getInt(cursor.getColumnIndex("x"));
				Integer y = cursor.getInt(cursor.getColumnIndex("y"));
				float lon = cursor.getFloat(cursor.getColumnIndex("lon"));
				float lat = cursor.getFloat(cursor.getColumnIndex("lat"));
				String intro_short = cursor.getString(cursor
						.getColumnIndex("intro_short"));
				Integer type = cursor.getInt(cursor.getColumnIndex("type"));
				Integer spot = cursor.getInt(cursor.getColumnIndex("spot"));
				Integer ord = cursor.getInt(cursor.getColumnIndex("ord"));

				// Bitmap icon = BitmapFactory.decodeByteArray(ic, 0, ic.length,
				// null);
				Scene scene = new Scene(_id, name, x, y, lon, lat, intro_short,
						icon, type, spot, ord);
				sceneList.add(scene);
				Log.i(TAG, name);
			}
			return sceneList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public static List<Scene> findAllScene(SQLiteDatabase db, Integer _id) {

		List<Scene> sceneList = new ArrayList<Scene>();
		Cursor cursor = db.rawQuery("select * from building_info where _id=?",
				new String[] { _id.toString() });
		try {
			// cursor.moveToFirst();
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				// Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
				Integer x = cursor.getInt(cursor.getColumnIndex("x"));
				Integer y = cursor.getInt(cursor.getColumnIndex("y"));
				float lon = cursor.getFloat(cursor.getColumnIndex("lon"));
				float lat = cursor.getFloat(cursor.getColumnIndex("lat"));
				String intro_short = cursor.getString(cursor
						.getColumnIndex("intro_short"));
				Integer type = cursor.getInt(cursor.getColumnIndex("type"));
				Integer spot = cursor.getInt(cursor.getColumnIndex("spot"));
				Integer ord = cursor.getInt(cursor.getColumnIndex("ord"));

				// Bitmap icon = BitmapFactory.decodeByteArray(ic, 0, ic.length,
				// null);
				Scene scene = new Scene(_id, name, x, y, lon, lat, intro_short,
						icon, type, spot, ord);
				sceneList.add(scene);
				Log.i(TAG, name);
			}
			return sceneList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	// 获取指定ID的图片
	public static Bitmap findAllSceneIconById(SQLiteDatabase db, Integer _id) {

		Cursor cursor = db.rawQuery("select * from building_info where _id=?",
				new String[] { _id.toString() });
		try {
			cursor.moveToFirst();
			byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
			Bitmap bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length,
					null);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	// 获取所有图片
	public static List<Bitmap> findAllScenemediaIcon(SQLiteDatabase db,
			Integer main_id) {

		List<Bitmap> drawables = new ArrayList<Bitmap>();
		Cursor cursor = db.rawQuery(
				"select * from media where main_id = ? and type = 2",
				new String[] { main_id.toString() });
		try {
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				byte[] media = cursor.getBlob(cursor.getColumnIndex("media"));
				Bitmap bitmap = BitmapFactory.decodeByteArray(media, 0,
						media.length, null);
				drawables.add(bitmap);
			}
			return drawables;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public String get_mp3_file(SQLiteDatabase db, String tabname, String keyname, int main_id) {
		String mp3url = "";
		String filename = DBHelper.mp3Url + tabname + "_" + keyname + "_" + main_id
				+ ".mp3";
		File file = new File(filename);
		if (file.exists())
			return filename;
		String sql ="select " + keyname + " from " + tabname
				+ " where main_id = "+ main_id +" and type = 3";
		Log.i("sql", sql);
		Cursor cursor = db.rawQuery("select " + keyname + " from " + tabname
				+ " where main_id = ? and type = 3", new String[] { "" + main_id });
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			byte[] media = null;				
	        try {
	        	media = cursor.getBlob(cursor.getColumnIndex("media"));
		        OutputStream outStream = new FileOutputStream(file);			        
		        outStream.write(media);
		        outStream.close();		      
		        mp3url = filename;
	        } catch (FileNotFoundException e) {
	        	filename = "";
	        	Log.w("ImageFileCache","FileNotFoundException");
	        } catch (IOException e) {
	        	filename = "";
	        	Log.w("ImageFileCache","IOException");
	        }	
		}
	    cursor.close();
		return mp3url;		
	}	
	public static List<Scene_media> findAllSceneMedia(SQLiteDatabase db) {

		List<Scene_media> scenemediaList = new ArrayList<Scene_media>();
		Cursor cursor = db.rawQuery("select * from media", null);
		try {
			// cursor.moveToFirst();
			if (cursor.getCount() == 0) {
				return null;
			}
			while (cursor.moveToNext()) {
				Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
				byte[] media = cursor.getBlob(cursor.getColumnIndex("media"));
				Integer type = cursor.getInt(cursor.getColumnIndex("type"));
				Integer main_id = cursor.getInt(cursor
						.getColumnIndex("main_id"));

				// Bitmap icon = BitmapFactory.decodeByteArray(ic, 0, ic.length,
				// null);
				Scene_media scene_media = new Scene_media(_id, main_id, type,
						media);
				scenemediaList.add(scene_media);
			}
			return scenemediaList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public static List<Bitmap> findSceneMediaIconByMain_Id(SQLiteDatabase db,
			Integer main_id) {

		List<Bitmap> scenemediaList = new ArrayList<Bitmap>();
		Cursor cursor = db.rawQuery(
				"select * from media where main_id=?",
				new String[] { main_id.toString() });
		try {
			cursor.moveToFirst();

			while (cursor.moveToNext()) {
				byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
				Bitmap bitmap = BitmapFactory.decodeByteArray(icon, 0,
						icon.length, null);
				scenemediaList.add(bitmap);
			}
			return scenemediaList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

	public static More findMoreById(SQLiteDatabase db, Integer _id) {

		Cursor cursor = db.rawQuery("select * from more where _id=?",
				new String[] { _id.toString() });
		try {
			cursor.moveToFirst();
			String name = cursor.getString(cursor.getColumnIndex("name"));
			byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
			Integer tag = cursor.getInt(cursor.getColumnIndex("tag"));
			More more = new More(_id, name, icon, tag);

			return more;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			// db.close();
		}
	}

}
