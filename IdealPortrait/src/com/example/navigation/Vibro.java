package com.example.navigation;

import android.content.Context;

public class Vibro extends VibratorAdapter {
	
	public Vibro(Context context) {
		super(context);
	}

	/**
	 * Vibrates the pattern
	 * @return true if vibrations are running
	 */
	public boolean vibrate(Navigation navigation){
		long[] pattern = null;
		
		if(navigation == Navigation.FAR){
			pattern = new long[]{0,50};
		}else if(navigation == Navigation.IDEAL){
			pattern = new long[]{0,50,30,50,30,50};
		} else if(navigation == Navigation.NEAR || navigation == Navigation.MEDIUM){
			pattern = new long[]{0,50,30,50};
		}	
		
		if(vibrator.hasVibrator()){
			vibrator.vibrate(pattern, -1);
			return true;
		}
		return false;
	}
}
