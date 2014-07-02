package com.example.idealportrait;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.graphics.Rect;

import com.example.navigation.NavigationSystem;

public class CameraProcessor implements Runnable{
	
	private BlockingQueue<Rect> rects;
	private NavigationSystem nav;
	private transient boolean shutdown = false;
	
	/**
	 * For its processing requires NavigationSystem instance
	 * @param nav
	 */
	public CameraProcessor(NavigationSystem nav){
		this.rects = new LinkedBlockingQueue<Rect>();
		this.nav = nav;
	}
	
	/**
	 * Adds the face to the queue for further processing
	 * @param face
	 */
	public void add(Rect rect){
		try {
			rects.put(rect);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Shutdown the CameraProcessor
	 */
	public void shutdown(){
		this.shutdown = true;
	}
	
	/**
	 * Processes given faces to the navigation system
	 */
	@Override
	public synchronized void run() {
		while(!shutdown){
			try {
				nav.navigate(rects.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


