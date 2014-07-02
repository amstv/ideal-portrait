package com.example.navigation;

import android.media.ToneGenerator;
import android.util.Log;

public class Beeper_original extends BeeperAdapter{

	@Override
	public boolean beep(Navigation navigation) {
		
		if(tone.getAudioSessionId() == 0){
			return false;
		}
		if(navigation == Navigation.IDEAL){
			for(int i = 0; i <= 4; i++){
				tone.startTone(ToneGenerator.TONE_PROP_BEEP, 50);
			}
		}
		
		tone.startTone(ToneGenerator.TONE_PROP_BEEP, 50);
		
		return true;
	}
}
