package cn.edu.qhnu.qhsfdx.ui;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.Traval;
import cn.edu.qhnu.qhsfdx.database.DBDao;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import cn.edu.qhnu.qhsfdx.widget.CircleFlowIndicator;
import cn.edu.qhnu.qhsfdx.widget.TopBar;
import cn.edu.qhnu.qhsfdx.widget.ViewFlow;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelFragment extends Fragment {
	private SQLiteDatabase db;
	private View parentView;
	private FragmentActivity parentActivity;
	private TopBar topBar;
	private cn.edu.qhnu.qhsfdx.widget.ViewFlow mviewFlow;
	private List<Traval> mtravalList = new ArrayList<Traval>();
	private final ArrayList<View> views = new ArrayList<View>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_travel, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		parentActivity = getActivity();
		parentView = getView();
		db = DBHelper.getDb(parentActivity.getApplicationContext());
		mtravalList = DBDao.findAllTraval(db);
		topBar = (TopBar) parentView.findViewById(R.id.topBar);
		topBar.setTitle("院系介绍");
		topBar.removeLeftButton();
		topBar.removeRightButton();

		if (mtravalList.size() > 0) {
			int i, size = mtravalList.size(), cnt = 6;
			int pages = size / cnt, lstcnt = size % cnt;

			if (lstcnt != 0)
				pages++;
			LayoutInflater mLi = LayoutInflater.from(parentActivity
					.getApplicationContext());

			for (i = 0; i < pages; i++) {
				View view = mLi.inflate(R.layout.travalmenupage, null);
				views.add(view);

				GridView gridView = (GridView) view
						.findViewById(R.id.gridView_travalmenu1);
				gridView.setNumColumns(2);

				List<Traval> itemtraval = new ArrayList<Traval>();
				if (i != pages - 1) {
					for (int j = 0; j < cnt; j++)
						itemtraval.add(mtravalList.get(i * cnt + j));
				} else {
					for (int j = 0; j < lstcnt; j++)
						itemtraval.add(mtravalList.get(i * cnt + j));
					for (int j = lstcnt; j < 6; j++) {
						Traval mytraval1 = new Traval();
						mytraval1.set_id(-1);
						itemtraval.add(mytraval1);
					}
				}

				ActiveAdapaterly mActiveAdapaterly1 = new ActiveAdapaterly(
						parentActivity, itemtraval);
				gridView.setAdapter(mActiveAdapaterly1);
				gridView.setSelector(R.drawable.listviewhideyellow);
			}
		}
		mviewFlow = (ViewFlow) parentView.findViewById(R.id.viewflow);
		mviewFlow.setAdapter(
				new ImageAdapter(parentActivity.getApplicationContext()), 0);
		CircleFlowIndicator indic = (CircleFlowIndicator) parentView
				.findViewById(R.id.viewflowindic);
		mviewFlow.setFlowIndicator(indic);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mviewFlow.onConfigurationChanged(newConfig);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	class ImageAdapter extends BaseAdapter {

		public ImageAdapter(Context context) {
			
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = views.get(position);
			}
			return convertView;
		}
	}

	class ActiveAdapaterly extends BaseAdapter {

		private Context mContext;
		private List<Traval> eventList;

		ActiveAdapaterly(Context myContext, List<Traval> eventList) {
			this.mContext = myContext;
			this.eventList = eventList;
		}

		public int getCount() {
			return eventList.size();
		}

		public Object getItem(int position) {
			return eventList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			private ImageView wbicon;
			private TextView wbtext;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			Traval wb = eventList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(
						ActiveAdapaterly.this.mContext).inflate(
						R.layout.travalmenupageitem, null);
				holder.wbtext = (TextView) convertView
						.findViewById(R.id.textView_travaltext);
				holder.wbtext.setTextSize(15);
				holder.wbicon = (ImageView) convertView
						.findViewById(R.id.imageView_travalicon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (wb != null) {

				if (wb.get_id() == -1) {
					holder.wbtext.setVisibility(View.INVISIBLE);
					holder.wbicon.setVisibility(View.INVISIBLE);
					return convertView;
				} else {
					holder.wbtext.setVisibility(View.VISIBLE);
					holder.wbicon.setVisibility(View.VISIBLE);
				}
				holder.wbtext.setText(wb.getName(),
						TextView.BufferType.SPANNABLE);
				holder.wbicon.setTag(position);
				holder.wbicon.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						String tmp = arg0.getTag().toString();
						int position = Integer.valueOf(tmp);
						Intent mIntent = new Intent();
						if(eventList.get(position).get_id() != 255)
						{
						mIntent.putExtra("traval_id", eventList.get(position)
								.get_id());
						mIntent.putExtra("traval_name", eventList.get(position)
								.getName());
						if (eventList.get(position).getTag() == 0)
							mIntent.setClass(mContext, TravalListShowActivity.class);
						else
							mIntent.setClass(mContext,
									TravalListShowActivity.class);
						}
						startActivity(mIntent);
					}
				});
			}
			return convertView;
		}
	}

}
