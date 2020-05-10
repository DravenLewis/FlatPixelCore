package io.infinitestrike.core.util;

public class FPMath {
	
	public abstract static class Vector{
		public final int size;
		public Vector(int size) {
			this.size = size;
		}
	}
	
	public static class Vector2i extends Vector{
		public int x;
		public int y;
		
		public Vector2i(int x, int y) {
			super(2);
			this.x = x;
			this.y = y;
		}
	}
	
	public static class Vector3i extends Vector{
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
}
