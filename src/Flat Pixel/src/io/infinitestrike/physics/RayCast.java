package io.infinitestrike.physics;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import io.infinitestrike.core.Core;
import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.EntityManager;

public class RayCast {

	public static boolean FLAG_BREAK_ON_HIT = false;
	public static boolean FLAG_INCLUDE_MAX_LENGTH = false;
	
	
	public static ArrayList<RayCastHit> CastRay(EntityManager mgr,Vector2f location, float direction, float length, float step){
		ArrayList<RayCastHit> hitList = new ArrayList<RayCastHit>();
		Vector2f vec = Core.MathFunctions.getPolarAngles(direction);
		boolean hit = false;
		
		for(float i = 0; i < length; i+= step) {
			
			// current location
			Vector2f locVec = new Vector2f(location.x + vec.x * i, location.y + vec.y * i);
			for(Entity e : mgr.getEntities()) {
				if(e.getBounds().contains(locVec.x, locVec.y) && !HitsContainsEntity(hitList, e)) {
					RayCastHit h = new RayCastHit();
					h.hitEntity = e;
					h.hitVector = locVec;
					h.length = i;
					hitList.add(h);
					hit = true;
					if(FLAG_BREAK_ON_HIT) break;
				}
			}
			if(FLAG_BREAK_ON_HIT && hit) {
				break;
			}
		}
		
		if(FLAG_INCLUDE_MAX_LENGTH) {
			RayCastHit lastLocation = new RayCastHit();
			lastLocation.hitVector = new Vector2f(location.x + vec.x * length, location.y + vec.y * length);
			lastLocation.length = length;
			hitList.add(lastLocation);
		}
		
		return hitList;
	}
	
	public static Vector2f getMaxRayPosition(Vector2f location, float direction, float length) {
		Vector2f vec = Core.MathFunctions.getPolarAngles(direction);
		return new Vector2f(location.x + vec.x * length, location.y + vec.y * length);
	}
	
	public static class RayCastHit{
		public Vector2f hitVector;
		public Entity hitEntity; // Entities Are Game Objects
		public float length;
	}
	
	private static boolean HitsContainsEntity(ArrayList<RayCastHit> hits, Entity e) {
		for(RayCastHit h : hits) {
			if(h.hitEntity == e) return true;
		}
		return false;
	}
}
