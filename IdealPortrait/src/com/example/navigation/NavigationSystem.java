package com.example.navigation;

import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class NavigationSystem implements OnInitListener, Runnable{
	
	private int width;
	private int height;
	private Locale locale;
	private TextToSpeech tts;
	private NavigatorAdapter navigator;
	private BlockingQueue<String> queue;
	private transient boolean shutdown = false;
	private transient boolean ready = false;
	private boolean catchable = false;
	
	/**
	 * Creates Navigation System which can handle easy Beeps, Vibrations, TextToSpeech
	 * @param context in which TTS should speak
	 * @param locale Language in which TTS should speak
	 */
	public NavigationSystem(Context context, Locale locale, Activity activity){
		this.locale = locale;
		queue = new LinkedBlockingQueue<String>();
		tts = new TextToSpeech(context, this);
		cameraViewSize(context);
		navigator = new Navigation_perc2(new Beeper_original(),new Vibrator_original(context),width,height);
	}
	
	/**
	 * Finds the width and height of camera view
	 * @param context
	 */
	private void cameraViewSize(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
	}
	
	/**
	 * Runs the navigation coresponding to the score of the face
	 * @param face camera recognition
	 */
	public void navigate(Rect rect){
		catchable = navigator.navigate(rect);
		Log.d("catchable", "" + catchable);
	}
	
	/**
	 * Adds text which is meant to be said to the queue for later processing
	 * @param textToSay
	 * @throws InterruptedException 
	 */
	public void speak(String textToSay){
		try {
			queue.put(textToSay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Turns off Navigation System
	 */
	public void shutdown(){
		shutdown = true;
		navigator.release();
		tts.stop();
		tts.shutdown();
	}

	@Override
	public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(locale);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "Language is not supported");
            }else{
            	ready = true;
            }
        } else {
            Log.e("error", "Failed  to Initilize!");
        }
	}
	
	/**
	 * Sings if the Navigation System is fully operational
	 * @returns true if the System is fully operational
	 */
	public boolean isReady(){
		return ready;
	}
	
	/**
	 * Navigation system determines if the photo is ready to be taken
	 * @return true if yes
	 */
	public boolean isCatchable(){
		boolean temp = catchable;
		catchable = false;
		return temp;
	}
	
	/**
	 * Waits until the Navigation System is fully operational
	 * Tries if there is something to say and that reproduces the voice
	 */
	@Override
	public synchronized void run() {
		while(!ready){
			try{
				wait(250);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		while(!shutdown){
			try {
				tts.speak(queue.take(), TextToSpeech.QUEUE_ADD, null);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}
