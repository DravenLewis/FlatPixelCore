package io.infinitestrike.core;

import org.lwjgl.Sys;

public class Time {
	private static long lastTime = 0;
	
	public static long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static int getDelta(){
		long time = getTime();
		int delta = (int) (time - lastTime);
		lastTime = time;
		
		return delta;
	}
}
