package com.example.navigation;

import android.graphics.Rect;

public class Navigation_perc extends NavigatorAdapter {
	
	private double surface;
	private final double faceSurfaceMIN = 0.4;
	private final double faceSurfaceMAX = 0.6;
	private int lastRepeat;

	public Navigation_perc(BeeperAdapter beeper, VibratorAdapter vibrator, int width, int height) {
		super(beeper, vibrator, width, height);
		surface = width*height;
	}

	@Override
	public boolean navigate(Rect rect) {
		int currentSurface;
		int repeat;
		boolean optimal = false;
			
		currentSurface = (int)(((((double)rect.width()+1000)/2000)*width) * (((double)rect.height()+1000)/2000)*height);
		optimal = ((currentSurface >= surface * faceSurfaceMIN) && (currentSurface <= surface * faceSurfaceMAX));
			
		repeat = (int)(distanceFromCenter(rect.centerX(),rect.centerY())/(Math.sqrt(Math.pow(width/2, 2) + Math.pow(height/2, 2))/6));

		if(repeat > 3){
			repeat = 3;
		}
			
		if(repeat != lastRepeat){
			lastRepeat = repeat;
			if(repeat == 0 && optimal){
				beeper.beep(Navigation.getNavigationByCode(repeat));
				vibrator.vibrate(Navigation.getNavigationByCode(repeat));
			}else{
				beeper.beep(Navigation.getNavigationByCode(repeat + 1));
				vibrator.vibrate(Navigation.getNavigationByCode(repeat + 1));
			}		
			
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
	private double distanceFromCenter(double x, double y){
		double distance = Math.sqrt(Math.pow((((x+1000)/2000)*width)-width/2, 2) + Math.pow((((y+1000)/2000)*height)-height/2, 2));
		return distance;
	}
}