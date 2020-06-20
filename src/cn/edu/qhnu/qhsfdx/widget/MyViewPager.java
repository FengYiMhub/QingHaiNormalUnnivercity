package cn.edu.qhnu.qhsfdx.widget;

import java.util.ArrayList;
import java.util.List;
import cn.edu.qhnu.qhsfdx.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

/**
 * 自定义带有底部原点的ViewPager
 */
public class MyViewPager extends LinearLayout implements Runnable {

	private ViewPager viewPager;
	private LinearLayout viewDots;
	private List<ImageView> dots;
	private List<View> views;
	private int position = 0;
	private boolean isContinue = true;
	private float dotsViewHeight;
	private float dotsSpacing;
	private Drawable dotsFocusImage;
	private Drawable dotsBlurImage;
	private ScaleType scaleType;
	private int gravity;
	private Drawable dotsBackground;
	private float dotsBgAlpha;
	private int changeInterval;

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyViewPager, 0, 0);

		try {
			dotsViewHeight = a.getDimension(
					R.styleable.MyViewPager_dotsViewHeight, 40);
			dotsSpacing = a
					.getDimension(R.styleable.MyViewPager_dotsSpacing, 0);
			dotsFocusImage = a
					.getDrawable(R.styleable.MyViewPager_dotsFocusImage);
			dotsBlurImage = a
					.getDrawable(R.styleable.MyViewPager_dotsBlurImage);
			int scaleNum = a.getInteger(
					R.styleable.MyViewPager_android_scaleType,
					ScaleType.FIT_XY.ordinal());
			scaleType = ScaleType.FIT_XY;
			for (ScaleType st : ScaleType.values()) {
				if (st.ordinal() == scaleNum) {
					scaleType = st;
					break;
				}
			}
			gravity = a.getInteger(R.styleable.MyViewPager_android_gravity,
					Gravity.CENTER);
			dotsBackground = a
					.getDrawable(R.styleable.MyViewPager_dotsBackground);
			dotsBgAlpha = a.getFloat(R.styleable.MyViewPager_dotsBgAlpha, 0);
			changeInterval = a.getInteger(R.styleable.MyViewPager_changeInterval, 1000);

			if (dotsFocusImage == null) {
				dotsFocusImage = getResources().getDrawable(
						R.drawable.page_indicator_focused);
			}
			if (dotsBlurImage == null) {
				dotsBlurImage = getResources().getDrawable(
						R.drawable.page_indicator_unfocused);
			}
		} finally {
			a.recycle();
		}

		initView();
	}
	@SuppressLint("NewApi")
	private void initView() {
		// TODO Auto-generated method stub
		viewPager = new ViewPager(getContext());
		viewDots = new LinearLayout(getContext());
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		addView(viewPager, lp);
		if (dotsBackground != null) {
			dotsBackground.setAlpha((int) (dotsBgAlpha * 255));
			viewDots.setBackground(dotsBackground);
		}
		viewDots.setGravity(gravity);
		addView(viewDots, lp);
	}
	public void addDots(int num) {
		viewDots.removeAllViews();
		dots = new ArrayList<ImageView>();
		for (int i = 0; i < num; i++) {
			ImageView dot = new ImageView(getContext());
			int margin = (int) (dotsSpacing / 2);
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			lp.leftMargin = lp.rightMargin = margin;
			if (dots.size() == 0)
				dot.setImageDrawable(dotsFocusImage);
			else
				dot.setImageDrawable(dotsBlurImage);
			dots.add(dot);
			viewDots.addView(dot, lp);
		}
	}
	public void switchToDot(int index) {//将所有的点都化为非聚焦的，最后在将聚焦的点加上去
		for (int i = 0; i < dots.size(); i++) {
			dots.get(i).setImageDrawable(dotsBlurImage);
		}
		dots.get(index).setImageDrawable(dotsFocusImage);
	}
	public void setViewPagerViews(List<View> views) {
		this.views = views;
		addDots(views.size());
		viewPager.setAdapter(new ViewPagerAdapter(views, scaleType));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				// TODO Auto-generated method stub
				position = index;
				switchToDot(index);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		viewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionevent) {
				// TODO Auto-generated method stub
				switch (motionevent.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});
		new Thread(this).start();
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		View child = this.getChildAt(0);
		child.layout(0, 0, getWidth(), getHeight());
		if (changed) {
			child = this.getChildAt(1);
			child.measure(r - l, (int) dotsViewHeight);
			child.layout(0, getHeight() - (int) dotsViewHeight, getWidth(),
					getHeight());
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (isContinue) {
				pageHandler.sendEmptyMessage(position);
				position = (position + 1) % views.size();
				try {
					Thread.sleep(changeInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	Handler pageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}
	};

}
