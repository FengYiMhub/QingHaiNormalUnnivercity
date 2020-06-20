package cn.edu.qhnu.qhsfdx.widget;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.bean.Scene;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class mapScene {
	private ArrayList <MyScene> mysceneList = new ArrayList<MyScene>();
	private Context            mContext = null;
	private  Bitmap             gintama;	
	private  ImageView       gimageView;
	private  ViewGroup       viewgroup;	
	private static final int    NONE = 0;
	private static final int    DRAG = 1;
	private static final int    ZOOM = 2;
	private static final int  ZOOMIN = 1;
	private static final int ZOOMOUT = 2;
	
	private static int          mode = NONE;
	private static int      zoommode = ZOOMOUT;	
	
	private  boolean     matrixCheck = false;
	
	private	 int	     gwidth;
	private	 int	    gheight; 
	private  int    widthScreen;
	private  int   heightScreen;
	
	  

	private  int    basewidthScreen=480;//480
	private  int   baseheightScreen=800;//800
	
	
	//private float scaleX;
	//private float scaleY;
	
	private  long         count = 0;
	private  long      firClick = 0;
	private  long      secClick = 0;
	
	private	 float	   CurMapX1;
	private	 float	   CurMapY1;
	private	 float	   CurMapX2;
	private	 float	   CurMapY2;
	private	 float	   CurMapY3;
	private	 float	   CurMapY4;
	
	private  float   proportion;
	private	 float	minMultiple;
	private	 float	   repvalue; //图片初始放大倍数\数据库内图钉显示的最小值  \放大倍数修正值
	private  float       pointx;
	private  float       pointy;
	private  float       TScale = 1; 	
	
	private  float x_down  = 0, x_up = 0;
	private  float y_down  = 0, y_up = 0;
	private  float oldDist = 1f;
	
	private  PointF mid         = new PointF();
	private  Matrix matrix      = new Matrix();
	private  Matrix matrix1     = new Matrix();
	private  Matrix savedMatrix = new Matrix();
	public mapScene(Context  mContext){
		this.mContext  =  mContext;
	}
	
	public void setLayout(ViewGroup viewgroup, ImageView gimageView, Bitmap gintama){
		this.viewgroup = viewgroup;
		this.gimageView = gimageView;
		this.gintama    = gintama;
		gimageView.setImageBitmap(gintama);
	}
	
	public void setdata(List<Scene> sceneList) {

		if (sceneList != null) {
			for (Scene scene : sceneList) {
				MyScene myscene = new MyScene(mContext, null);
				myscene.id = scene.get_id();
				myscene.sceneryName = scene.getName();
				myscene.sceneryX = scene.getX();
				myscene.sceneryY = scene.getY();
				myscene.multiple = scene.getType();

				myscene.PostionImage = BitmapFactory.decodeResource(
						mContext.getResources(), R.drawable.mapspot);

				mysceneList.add(myscene);
				viewgroup.addView(myscene);
			}
			minMultiple = 0.2f;
		}
	}

	private View getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	private class MyScene extends View {
		protected int id;
		protected Bitmap PostionImage;
		protected float multiple;
		protected String sceneryName;
		protected float sceneryX;
		protected float sceneryY;
		private String text = "";
		private Rect src, dst;

		public MyScene(Context context, AttributeSet attrs) {
			super(context, attrs);
			invalidate();
		}

		public void setbitmaptext(String text) {
			this.text = text;
		}

		public void setsrcdst(Rect src, Rect dst) {
			this.src = src;
			this.dst = dst;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			if (dst.top < 75)
				paint.setAlpha(0x00);
			else
				paint.setAlpha(0xf0);

			
			canvas.drawBitmap(PostionImage, src, dst, paint);
			if (dst.top < 75)
				paint.setColor(0x00);
			else
			paint.setColor(Color.WHITE);
			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);
			paint.setTextSize(13);
			paint.setFakeBoldText(true);

			int textlen = text.length() > 3 ? text.length() - 3 : 0;
			canvas.drawText(text,
					dst.left + (PostionImage.getWidth() + textlen * 20)/2 
							- (text.length() * 18) / 2 + 8, dst.top
							+ PostionImage.getHeight() / 2 + 5, paint);
		}
	}

	public interface onClickCallback {
		public void ClickCallback(int id);
	}

	public void onCreate(final onClickCallback myCallback, int ad_height) {
		// 要获取屏幕的宽和高等参数，首先需要声明一个DisplayMetrics对象，屏幕的宽高等属性存放在这个对象中
		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getApplicationContext().getResources()
				.getDisplayMetrics();

		widthScreen = dm.widthPixels;
		heightScreen = dm.heightPixels - ad_height;// 去掉消息栏和标题栏

		//scaleX=widthScreen/basewidthScreen;
		//scaleY = heightScreen/baseheightScreen;
		
		Log.i("pkuan", widthScreen+"");
		Log.i("pgao", heightScreen+"");
		
		// 获得未变化前图片的宽高及其比例
		gwidth = gintama.getWidth();
		gheight = gintama.getHeight();
		Log.i("kuan", gwidth+"");
		Log.i("gao", gheight+"");
		matrix1.set(savedMatrix);
		float scaletemp = (float) heightScreen / (float) gheight;
		matrix1.postScale(scaletemp, scaletemp, 0, 0);
		matrix.set(matrix1);

		GetCurMapValue();
		matrix1.postTranslate(-600, (heightScreen - CurMapY3));
		matrix.set(matrix1);

		gimageView.setImageMatrix(matrix);

		GetCurMapValue();

		// 最小显示后图片的放大倍数，做为修正使用
		proportion = GetBmpMultiple();
		repvalue = minMultiple / proportion;
		DrawPostion();

		gimageView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				int id = GetClickId();
				myCallback.ClickCallback(id);
			}
		});

		gimageView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {  //单手指按下   
				case MotionEvent.ACTION_DOWN:
					count++;
					if (count == 1) {
						secClick = firClick = System.currentTimeMillis();
					} else if (count > 1) {
						int rtry = 2;
						secClick = System.currentTimeMillis();
						if (secClick - firClick < 250) {

							do {
								// 双击事件
								if (zoommode == ZOOMIN) {
									TScale = 1f;
									zoommode = ZOOMOUT;
								} else if (zoommode == ZOOMOUT) {
									TScale = 2f;
									zoommode = ZOOMIN;
								}

								matrix1.set(savedMatrix);
								matrix1.postScale(TScale, TScale, 0, 0);// x_down,
																		// y_down);//
																		// 縮放
								matrixCheck = matrixCheck();
								if (matrixCheck == false) {
									matrix.set(matrix1);
									gimageView.setImageMatrix(matrix);
									DrawPostion();
									x_up = y_up = 0;
									BmpRetHome();
									if ((CurMapX2 - CurMapX1) < widthScreen)
										BitmapDisReset();
									break;
								}
							} while (matrixCheck && (rtry-- > 0));
						} else {
							firClick = secClick;
							count = 1;
						}
					}
					mode = DRAG;
					x_up = y_up = 0;
					x_down = event.getX();
					y_down = event.getY();
					savedMatrix.set(matrix);
					return false;
				case MotionEvent.ACTION_POINTER_DOWN:
					mode = ZOOM;
					oldDist = spacing(event);
					savedMatrix.set(matrix);
					midPoint(mid, event);
					break;
				case MotionEvent.ACTION_MOVE:
					matrix1.set(savedMatrix);
					if (mode == ZOOM) {
						float newDist = spacing(event);
						float scale = newDist / oldDist;
						matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
						Log.i("mid.x", ""+mid.x);
						Log.i("mid.y", ""+mid.y);
						
						matrixCheck = matrixCheck();
						if (matrixCheck == false) {
							matrix.set(matrix1);
							gimageView.setImageMatrix(matrix);
							DrawPostion();
							x_up = event.getX();
							y_up = event.getY();
						}
					} else if (mode == DRAG) {
						matrix1.postTranslate(event.getX() - x_down,
								event.getY() - y_down);// 平移
						matrixCheck = matrixCheck();
						if (matrixCheck == false) {
							matrix.set(matrix1);
							gimageView.setImageMatrix(matrix);
							DrawPostion();
							x_up = event.getX();
							y_up = event.getY();
						}
					}
					break;

				case MotionEvent.ACTION_UP:
					x_up = event.getX();
					y_up = event.getY();
					BmpRetHome();
					return false;
				case MotionEvent.ACTION_POINTER_UP:
					x_up = y_up = 0;
					BmpRetHome();
					if ((CurMapX2 - CurMapX1) < widthScreen)
						BitmapDisReset();
					mode = NONE;
					break;
				}
				return true;
			}
		});
	}

	// 获取点击景点ID
	private int GetClickId() {
		for (int i = 0; i < mysceneList.size(); i++) {
			if (GetBmpMultiple() * repvalue < mysceneList.get(i).multiple)
				continue;
			if (isOnclick(mysceneList.get(i).sceneryX*widthScreen/basewidthScreen,
					mysceneList.get(i).sceneryY*heightScreen/baseheightScreen,
					mysceneList.get(i).PostionImage)) {
				return i;
			}
		}
		return 0xff;
	}

	// 将图片归位
	private void BmpRetHome() {
		float dx = 0, dy = 0;
		if (x_up == x_down && y_up == y_down)
			return;
		if (CurMapX1 > 0) {
			dx = -CurMapX1;
			if (CurMapY1 > 0)
				dy = -CurMapY1;
			else if (CurMapY3 < heightScreen)
				dy = heightScreen - CurMapY3;
			else
				dy = 0;
		} else if (CurMapX2 < widthScreen) {
			dx = widthScreen - CurMapX2;
			if (CurMapY2 > 0)
				dy = -CurMapY2;
			else if (CurMapY4 < heightScreen)
				dy = heightScreen - CurMapY4;
			else
				dy = 0;
		} else if (CurMapY1 > 0)
			dy = -CurMapY1;
		else if (CurMapY4 < heightScreen)
			dy = heightScreen - CurMapY4;

		matrix1.set(matrix);
		matrix1.preTranslate(dx / ((CurMapX2 - CurMapX1) / gwidth), dy
				/ ((CurMapX2 - CurMapX1) / gwidth));
		
		if (matrixCheck())
			return;
		matrix.set(matrix1);

		if (CurMapY4 < heightScreen) {
			//matrix1.postTranslate(0, (heightScreen - CurMapY4));
			matrix1.postTranslate(mid.x/16,mid.y/2);
			GetCurMapValue();
			matrix.set(matrix1);
		}
		gimageView.setImageMatrix(matrix);
		DrawPostion();
	}

	// 返回图片放大倍数
	private float GetBmpMultiple() {
		float[] f = new float[9];
		matrix1.getValues(f);
		// 图片4个顶点的坐标
		float x1 = f[0] * 0 + f[1] * 0 + f[2];
		float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
		return (x2 - x1) / gintama.getWidth();
	}

	private boolean matrixCheck() {
		float[] f = new float[9];
		matrix1.getValues(f);
		// 图片4个顶点的坐标
		float x1 = f[0] * 0 + f[1] * 0 + f[2];
		float y1 = f[3] * 0 + f[4] * 0 + f[5];
		float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
		float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
		float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
		float y4 = f[3] * gintama.getWidth() + f[4] * gintama.getHeight()
				+ f[5];

		// 图片现高度
		float bitmapheight = y3 - y1;

		if ((x2 - x1) < widthScreen / 2 || bitmapheight > heightScreen * 3)
			return true;

		CurMapX1 = x1;
		CurMapY1 = y1;
		CurMapX2 = x2;
		CurMapY2 = y2;
		CurMapY3 = y3;
		CurMapY4 = y4;
		return false;
	}

	// 图片恢复原大小显示，需在图片初始化后调用
	public void BitmapDisReset() {
		// 先将图片归0,0坐标
		matrix1.set(matrix);

		// 动态算出缩放比例，
		float scaletemp = (float) widthScreen / (float) (CurMapX2 - CurMapX1);
		matrix1.postScale(scaletemp, scaletemp, 0, 0);
		GetCurMapValue();
		matrix1.postTranslate(-CurMapX1 / ((CurMapX2 - CurMapX1) / gwidth),
				-CurMapY1 / ((CurMapX2 - CurMapX1) / gwidth));
		GetCurMapValue();
		matrix.set(matrix1);
		gimageView.setImageMatrix(matrix);
		DrawPostion();
		x_up = y_up = 0;
		BmpRetHome();
	};

	// 获得当前图片的坐标给坐标全局变量
	private void GetCurMapValue() {
		float[] f = new float[9];
		matrix1.getValues(f);
		// 图片4个顶点的坐标
		float x1 = f[0] * 0 + f[1] * 0 + f[2];
		float y1 = f[3] * 0 + f[4] * 0 + f[5];
		float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
		float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
		float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
		float y4 = f[3] * gintama.getWidth() + f[4] * gintama.getHeight()
				+ f[5];
		CurMapX1 = x1;
		CurMapY1 = y1;
		CurMapX2 = x2;
		CurMapY2 = y2;
		CurMapY3 = y3;
		CurMapY4 = y4;
	}

	private void DrawPostion() {

		// src 这个是表示绘画图片的大小
		float CurWideproportion = 1; // 当前变换后的图片的宽度与初始图片宽度的比例
		if (CurMapX1 != CurMapX2)
			CurWideproportion = (CurMapX2 - CurMapX1) / gwidth;
		
		for (int i = 0; i < mysceneList.size(); i++) {

			Rect src = new Rect();// 图片
			Rect dst = new Rect();// 屏幕位置及尺寸
//*widthScreen/basewidthScreen;*heightScreen/baseheightScreen;
			pointx = mysceneList.get(i).sceneryX*widthScreen/basewidthScreen;
			pointy = mysceneList.get(i).sceneryY*heightScreen/baseheightScreen;
			pointx = (pointx * CurWideproportion) + CurMapX1
					- mysceneList.get(i).PostionImage.getWidth() / 2;
			pointy = (pointy * CurWideproportion) + CurMapY1
					- mysceneList.get(i).PostionImage.getHeight() / 2;

			src.left = 0; // 0,0
			src.top = 0;
			if (GetBmpMultiple() * repvalue < mysceneList.get(i).multiple)
				src.right = src.bottom = 0;
			else {
				src.right = mysceneList.get(i).PostionImage.getWidth();// mBitDestTop.getWidth();,这个是桌面图的宽度，
				src.bottom = mysceneList.get(i).PostionImage.getHeight();// mBitDestTop.getHeight()/2;//
																			// 这个是桌面图的高度的一半
			}

			// 下面的 dst 是表示 绘画这个图片的位置
			dst.left = (int) pointx; // 这个是可以改变的，也就是绘图的起点X位置
			Log.i("x", ""+dst.left);
	
			dst.top = (int) pointy; // 这个是图片的高度。 也就相当于 桌面图片绘画起点的Y坐标
			
			Log.i("y", ""+dst.top);
			
			int textlen = mysceneList.get(i).sceneryName.length() > 3 ? mysceneList
					.get(i).sceneryName.length() - 3 : 0;
			dst.right = (int) pointx
					+ mysceneList.get(i).PostionImage.getWidth() + textlen * 18; // 表示需绘画的图片的右上角
			dst.bottom = (int) pointy
					+ mysceneList.get(i).PostionImage.getHeight(); // 表示需绘画的图片的右下角

			if (GetBmpMultiple() * repvalue < mysceneList.get(i).multiple)
				mysceneList.get(i).setbitmaptext("");
			else
				mysceneList.get(i)
						.setbitmaptext(mysceneList.get(i).sceneryName);
			mysceneList.get(i).setsrcdst(src, dst);
			mysceneList.get(i).invalidate();

			src = null;
			dst = null;
			
		}
		
	}

	private float Labs(float x, float y) {
		if (y > x)
			return y - x;
		else
			return x - y;
	}

	// 判断目标是否被点击 按地图比例跟随
	private boolean isOnclick(float objectx, float objecty, Bitmap bitmap) {

		float clickx, clicky;
		float CurWideproportion = 1;

		// click 消抖动
		if (Labs(x_up, x_down) > 10 || Labs(y_up, y_down) > 10)
			return false;
		clickx = x_down - CurMapX1;
		clicky = y_down - CurMapY1;

		if (CurMapX1 != CurMapX2) {
			CurWideproportion = (CurMapX2 - CurMapX1) / gwidth;
		}
		objectx *= CurWideproportion;
		objecty *= CurWideproportion;
		if (Labs(clickx, objectx) < (bitmap.getWidth() / 2)
				&& Labs(clicky, objecty) < bitmap.getHeight())
			return true;
		return false;
	}

	// 触碰两点间距离
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// 取手势中心点
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}
