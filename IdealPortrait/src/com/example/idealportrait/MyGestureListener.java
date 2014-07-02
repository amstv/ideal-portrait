package com.example.idealportrait;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private File f;
    private Context c;
    private Activity a;
    
    /**
     * Creates gesture listener 
     * @param f file for sharing in intent
     * @param c parent context
     * @param a parent activity
     */
    public MyGestureListener(File f,Context c,Activity a){
    	this.f = f;
    	this.c = c;
    	this.a = a;
    }
   
    /* (non-Javadoc)
     * Override method, start intent when doubleTap detected
     * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android.view.MotionEvent)
     */
    @Override
    public boolean onDoubleTap(MotionEvent event) { 
		c.startActivity(Intent.createChooser(Files.shareFile(f,ShowPhotoActivity.text.getText().toString()), "Share Image"));
        return true;
    }

    /* (non-Javadoc)
     * Override method, go back from parent activity when fling detected
     * @see android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
     */
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, 
            float velocityX, float velocityY) {
        a.onBackPressed();
    	return true;
    }
}