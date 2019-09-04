package io.infinitestrike.mechanics.warp;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import io.infinitestrike.entity.Entity;

public class Warp {
	private final float x;
	private final float y;
	
	private Dimension size = null;
	private String name = "";
	
	private Warp destination = null;
	
	public Warp(float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.setSize(new Dimension((int) w, (int) h));
	}
	
	public Vector2f getLocation(){
		return new Vector2f(x,y);
	}
	
	
	
	public boolean collidesWithWarp(Rectangle entityBounds){
		if(new Rectangle(x,y,size.getWidth(),size.getHeight()).intersects(entityBounds)){
			return true;
		}
		return false;
	}

	public void warp(Entity e){
		if(this.getDestination() != null){
			e.setLocation(this.getDestination().getLocation());
		}
	}
	
	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,this.size.getWidth(),this.size.getHeight());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Warp getDestination() {
		return destination;
	}

	public void setDestination(Warp destination) {
		this.destination = destination;
	}
}
