package com.example.navigation;

import android.media.AudioManager;
import android.media.ToneGenerator;

public abstract class BeeperAdapter {

	protected ToneGenerator tone;
	
	public BeeperAdapter(){
		tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION,100);
	}
	
	public abstract boolean beep(Navigation navigation);
	
	public void release(){
		tone.stopTone();
		tone.release();
	}
}
