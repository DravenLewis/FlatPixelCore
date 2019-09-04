package io.infinitestrike.core.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class SlickImageUtils {
	
	public static BufferedImage getBlankImage(int w, int h){
		return new BufferedImage(w,h,1);
	}
	
	public static BufferedImage getBlankImage(int w, int h, Color col){
		BufferedImage img = new BufferedImage(w,h,1);
		Graphics g = img.getGraphics();
		g.setColor(col);
		g.fillRect(0,0,w,h);
		return img;
	}
	
	public static Image getSlickImageFromBufferedImage(BufferedImage img){
		Image simg = null;
		try {
			Texture tex = BufferedImageUtil.getTexture("", img);
			simg = new Image(img.getWidth(),img.getHeight());
			simg.setTexture(tex);
		} catch (IOException | SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		return simg;
	}
	
	public static java.awt.Color getAwtColorFromSlickColor(org.newdawn.slick.Color color){
		java.awt.Color col = new java.awt.Color(color.r,color.g,color.b,color.a);
		return col;
	}
}
