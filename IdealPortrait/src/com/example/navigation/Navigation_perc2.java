package com.example.navigation;

import android.graphics.Rect;

public class Navigation_perc2 extends NavigatorAdapter {
	
	private double surface;
	private final double faceSurfaceMIN = 0.4;
	private final double faceSurfaceMAX = 0.6;
	private int previousDistanceLevel;

	public Navigation_perc2(BeeperAdapter beeper, VibratorAdapter vibrator, int width, int height) {
		super(beeper, vibrator, width, height);
		surface = width*height;
	}

	@Override
	public boolean navigate(Rect rect) {
		
		int currentSurface = (int)(((((double)rect.width()+1000)/2000)*width) * (((double)rect.height()+1000)/2000)*height);
		int distanceLevel = (int)(distanceFromCenter(rect.centerX(),rect.centerY())/(Math.sqrt(Math.pow(width/2, 2) + Math.pow(height/2, 2))/6));
		boolean optimal = ((currentSurface >= surface * faceSurfaceMIN) && (currentSurface <= surface * faceSurfaceMAX));		
		
		if(distanceLevel != previousDistanceLevel){
			previousDistanceLevel = distanceLevel;
			
			if(!optimal && distanceLevel == 0){
				distanceLevel += 1;
			}
			
			beeper.beep(Navigation.getNavigationByCode(distanceLevel));
			vibrator.vibrate(Navigation.getNavigationByCode(distanceLevel));	
		}
		
		return distanceLevel == 0 && optimal;
	}
	
	/**
	 * Counts the distance from the center of the screen and returns true if this
	 * distance is lesser or equal to magic constant
	 * @param x position of face rectangle center
	 * @param y position of face rectangle center
	 * @return
	 */
	private double distanceFromCenter(double x, double y){
		return Math.sqrt(Math.pow((((x+1000)/2000)*width)-width/2, 2) + Math.pow((((y+1000)/2000)*height)-height/2, 2));
	}
}