package io.infinitestrike.core;

import java.util.HashMap;


@Deprecated
/**
 * 
 * @author Developer
 * redunadant just use hashmaps.
 */
public class VariableRegistry {
	private static final HashMap<String, Object> map = new HashMap<String, Object>(); 
	
	public static void registerVariable(String key, Object value){
		map.put(key, value);
	}
	
	public static void removeVariable(String key){
		map.remove(key);
	}
	
	public static void setVariable(String key, Object value){
		map.replace(key, value);
	}
	
	public static boolean exists(String key){
		return map.containsKey(key);
	}
}
