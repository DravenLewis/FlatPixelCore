package io.infinitestrike.core.util;

import org.lwjgl.util.vector.Vector2f;

public class FPMath {

	public abstract static class Vector {
		public final int size;

		public Vector(int size) {
			this.size = size;
		}
	}

	public static class Vector2i extends Vector {
		public int x;
		public int y;

		public Vector2i(int x, int y) {
			super(2);
			this.x = x;
			this.y = y;
		}
	}

	public static class Vector3i extends Vector {
		public int x;
		public int y;
		public int z;

		public Vector3i(int x, int y, int z) {
			super(3);
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	public static Vector2f getCell(int fieldOffsetX, int fieldOffsetY, int screenSpaceX, int screenSpaceY, int wPixels,
			int hPixels, int cellSize) {

		// Bind the Position Relative to the Grid
		int boundOffsetX = screenSpaceX - fieldOffsetX;
		int boundOffsetY = screenSpaceY - fieldOffsetY;

		// Scale the Relative position to an offset in cells space
		// X Cell Location = floor(X Relative offset * (Horizontal Cell Count) / Number
		// of pixels the grid is wide)
		// Y Cell Location = floor(Y Relative offset * (Vertical Cell Count) / Number of
		// pixels the grid is high)

		int fieldQuotientX = (int) Math.floor(boundOffsetX * ((float) (wPixels / cellSize) / (float) wPixels));
		int fieldQuotientY = (int) Math.floor(boundOffsetY * ((float) (hPixels / cellSize) / (float) hPixels));

		// Clamp the output to only be valid to the grid, as the bound offset is still
		// in screen space,
		// therefore you can technically have a cell at (-1,-1) or (x + 1, y + 1), where
		// x and y are the
		// max values the grid has. Prevents IndexOutOfBoundsException.

		int gridBoundX = clamp(fieldQuotientX, 0, (wPixels / cellSize) - 1);
		int gridBoundY = clamp(fieldQuotientY, 0, (hPixels / cellSize) - 1);

		// Return the clamped orderd pair
		return new Vector2f(gridBoundX, gridBoundY);
	}

	public static Vector2f getCell(Vector2f fieldOffset, Vector2f screenSpacePoint, Vector2f gridPixelSize,int cellSize) {
		return getCell(
				(int) fieldOffset.x, 
				(int) fieldOffset.y, 
				(int) screenSpacePoint.x, 
				(int) screenSpacePoint.y, 
				(int) gridPixelSize.x,
				(int) gridPixelSize.y, 
				cellSize
		);
	}

	public static int clamp(int i, int min, int max) {
		return Math.max(min, Math.min(max, i));
	}
}
