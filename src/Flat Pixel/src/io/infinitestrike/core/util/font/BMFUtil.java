package io.infinitestrike.core.util.font;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.infinitestrike.core.util.ImageLoader;

public class BMFUtil {
	public static Font loadBMF(String location,Image img){
		Font fnt = null;
		try {
			fnt = new AngelCodeFont(location,img);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fnt;
	}
}
