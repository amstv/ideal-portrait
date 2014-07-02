package com.example.navigation;

import android.content.Context;

public class Vibrator_original extends VibratorAdapter{
	
	public Vibrator_original(Context context) {
		super(context);
	}

	@Override
	public boolean vibrate(Navigation navigation) {
		long[] pattern = generatePattern(navigation);
		if(vibrator.hasVibrator()){
			vibrator.vibrate(pattern, -1);
			return true;
		}
		return false;
	}

	/**
	 * Generates a pattern for vibrations
	 * @param repeat decides how many times will be vibrated
	 * @param optimal sings if the face dimensions are optimal
	 * @return
	 */
	private long[] generatePattern(Navigation navigation){
		
		long[] pattern;
		
		if(navigation == Navigation.IDEAL){
			pattern = new long[8];
			for(int i = 1; i < 8; i++){
				if(i%2 == 1){
					pattern[i] = 50;
				}else{
					pattern[i] = 30;
				}
			}
		}else{
			pattern = new long[]{0,50};
		}
		
		return pattern;
	}
}
