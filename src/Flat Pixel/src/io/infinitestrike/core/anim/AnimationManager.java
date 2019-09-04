package io.infinitestrike.core.anim;

import java.util.HashMap;

public class AnimationManager {
	private HashMap<String,Animation> map = new HashMap<String,Animation>();
	
	public void addAnimation(String key, Animation anim){
		map.put(key, anim);
	}
	
	public void removeAnimation(String key){
		map.get(key);
	}
	
	public Animation getAnimationWithName(String name){
		return map.get(name);
	}
	
	public Animation getAnimationAtIndex(int i){
		Object[] objects = map.values().toArray();
		Animation a = (Animation) objects[i];
		
		return a;
	}
	
	public String[] getKeynames(){
		Object[] keys = map.keySet().toArray();
		return (String[]) keys;
	}
	
	public boolean hasAnimationWithName(String name){
		return this.map.containsKey(name);
	}
}
