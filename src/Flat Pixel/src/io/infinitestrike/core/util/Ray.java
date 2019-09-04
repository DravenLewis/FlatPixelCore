package io.infinitestrike.core.util;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import io.infinitestrike.core.Core;

public class Ray {
	
	private Rectangle rect = null;
	private Vector2f start = null;
	
	public Ray(Rectangle screenSize, Vector2f start) {
		this.rect = screenSize;
		this.start = start;
	}
	
	public RayCastHit castToBounds(float angle, int max) {
		Vector2f dir = Core.MathFunctions.getPolarAngles(angle);
		for(int i = 0; i < max; i++) {
			float hx = start.x + dir.x * i;
			float hy = start.y + dir.y * i;
			if (hx < 0 || hx > rect.getWidth() || hy < 0 || hy > rect.getHeight()) {
				RayCastHit hit = new RayCastHit();
				hit.length = i;
				hit.hitPoint = new Vector2f(hx,hy);
				return hit;
			}
		}
		
		return new RayCastHit();
	}
	
	public static class RayCastHit{
		public int length = 0;
		public Vector2f hitPoint = new Vector2f(0,0);
		
		public String toString() {
			return String.format("RCH: L: %s P: [%s,%s]", length,hitPoint.x,hitPoint.y);
		}
	}
}
