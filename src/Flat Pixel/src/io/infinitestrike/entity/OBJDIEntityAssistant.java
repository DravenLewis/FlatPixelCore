package io.infinitestrike.entity;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;

import io.infinitestrike.core.anim.Animation;
import io.infinitestrike.core.util.ImageLoader;
import io.infinitestrike.objdi.ObjectDataInformation;

@Deprecated
/**
 * @see JavaScriptEntityAssistant
 * @author draven
 *
 */
public class OBJDIEntityAssistant {
	
	private static String[] opcodes = {
			"x","y","w","h","animstart","animend","img"
	}; 
	
	// TODO Convert this to the new style
	public static void applyDataFromFile(Entity e, File f){
		
		boolean animation = false;
		ArrayList<Image> animationImages = new ArrayList<Image>();
		
		ObjectDataInformation information = new ObjectDataInformation();
		information.setDataFromFile(f);
		HashMap<String, String> parsedData = information.getMapData();
		
		Object[] keys = parsedData.keySet().toArray();
		Object[] valu = parsedData.values().toArray();
		
		for(int i = 0; i < keys.length; i++){
			
			// all values between the anim lines are treated as image locators
			// and the data is only added to the animation array, if the file
			// cant be found then the item wont be added.
			if(animation == true){
				animationImages.add(ImageLoader.getSlickImageFromPath((String) valu[i]));
			}
			
			switch((String) keys[i]){
				case "x":
					e.getLocation().x = Integer.parseInt((String) valu[i]);
					break;
				case "y":
					e.getLocation().y = Integer.parseInt((String) valu[i]);
					break;
				case "w":
					e.getSize().setWidth(Integer.parseInt((String) valu[i]));
					break;
				case "h":
					e.getSize().setHeight(Integer.parseInt((String) valu[i]));
					break;
				case "animstart":
					// Clear the animation frames
					animationImages = new ArrayList<Image>();
					animation = true;
					break;
				case "animend":
					Animation anim = new Animation((Image[]) animationImages.toArray());
					e.setEntityAnimation(anim);
					animation = false;
					break;
				case "img":
					e.setImage(ImageLoader.getSlickImageFromPath((String) valu[i]));
					break;
			}
		}
	}
	
public static void applyDataFromStream(Entity e, InputStream f){
		
		boolean animation = false;
		ArrayList<Image> animationImages = new ArrayList<Image>();
		
		ObjectDataInformation information = new ObjectDataInformation();
		information.setDataFromStream(f);
		HashMap<String, String> parsedData = information.getMapData();
		
		Object[] keys = parsedData.keySet().toArray();
		Object[] valu = parsedData.values().toArray();

		for(int i = 0; i < keys.length; i++){
			
			// all values between the anim lines are treated as image locators
			// and the data is only added to the animation array, if the file
			// cant be found then the item wont be added.
			if(animation == true){
				animationImages.add(ImageLoader.getSlickImageFromPath((String) valu[i]));
			}

					
			if(keys[i].equals("x")){
				e.getLocation().x = Integer.parseInt((String) valu[i]);
			}else if(keys[i].equals("y")){
				e.getLocation().y = Integer.parseInt((String) valu[i]);
			}else if(((String) (keys[i])).equals("w")){
				e.getSize().setWidth(Integer.parseInt((String) valu[i]));
			}else if(keys[i].equals("h")){
				e.getSize().setHeight(Integer.parseInt((String) valu[i]));
			}else if(keys[i].equals("animstart")){
				animationImages = new ArrayList<Image>();
				animation = true;
			}else if(keys[i].equals("animend")){
				Image[] img = new Image[animationImages.size()];
				int c = 0;
				for(Object o : animationImages.toArray()){
					img[c] = (Image) o;
					c++;
				}
				
				Animation anim = new Animation(img);
				e.setEntityAnimation(anim);
				animation = false;
			}else if(keys[i].equals("img")){
				e.setImage(ImageLoader.getSlickImageFromPath((String) valu[i]));
			}
		}
	}
}
