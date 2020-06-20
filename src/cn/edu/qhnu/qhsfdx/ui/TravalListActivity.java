package cn.edu.qhnu.qhsfdx.ui;

import java.util.List;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.TravalDetail;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TravalListActivity extends Activity {

	private TopBar topBar;
	private SQLiteDatabase db;
	private  ListView        mylist;
	private  List<TravalDetail>  mytraval_details;
	private  int           traval_id;
	private  String      traval_name;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list); 
        topBar = (TopBar) findViewById(R.id.topBar);
        mylist   = (ListView)findViewById(R.id.mylist);
        mylist.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {				
				Intent mIntent = new Intent();
				mIntent.putExtra("traval_detail_id",     mytraval_details.get(arg2).get_id());
				mIntent.putExtra("traval_detail_name",   traval_name);
				mIntent.setClass(TravalListActivity.this, TravalListShowActivity.class);
				startActivity(mIntent);				
			}        	
        });
        topBar.setLeftButton("", new OnLeftButtonClickListener() {
    		
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				TravalListActivity.this.finish();
			}
		});	
			
		topBar.hiddenRightButton();
        
        Intent mIntent = this.getIntent();
        if(mIntent.hasExtra("traval_id")){
        	traval_id   = mIntent.getIntExtra("traval_id", 0);
        	traval_name = mIntent.getStringExtra("traval_name");
        }
        topBar.setTitle(traval_name);
        db = DBHelper.getDb(this);
        mytraval_details = DBDao.findAllTravalDetailById(db, traval_id); 
    	if(mytraval_details.size() > 0){    		
    		TravalListAdapater mySceneAgainAdapater = new TravalListAdapater(this);
    		mylist.setAdapter(mySceneAgainAdapater);
    	}
    }

	class TravalListAdapater extends BaseAdapter {		
		
		private Context            mContext;
		TravalListAdapater(Context myContext){
			this.mContext = myContext;
		}
	
		public int getCount() { return mytraval_details.size(); }
		public Object getItem(int position) { return mytraval_details.get(position); 	}
		public long getItemId(int position) { return position; }
	
	    class ViewHolder { 
	    	private ImageView wbicon;
	    	private TextView  wbname; 	
	    	private TextView  wbshort; 	
	    } 
	
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder        holder;				
			TravalDetail  wb = mytraval_details.get(position);					
			if (convertView == null) { 
				holder = new ViewHolder(); 
				convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_list_item, null); 
				holder.wbshort= (TextView)  convertView.findViewById(R.id.textView_sceneshort);	
				holder.wbname = (TextView)  convertView.findViewById(R.id.textView_scenename );	
				holder.wbicon = (ImageView) convertView.findViewById(R.id.imageView_sceneicon); 	 			
				convertView.setTag(holder); 
	        } else { 
	            holder = (ViewHolder) convertView.getTag(); 
	        } 		
			holder.wbname.setText       ( wb.getName()        );			
			holder.wbshort.setText      ( wb.getIntro_short() );	
			holder.wbicon.setImageBitmap( BitmapUtil.convertFromByteToBitmap(wb.getIcon()));
			
			return convertView;				
		}		
	}	    	 

}


