/**
 * 
 */
package cn.edu.qhnu.qhsfdx.ui;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.Booking;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BookingFragment extends Fragment {
	
	private View parentView;	
	private FragmentActivity parentActivity;	
	private TopBar topBar;	
	private SQLiteDatabase db;
	private Button button;
	private Booking Booking;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_booking, container, false);
		return view;		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		parentActivity = getActivity();
		parentView = getView();
		db = DBHelper.getDb(parentActivity.getApplicationContext());
		Booking = DBDao.findBookingById(db, 1);
		
		
		topBar = (TopBar) parentView.findViewById(R.id.topBar);
		topBar.setTitle("路线查询");		
		topBar.removeLeftButton();		
		topBar.removeRightButton();		
		button = (Button) parentView.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(parentActivity, BookingDetailList.class);				
		        startActivity(i); }
		});
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
