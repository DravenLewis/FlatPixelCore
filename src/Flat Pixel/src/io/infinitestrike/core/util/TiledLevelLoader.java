package io.infinitestrike.core.util;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class TiledLevelLoader {
	public static TiledMap loadMap(String map, String loc){
		try {
			return new TiledMap(map,loc);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
			return null;
		}
	}
	
	public static TiledMap loadMap(String map){
		try{
			return new TiledMap(map);
		}catch(Exception e){
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
			return null;
		}
	}
}
