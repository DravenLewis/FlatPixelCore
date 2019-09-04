package io.infinitestrike.grafx;

import org.newdawn.slick.Image;

public class Tile extends Image{
	
	private boolean solid = false;
	
	public Tile(Image img){
		super(img);
	}
	
	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}
