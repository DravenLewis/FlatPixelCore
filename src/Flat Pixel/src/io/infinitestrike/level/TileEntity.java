package io.infinitestrike.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.geom.Rectangle;

import io.infinitestrike.entity.RectangleEntity;

public class TileEntity extends RectangleEntity{

	//ArrayList<String> flags = new ArrayList<String>();
	HashMap<String,String> flags = new HashMap<String,String>();
	
	
	public int tile_x = 0;
	public int tile_y = 0;

	public TileEntity(Rectangle rect) {
		super(rect);
		// TODO Auto-generated constructor stub
	}

	public TileEntity(int x, int y, int w, int h){
		super(x,y,w,h);
	}
	
	/*public void addFlag(String name){
		this.flags.add(name);
	}*/
	
	public void addFlag(String key, String value){
		this.flags.put(key,value);
	}
	
	/*public void removeFlag(String name){
		this.flags.remove(name);
	}*/
	
	public void removeFlag(String key){
		this.flags.remove(key);
	}
	
	/*public boolean hasFlag(String name){
		return flags.contains(name);
	}*/
	
	public boolean hasFlag(String key){
		return flags.containsKey(key);
	}
	
	/*public ArrayList<String> getFlags(){
		return this.flags;
	}*/
	
	public String getValue(String key){
		return this.flags.get(key);
	}
	
	public HashMap<String, String> getFlags(){
		return this.flags;
	}
	
	public void addFlags(HashMap map){
		Object[] keys = map.keySet().toArray();
		Object[] values = map.values().toArray();
		for(int i = 0; i < keys.length; i++){
			this.addFlag(keys[i] + "", values[i] + "");
		}
	}
}
