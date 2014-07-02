package com.example.navigation;

import android.graphics.Rect;

public abstract class NavigatorAdapter {

	protected int width;
	protected int height;
	protected BeeperAdapter beeper;
	protected VibratorAdapter vibrator;
	
	public NavigatorAdapter(BeeperAdapter beeper, VibratorAdapter vibrator, int width, int height){
		this.beeper = beeper;
		this.vibrator = vibrator;
		this.width = width;
		this.height = height;
	}
	
	public abstract boolean navigate(Rect rect);
	
	public void release(){
		beeper.release();
		vibrator.release();
	}
}
