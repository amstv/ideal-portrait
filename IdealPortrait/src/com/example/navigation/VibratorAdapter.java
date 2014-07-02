package com.example.navigation;

import android.content.Context;
import android.os.Vibrator;

public abstract class VibratorAdapter {
	
	protected Vibrator vibrator;
	
	public VibratorAdapter(Context context){
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public abstract boolean vibrate(Navigation navigation);

	public void release(){
		vibrator.cancel();
	}
}
