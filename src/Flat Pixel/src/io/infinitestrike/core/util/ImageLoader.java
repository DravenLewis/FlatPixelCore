package io.infinitestrike.core.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureLoader;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class ImageLoader {
	public static Image getSlickImageFromPath(String path){
		Image img = null;
		try {
			img = new Image(path);
			img.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.WARNING, "Failed to load image from path: " + path + " : " +  e.getLocalizedMessage());
		}
		return img;
	}
	
	public static BufferedImage getImageFromPath(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(ResourceLoader.loadResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.WARNING,"Failed to load image from path: " + path + " : " +  e.getLocalizedMessage());
		}
		return img;
	}
	
	public static Image getSlickImageFromStream(InputStream s){
		try{
			Image img = new Image(TextureLoader.getTexture("PNG",s));
			img.setFilter(Image.FILTER_NEAREST);
			return img;
		}catch(Exception e){
			LogBot.logDataVerbose(e, Status.WARNING, "Failed to load image from Input Stream: " + e.getLocalizedMessage());
		}
		return null;
	}
}
