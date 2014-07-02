package com.example.navigation;

import android.media.ToneGenerator;

public class Beeper extends BeeperAdapter {
	
	@Override
	public boolean beep(Navigation navigation) {
		if(tone.getAudioSessionId() == 0){
			return false;
		}
		
		for(int i = 0; i < 4 - navigation.getNavigationCode(); i++){
			tone.startTone(ToneGenerator.TONE_PROP_BEEP, 50);
		}
		
		return true;	
	}
}
