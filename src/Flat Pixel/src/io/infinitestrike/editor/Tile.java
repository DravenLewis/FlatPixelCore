package io.infinitestrike.editor;

import java.awt.Image;
import java.awt.image.BufferedImage;


// just a data structure
// not an actual tile, can be used
// to calculate what a tile at this location would be

public class Tile {
	
	public int id = 0;
	public int tileSize = 32;
	public int tileOffsetX = 0; // this gets multiplied by size
	public int tileOffsetY = 0; // to calculate sub image
	
	
	public Tile(BufferedImage img, int tileSize, int xoffset, int yoffset) {
		this.tileSize = tileSize;
		this.tileOffsetX = xoffset;
		this.tileOffsetY = yoffset;
	}
	
	public Image getTileImage(BufferedImage tileMap) {
		if(tileOffsetX * tileSize < tileMap.getWidth() && tileOffsetY * tileSize < tileMap.getHeight()) {
			return tileMap.getSubimage(tileOffsetX * tileSize, tileOffsetY * tileSize, tileSize, tileSize);
		}else {
			return new BufferedImage(tileSize,tileSize,BufferedImage.TYPE_INT_ARGB_PRE);
		}
	}
}
