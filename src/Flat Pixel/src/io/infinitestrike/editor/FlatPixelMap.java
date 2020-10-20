package io.infinitestrike.editor;

import java.io.File;

import org.newdawn.slick.Image;

import io.infinitestrike.core.util.SlickImageUtils;

public class FlatPixelMap {

	private String path;
	private Image mapImage;
	
	public FlatPixelMap(String path){
		this.path = path;
		Map m = Map.openMap(new File(path));
		this.mapImage = SlickImageUtils.getSlickImageFromBufferedImage(m.renderMap());
	}
	
	
	public void draw(float offsetx, float offsety) {
		mapImage.draw(offsetx, offsety);
	}
	
	public Image getMapImage() {
		return this.mapImage;
	}


	public String getPath() {
		return path;
	}
}
