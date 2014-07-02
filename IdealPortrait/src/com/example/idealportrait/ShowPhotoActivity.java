package com.example.idealportrait;

import java.io.File;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Show photo with editable caption, detection of gestures
 * @author OAF
 *
 */
public class ShowPhotoActivity extends Activity{
	
	public ImageView image;
	public static EditText text;
	public File f;
	private GestureDetectorCompat mDetector; 

	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.show_layout);
		
        f = Files.createFile(getIntent().getStringExtra("ImageName"));
        mDetector = new GestureDetectorCompat(this, new MyGestureListener(f,this,this));

        
		text = (EditText) findViewById(R.id.text_image);        
		image = (ImageView) findViewById(R.id.image_preview);
       
		if (f.exists()) {
		  Drawable d = Drawable.createFromPath(f.getAbsolutePath());
		  image.setImageDrawable(d);
		}
		
		
	}
	
    @Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    
}
