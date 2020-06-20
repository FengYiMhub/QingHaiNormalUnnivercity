package cn.edu.qhnu.qhsfdx.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.BookingDetail;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.util.BitmapUtil;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.TopBar.OnLeftButtonClickListener;

public  class BookingDetailList extends FragmentActivity implements View.OnClickListener {

	private List<BookingDetail> bookingdetailList;
	private SQLiteDatabase db;
	private TopBar topBar;
	
	public void onCreate(Bundle savedInstanceState) {
		db = DBHelper.getDb(this);
		bookingdetailList = DBDao.findAllBookingDetail(db);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		topBar = (TopBar) findViewById(R.id.topBar);
		topBar.setTitle("路线查询");
		topBar.setLeftButton("", new OnLeftButtonClickListener() {
			
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				BookingDetailList.this.finish();
			}		
		});	
		topBar.hiddenRightButton();
		// 创建一个List集合，List集合的元素是Map
		final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		if (bookingdetailList != null && !bookingdetailList.isEmpty()) {
			for (BookingDetail bookingdetail : bookingdetailList) {
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("header", BitmapUtil.convertFromByteToBitmap(bookingdetail.getIcon()));
				listItem.put("personName", bookingdetail.getName());
				listItem.put("desc", bookingdetail.getIntro_short());
				listItems.add(listItem);
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
					Intent i = new Intent(BookingDetailList.this , DetailListBookingDetail.class);
					i.putExtra("BookingDetail_id",bookingdetailList.get(arg2).get_id()); 
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
