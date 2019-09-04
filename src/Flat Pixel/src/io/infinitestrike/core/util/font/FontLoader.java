package io.infinitestrike.core.util.font;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class FontLoader {
	
	public static org.newdawn.slick.Font getFallbackFont(){
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] names = e.getAvailableFontFamilyNames();
		for(String s : names){
			if(!s.contains("@")){
				return loadSystemSlickFont(s, 24);
			}
		}
		return null;
	}
	
	
	
	public static org.newdawn.slick.Font loadSlickFont(String path, int size){
		//GL11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_NEAREST);
		UnicodeFont font = new UnicodeFont(loadAwtFont(path,size));
		font.addAsciiGlyphs();
		font.addGlyphs(400, 600);
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR,"Cant Load Slick Compatable Font from source! " + e.getLocalizedMessage());
		}
		return font;
	}
	
	public static org.newdawn.slick.Font loadSystemSlickFont(String path, int size){
		//GL11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_NEAREST);
		UnicodeFont font = new UnicodeFont(loadSystemAwtFont(path,size));
		font.addAsciiGlyphs();
		font.addGlyphs(400, 600);
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR,"Cant Load Slick Compatable Font from system! " +  e.getLocalizedMessage());
		}
		return font;
	}
	
	public static java.awt.Font loadAwtFont(String path, int size){
		java.awt.Font font = null;
		try {
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT , new FontLoader().getClass().getResourceAsStream(path));
			font = font.deriveFont((float) size);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR,"Cant load font with unknown format! " +  e.getLocalizedMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR,"Cant load font! " + e.getLocalizedMessage());
		}
		return font;
	}
	
	public static java.awt.Font loadSystemAwtFont(String path, int size){
		java.awt.Font font = new java.awt.Font(path,java.awt.Font.PLAIN,size);
		return font;
	}
}
