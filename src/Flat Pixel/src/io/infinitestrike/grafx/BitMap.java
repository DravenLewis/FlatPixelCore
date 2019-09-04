package io.infinitestrike.grafx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class BitMap extends BufferedImage{
	public final int width;
	public final int height;
	public final int[] pixels;
	public final Graphics g;
	
	public BitMap(int width, int height){
		super(width,height,BufferedImage.TRANSLUCENT);
		this.width = width;
		this.height = height;
		this.pixels = ((DataBufferInt)this.getRaster().getDataBuffer()).getData();
		this.g = this.getGraphics();
	}
	
	public Graphics getDrawGraphics(){
		return g;
	}
}
