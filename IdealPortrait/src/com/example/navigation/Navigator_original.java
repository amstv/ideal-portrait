package com.example.navigation;

import android.graphics.Rect;
import android.util.Log;

public class Navigator_original extends NavigatorAdapter {
	
	private double surface;
	private final double faceSurfaceMIN = 0.4;
	private final double faceSurfaceMAX = 0.6;
	private int lastRepeat;

	public Navigator_original(BeeperAdapter beeper, VibratorAdapter vibrator, int width, int height) {
		super(beeper, vibrator, width, height);
		surface = width*height;
		Log.d("widht",""+width);
	}

	@Override
	public boolean navigate(Rect rect) {
		int currentSurface;
		int repeat;
		boolean optimal = false;
			
		currentSurface = (int)(((((double)rect.width()+1000)/2000)*width) * (((double)rect.height()+1000)/2000)*height);
		optimal = ((currentSurface >= surface * faceSurfaceMIN) && (currentSurface <= surface * faceSurfaceMAX));
			
		repeat = distanceFromCenter(rect.centerX(),rect.centerY())/50;// width or height / 3 ?
		
		Log.d("DISTANCE", " " + repeat + "distance:" + distanceFromCenter(rect.centerX(),rect.centerY()));
		
		
		if(repeat > 3){
			repeat = 3;
		}
			
		if(repeat != lastRepeat){
			lastRepeat = repeat;
			beeper.beep(Navigation.getNavigationByCode(repeat));
			vibrator.vibrate(Navigation.getNavigationByCode(repeat));
			Log.d("repeat + optimal", repeat + " " + optimal);
			if(repeat == 0 && optimal){
				return true;
			} else {
				return false;
			}
		}else{
			if(repeat == 0 && optimal){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Counts the distance from the center of the screen and returns true if this
	 * distance is lesser or equal to magic constant
	 * @param x position of face rectangle center
	 * @param y position of face rectangle center
	 * @return
	 */
	private int distanceFromCenter(double x, double y){
		int distance = (int) Math.sqrt(Math.pow((((x+1000)/2000)*width)-width/2, 2) + Math.pow((((y+1000)/2000)*height)-height/2, 2));
		return distance;
	}
}
