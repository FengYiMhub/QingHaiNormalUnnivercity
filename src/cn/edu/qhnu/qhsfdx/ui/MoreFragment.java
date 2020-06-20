/**
 * 
 */
package cn.edu.qhnu.qhsfdx.ui;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.More;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MoreFragment extends Fragment {
	
	private View parentView;	
	private FragmentActivity parentActivity;	
	private TopBar topBar;	
	private SQLiteDatabase db;
	private Button button1;
	private Button button2;
	private More more1;
	private More more2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_more, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		parentActivity = getActivity();
		parentView = getView();
		db = DBHelper.getDb(parentActivity.getApplicationContext());
		more1 = DBDao.findMoreById(db, 1);
		more2 = DBDao.findMoreById(db, 2);
		topBar = (TopBar) parentView.findViewById(R.id.topBar);
		topBar.setTitle("更多");	
		topBar.removeLeftButton();		
		topBar.removeRightButton();	
		button1 = (Button) parentView.findViewById(R.id.button1);
		button2 = (Button) parentView.findViewById(R.id.button2);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(parentActivity, CopyRight.class);
				startActivity(i);
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(parentActivity, AboutMe.class);
				startActivity(i);
			}
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
