package io.infinitestrike.core.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

import io.infinitestrike.octave.Noise;

public class NoiseMap extends BitMap{
	
	public NoiseMap(int width, int height){
		super(width,height);
	}
	
	
	public final void generate(double scale, long seed){
		double[][] map = Noise.getNoiseMap(this.width, this.heigth, scale, seed);
		int[][] intMap = new int[map.length][map[0].length];
		
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				intMap[i][j] = (int) map[i][j];
			}
		}
		
		int[] pixelMap = BitMap.BitMapUtils.to1DArray(intMap);
		for(int k = 0; k < pixelMap.length; k++){
			int val = pixelMap[k];
			pixels[k] = (val<<16) + (val<<8) + val;
		}
	}
	
	public final int[][] getSubdividedMap(int x, int y, int w, int h){
		int[][] output = new int[w][h];
		BufferedImage img = this.getSubimage(y, x, w, h);
		for(int i = 0; i < output.length; i++){
			for(int j = 0; j < output[0].length; j++){
				int val = new Color(img.getRGB(i, j)).getRed();
				output[j][i] = val;
			}
		}
		return output;
	}
}
