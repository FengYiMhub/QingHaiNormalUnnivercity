package cn.edu.qhnu.qhsfdx;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.ui.BookingFragment;
import cn.edu.qhnu.qhsfdx.ui.MoreFragment;
import cn.edu.qhnu.qhsfdx.ui.SceneFragment;
import cn.edu.qhnu.qhsfdx.ui.TravelFragment;
public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	public void initView() {
	    SceneFragment chat = new SceneFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.bottom, chat).commit();
		RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab);
		myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				BookingFragment Booking;
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.Main:
					SceneFragment chat = new SceneFragment();
					getSupportFragmentManager().beginTransaction().replace(R.id.bottom, chat)
							.commit();
					break;
				case R.id.rbTravel:
					TravelFragment travel = null;
					if (travel==null) {
						travel =new TravelFragment();
					}
					Log.i("MyFragment", "FragmentAddress");
					getSupportFragmentManager().beginTransaction().replace(R.id.bottom, travel).commit();
					break;
				case R.id.rbBooking:
					Booking = new BookingFragment();
					getSupportFragmentManager().beginTransaction().replace(R.id.bottom, Booking)
							.commit();
					break;
				case R.id.rbMore:
					MoreFragment More = new MoreFragment();
					getSupportFragmentManager().beginTransaction().replace(R.id.bottom, More)
							.commit();
					break;
				default:
					break;
			}
			}
		});
}
}
