package cn.edu.qhnu.qhsfdx.ui;

import java.util.List;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.TravelMedia;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.util.BitmapUtil;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.TopBar.OnLeftButtonClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TravalListShowActivity extends Activity {
	
	private TopBar topBar;
	private  List<TravelMedia>    mytraval_medias; 
	private  ListView        listView_travalshow; 
	private  int           traval_detail_id = 0;
	private  String      traval_detail_name = "";
	private  int                  traval_id = 0;
	private  String             traval_name = "";
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travallistshow);
		SQLiteDatabase db = DBHelper.getDb(this);

		listView_travalshow = (ListView) findViewById(R.id.listView_travalshow);
		topBar = (TopBar) findViewById(R.id.topBar);
		topBar.setLeftButton("", new OnLeftButtonClickListener() {

			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				TravalListShowActivity.this.finish();
			}
		});

		topBar.hiddenRightButton();
        Intent intent = getIntent();
        if(intent.hasExtra("traval_detail_id")) {
        	traval_detail_id   = intent.getIntExtra("traval_detail_id", 0);    			   	
        	traval_detail_name = intent.getStringExtra("traval_detail_name");     
        	mytraval_medias = DBDao.findTravalmediaById(db, traval_detail_id, false);        	
        	topBar.setTitle(traval_detail_name);
        }else if(intent.hasExtra("traval_id")){
        	traval_id          = intent.getIntExtra("traval_id", 0);    				 	
        	traval_name        = intent.getStringExtra("traval_name");   
        	mytraval_medias    = DBDao.findTravalmediaById(db, traval_id, true);         	
        	topBar.setTitle(traval_name);
        }
  	    TravalListShowAdapater mTravalListShowAdapater = new TravalListShowAdapater(this);
  	    listView_travalshow.setAdapter(mTravalListShowAdapater);
 	   	       	  
    }    
	
  	class TravalListShowAdapater extends BaseAdapter {		
  		private Context            mContext;

  		TravalListShowAdapater(Context myContext){
  			this.mContext = myContext;
  		}
  	
  		public int getCount() { return mytraval_medias.size(); }
  		public Object getItem(int position) { return mytraval_medias.get(position); }
  		public long getItemId(int position) { return position; }
  	
  	    class ViewHolder { 
  	    	private TextView    title; 	
  	    	private ImageView    icon;
  	    	private TextView  strlong; 	
  	    } 
  	
  		public View getView(int position, View convertView, ViewGroup parent) {
  			ViewHolder        holder;				
  			TravelMedia  wb = mytraval_medias.get(position);			
  			if (convertView == null) { 
  				holder = new ViewHolder(); 
  				convertView=LayoutInflater.from(mContext).inflate(R.layout.travallistshowitem, null); 
  				holder.title   = (TextView ) convertView.findViewById(R.id.textView_traval_text);	
  				holder.icon    = (ImageView) convertView.findViewById(R.id.imageView_traval_icon );	
  				holder.strlong = (TextView ) convertView.findViewById(R.id.textView_traval_long); 	 			
  				convertView.setTag(holder); 
  	        } else { 
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

