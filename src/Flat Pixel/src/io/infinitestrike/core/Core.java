package io.infinitestrike.core;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.geom.Vector2f;

public class Core {	
	
	
	
	public enum Type{
		type_int,
		type_char,
		type_string,
		type_short,
		type_long,
		type_byte,
		type_object,
		type_float,
		type_null
	}
	
	public static <T> T choose(T... args){
		T[] objects = args;
		// do length -1 if array overflow
		return objects[new Random().nextInt(objects.length)];				
	}
	
	public static Object[] getArrayFromArrayList(ArrayList<Object> obj){
		Object[] objects = new Object[obj.size()];
		for(int i = 0; i < obj.size(); i++){
			objects[i] = obj.get(i);
		}
		return objects;
	}
	
	public static ArrayList<Object> getArrayListFromArray(Object[] obj){
		ArrayList<Object> objects = new ArrayList<Object>();
		for(Object o : obj){
			objects.add(o);
		}
		return objects;
	}
	
	public static String MD5(String msg){
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        md.update(msg.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
	}
	
	public static Type typeof(int i){
		return Type.type_int;
	}
	public static Type typeof(char i){
		return Type.type_char;
	}
	public static Type typeof(String i){
		return Type.type_string;
	}
	public static Type typeof(short i){
		return Type.type_short;
	}
	public static Type typeof(long i){
		return Type.type_long;
	}
	public static Type typeof(byte i){
		return Type.type_byte;
	}
	public static Type typeof(Object i){
		return Type.type_object;
	}
	public static Type typeof(float i){
		return Type.type_float;
	}
	
	public static String[] sortStringEvalLTG(int resolution, String... dat){
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				int index_i = Integer.parseInt(dat[i]);
				int index_nxt = Integer.parseInt(dat[i + 1]);

				// Greatest to least
				if (index_nxt > index_i) {
					dat[i + 1] = index_i + "";
					dat[i] = index_nxt + "";
				}
			}
		}
		return dat;
	}
	
	public static String[] sortStringEvalGTL(int resolution, String... dat){
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				int index_i = Integer.parseInt(dat[i]);
				int index_nxt = Integer.parseInt(dat[i + 1]);

				// Greatest to least
				if (index_nxt > index_i) {
					dat[i + 1] = index_i + "";
					dat[i] = index_nxt + "";
				}
			}
		}
		return dat;
	}
	
	public static String[] sortStringLengthGTL(int resolution, String... dat){
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				String index_i = dat[i];
				String index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.length() > index_i.length()) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static String[] sortStringLengthLTG(int resolution, String... dat){
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				String index_i = dat[i];
				String index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.length() < index_i.length()) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static Integer[] sortIntGTL(int resolution,Integer... dat) {
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				int index_i = dat[i];
				int index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt > index_i) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static Integer[] sortIntLTG(int resolution,Integer... dat) {
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				int index_i = dat[i];
				int index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt < index_i) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static DataEntry[] sortDataLTG(int resolution, DataEntry... dat){
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				DataEntry index_i = dat[i];
				DataEntry index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.dataEntryInt  < index_i.dataEntryInt) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static DataEntry[] sortDataGTL(int resolution, DataEntry... dat){
		for (int pass = 0; pass < resolution; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				DataEntry index_i = dat[i];
				DataEntry index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.dataEntryInt  > index_i.dataEntryInt) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static void printHashMapData(HashMap map){
		Object[] keys = map.keySet().toArray();
		Object[] values = map.values().toArray();
		if(keys.length == values.length){
			for(int i = 0; i < keys.length; i++){
				LogBot.logData(LogBot.Status.INFO, "[HashMapData] " + String.format("Key: %s : Value: %s",keys[i],values[i]));
			}
		}else{
			LogBot.logData(LogBot.Status.WARNING, "[HashMapData] Incomplete HashMap, Values dont match!");
		}
	}
	
	public static boolean fileExists(String dir){
		File file = new File(dir);
		return file.exists();
	}
	
	public static final boolean complexInstanceOf(Object e, ArrayList<Class<?>> classObject) {
		for (Class<?> c : classObject) {
			if (c == e.getClass() || e.getClass().isAssignableFrom(c)) {
				return true;
			}
		}
		return false;
	}
	
	public static final boolean hasInterface(Object e, ArrayList<Class<?>> objs){
			Class<?>[] interfaces = e.getClass().getInterfaces();
			for(Class<?> sources : objs){
				for(Class<?> interf : interfaces){
					if(sources.getClass() == interf.getClass()){
						return true;
					}
				}
			}
			return false;
	}
	
	public static final boolean hasInterface(Object e, Class<?>[] objs){
		Class<?>[] interfaces = e.getClass().getInterfaces();
		for(Class<?> sources : objs){
			for(Class<?> interf : interfaces){
				if(sources.getClass() == interf.getClass()){
					return true;
				}
			}
		}
		return false;
}
	
	public static class MathFunctions{
		public static Vector2f getPolarAngles(float angle){
			float x = (float) Math.cos((angle / 180) * 3.14159);
			float y = (float) Math.sin((angle / 180) * 3.14159);
			
			return new Vector2f(x,y);
		}
		
		public static float getDistance(Vector2f v, Vector2f v2){
			
			float x = v2.x - v.x;
			float y = v2.y - v.y;
			
			return (float) Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
		}
		
		public static float getDistance(float x, float y, float xx, float yy){
			float xxx = xx - x;
			float yyy = yy - y;
			
			return (float) Math.sqrt(Math.pow(xxx,2) + Math.pow(yyy, 2));
		}
		
		public static double randomRange(double firstBound, double lastBound){
			return ThreadLocalRandom.current().nextDouble(firstBound, lastBound + 1);
		}
		
		/**
		 * Easy way to calculate a point bound to a grid that can move.
		 * @param x - x pos of input point
		 * @param y - y pos of input point
		 * @param gx - grid size x
		 * @param gy - grid size y
		 * @param bx - bound size x (the width of the grid from its max value to min value) eg. -8 to 8 bx = 16
		 * @param by - bound size y (the height of the grid from its max value to min value) eg.-10 to 10 = 20
		 * @param xo - the x offset of the grid (for a movable grid)
		 * @param yo - the y offset of the grid (for a movable grid)
		 * @return a vector containing the 2 points.
		 */
		public static Vector2f getPointBoundToGrid(float x, float y, float gx, float gy, float bx, float by, float xo, float yo){
			float fx = xo;
			float fy = yo;
			
			float xnn = (float) (gx * Math.floor(((x -fx)/ gx)));
			float ynn = (float) (gy * Math.floor(((y -fy)/ gy)));
			
			float xn = (xnn+fx) % bx;
			float yn = (ynn+fy) % by;
			
			return new Vector2f(xn,yn);
		}
		public static float getAngleFromPoints(Vector2f source, Vector2f target) {
			//float deltaX = a.x - b.x;
			//float deltaY = a.y - b.y;
			//float angle  = (float) Math.toDegrees((float) Math.atan(Math.tan(deltaY/deltaX)));
			float angle = (float) Math.toDegrees(Math.atan2(target.y - source.y, target.x - source.x));
			return angle;
		}
	}
}
