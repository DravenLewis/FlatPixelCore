package io.infinitestrike.grafx;

import org.newdawn.slick.Image;

import io.infinitestrike.core.util.ImageLoader;

public class SpriteSheet {
	private Image img = null;
	private int width = 0;
	private int height = 0;
	
	public SpriteSheet(String path){
		img = ImageLoader.getSlickImageFromPath(path);
		this.setWidth(img.getWidth());
		this.setHeight(img.getHeight());
	}
	
	public Image getSubImage(int x, int y, int w, int h){
		return img.getSubImage(x, y, w, h);
	}

	public Tile getTile(int x, int y, int tileSize){
		return new Tile(img.getSubImage(x * tileSize, y * tileSize, tileSize, tileSize));
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
