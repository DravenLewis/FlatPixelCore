package io.infinitestrike.core.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class BitMap extends BufferedImage {

	public final int width;
	public final int heigth;
	public final int[] pixels;

	public BitMap(int width, int height) {
		super(width, height, BufferedImage.TYPE_INT_RGB);
		this.width = width;
		this.heigth = height;
		this.pixels = ((DataBufferInt) (this.getRaster().getDataBuffer())).getData();
	}

	public static class BitMapUtils {

		public static int[] to1DArray(int[][] array) {
			int[] output = new int[array.length * array[0].length];
			for (int x = 0; x < array.length; x++) {
				for (int y = 0; y < array[0].length; y++) {
					output[x + y * array.length] = array[x][y];
				}
			}
			return output;
		}

		public static int[][] to2DArray(int[] array) {

			int sqrt = (int) Math.sqrt(array.length);
			
			int[][] output = new int[sqrt][sqrt];

			for (int i = 0; i < sqrt; i++) {
				for (int j = 0; j < sqrt; j++) {
					output[j][i] = array[(j * sqrt) + i];
				}
			}

			return output;
		}

	}
}
