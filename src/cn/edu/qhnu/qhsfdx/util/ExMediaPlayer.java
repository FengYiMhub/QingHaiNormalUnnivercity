package cn.edu.qhnu.qhsfdx.util;


import java.io.File;
import java.io.IOException;

import cn.edu.qhnu.qhsfdx.R;
import cn.edu.qhnu.qhsfdx.database.DBHelper;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class ExMediaPlayer extends MediaPlayer{
	private Handler                 handler = new Handler();
    private MediaPlayer         mediaPlayer = new MediaPlayer();
	private SeekBar            ProceseekBar;
    private ImageView             BtnPlayer;
    private Activity              mActivity;
    private Context                mContext;
    private String                  tabname;
    private String                  keyname;
    private String                   mp3url = null;
    private int                         _id;
    private cn.edu.qhnu.qhsfdx.database.DBDao    DBDao;
    private boolean             MediaRdyFlg = false;
    private SQLiteDatabase db;
    //####################################################################################//    
	public ExMediaPlayer(SQLiteDatabase db,Activity mContext, int SeekBarId, int BtnPlayerId, String tabname, String keyname, int _id) {
		this.db            = db;
		this.mActivity     = mContext;
		this.mContext      = mContext;
		this.ProceseekBar  = (SeekBar  )this.mActivity.findViewById(SeekBarId);
		this.BtnPlayer     = (ImageView)this.mActivity.findViewById(BtnPlayerId);
		this.tabname       = tabname;
		this.keyname       = keyname;
		this._id           = _id;
		
		
  	    mediaPlayer.setOnCompletionListener(new OnCompletionListener(){
			public void onCompletion(MediaPlayer arg0) {
				BtnPlayer.setImageResource(R.drawable.play); 				
			}
  	    });
  	    
  	    BtnPlayer.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				if (mediaPlayer.isPlaying()) 
					StopPlayer();
	            else
	            	StartPlayer();
			}  	    	
  	    });
  	    
  	  	ProceseekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
	        public void onProgressChanged(SeekBar seekBar, int progress,  boolean fromUser) {
	            if (fromUser==true) {
	                mediaPlayer.seekTo(progress);
	            }
	        }
	        public void onStartTrackingTouch(SeekBar seekBar) { }
	        public void onStopTrackingTouch(SeekBar seekBar)  { }	  	    	
  	    });	
  	  	
  	  	mp3url = getMp3Url();
  	  	if(mp3url == null || mp3url.length() == 0){
			Toast.makeText(mContext, "û���ҵ���Ƶ�ļ�!", 200).show();
			MediaRdyFlg = false;
		}else{
       		mediaPlayer.reset();
       		try {
				mediaPlayer.setDataSource(mp3url);
				mediaPlayer.prepare();	
			} catch (IOException e) {
				e.printStackTrace();
			}	
			MediaRdyFlg = true;
		}  	  	
	} 
    //####################################################################################//	
	private String getMp3Url(){
		String temp = "";
		DBDao = new cn.edu.qhnu.qhsfdx.database.DBDao();
  	 	temp = DBDao.get_mp3_file(db, tabname, keyname, _id);  
  	  	return temp;
	}
	
	public void removeMp3File(){
		if(mp3url != null && mp3url.length() > 0){
			File file = new File(mp3url);
	        if(file.exists())
	        	file.delete();
		}		
	}
	
	public void StartPlayer()
    {
		if(!MediaRdyFlg){
			Toast.makeText(mContext, "播放音频", 200).show();
			return;
		}		
		
    	if(mediaPlayer.isPlaying()){
    		mediaPlayer.reset();  
    	}else{
    		mediaPlayer.start();       
    	}     
    	BtnPlayer.setImageResource(R.drawable.pause);
        StrartbarUpdate();   	
    }
    
    public void StopPlayer()
    {
	/*	if(!MediaRdyFlg){
			Toast.makeText(mContext, "û���ҵ���Ƶ�ļ�!", 200).show();
			return;
		}*/
		
    	if(mediaPlayer.isPlaying()){
	        mediaPlayer.pause();
	        BtnPlayer.setImageResource(R.drawable.play);      		
    	}
    }      
    
    //####################################################################################//
    void StrartbarUpdate()
    {
        handler.post(r);
    }
    
    Runnable r = new Runnable(){
        public void run() 
        {
            int CurrentPosition = mediaPlayer.getCurrentPosition();
            int mMax = mediaPlayer.getDuration();
            ProceseekBar.setMax(mMax);
            ProceseekBar.setProgress(CurrentPosition);
            handler.postDelayed(r, 100);
        }
    };	
}

//####################################################################################//
//####################################################################################//
//####################################################################################//
//####################################################################################//
