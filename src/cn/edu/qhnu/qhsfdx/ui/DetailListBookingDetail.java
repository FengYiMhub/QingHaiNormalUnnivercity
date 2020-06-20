package cn.edu.qhnu.qhsfdx.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.BookingMedia;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.util.BitmapUtil;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.TopBar.OnLeftButtonClickListener;


public class DetailListBookingDetail extends Activity {
	private TopBar topBar;
	private  List<BookingMedia>   mybookingmedialist; 
	private  ListView        listView_travalshow; 
	private  int           BookingDetail_id = 0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travallistshow);
		topBar = (TopBar) findViewById(R.id.topBar);
		topBar.setLeftButton("", new OnLeftButtonClickListener() {

			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				DetailListBookingDetail.this.finish();
			}
		});
		topBar.hiddenRightButton();
		SQLiteDatabase db = DBHelper.getDb(this);
		listView_travalshow   = (ListView)findViewById(R.id.listView_travalshow);	
        Intent intent = getIntent();
        if(intent.hasExtra("BookingDetail_id")) {
        	BookingDetail_id   = intent.getIntExtra("BookingDetail_id", 0);    			   	  
        	mybookingmedialist = DBDao.findBookingMediaById(db, BookingDetail_id);        	
        	topBar.setTitle("路线查询");
        }
  	    TravalListShowAdapater mTravalListShowAdapater = new TravalListShowAdapater(this);
  	    listView_travalshow.setAdapter(mTravalListShowAdapater);
 	   	       	  
    }    
	
  	class TravalListShowAdapater extends BaseAdapter {		
  		
  		private Context            mContext;
  		TravalListShowAdapater(Context myContext){
  			this.mContext = myContext;
  		}
  	
  		public int getCount() { return mybookingmedialist.size(); }
  		public Object getItem(int position) { return mybookingmedialist.get(position); }
  		public long getItemId(int position) { return position; }
  	
  	    class ViewHolder { 
  	    	private TextView    title; 	
  	    	private ImageView    icon;
  	    	private TextView  strlong; 	
  	    } 
  	
  		public View getView(int position, View convertView, ViewGroup parent) {
  			ViewHolder        holder = null;				
  			BookingMedia  wb = mybookingmedialist.get(position);			
  			if (convertView == null) { 
  				holder = new ViewHolder(); 
  				convertView=LayoutInflater.from(mContext).inflate(R.layout.travallistshowitem, null); 
  				holder.title   = (TextView ) convertView.findViewById(R.id.textView_traval_text);	
  				holder.icon    = (ImageView) convertView.findViewById(R.id.imageView_traval_icon );	
  				holder.strlong = (TextView ) convertView.findViewById(R.id.textView_traval_long); 	 			
  				convertView.setTag(holder); 
  	        }  else { 
  	            holder = (ViewHolder) convertView.getTag(); 
  	        } 	
  			if(wb.getTitle() == null || wb.getTitle().length() < 2){
  				holder.title.setVisibility(View.GONE);
  			}else{
  				holder.title.setVisibility(View.VISIBLE);
  				holder.title.setText( wb.getTitle() );
  			}
  			
  			if(wb.getMedia() == null){
  				holder.icon.setVisibility(View.GONE);
  			}else{
  				holder.icon.setVisibility(View.VISIBLE);
  				holder.icon.setImageBitmap( BitmapUtil.convertFromByteToBitmap(wb.getMedia()));
  			}
  			
  			if(wb.getIntroduction() == null || wb.getIntroduction().length() < 2){
  				holder.strlong.setVisibility(View.GONE);	
  			}else{
  				holder.strlong.setVisibility(View.VISIBLE);	
  				holder.strlong.setText( wb.getIntroduction() );	
  			}  			
  			return convertView;	 			
  		}		
  	}	    
  	 
}