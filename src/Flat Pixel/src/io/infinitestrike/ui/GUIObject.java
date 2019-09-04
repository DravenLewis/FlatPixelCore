package io.infinitestrike.ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;

import io.infinitestrike.entity.Entity;

public abstract class GUIObject extends Entity{
	
	private GUILayer guiParent = null;
	private ArrayList<GUIChangeListener> changeListener = new ArrayList<GUIChangeListener>();
	
	private boolean showBounds = false;
	
	protected boolean focus = false;
	
	public GUIObject(int x, int y){
		super(x,y);
	}
	
	public GUIObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	private float xOffset = 0;
	private float yOffset = 0;
	

	public final void addChangeListener(GUIChangeListener e){
		this.changeListener.add(e);
	}
	
	public final void removeChangeListener(GUIChangeListener e){
		this.changeListener.remove(e);
	}
	
	public final void setOffset(float offsetx,float offsety){
		this.setxOffset(offsetx);
		this.setyOffset(offsety);
 	}

	public final GUILayer getGuiParent() {
		return guiParent;
	}

	public final void setGuiParent(GUILayer guiParent) {
		this.guiParent = guiParent;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}
	
	@Override
	public void onDimensionChange(Entity e) {
		// TODO Auto-generated method stub
		for(GUIChangeListener l : this.changeListener){
			l.onGUIChange(e);
		}
	}

	public boolean isShowBounds() {
		return showBounds;
	}

	public void setShowBounds(boolean showBounds) {
		this.showBounds = showBounds;
	}
	
	private final void drawOutline(Graphics g){
		int spacing = 4;
		short pattern = (short) 0xAAAA;
		GL11.glLineStipple(spacing, pattern);
		GL11.glEnable(GL11.GL_LINE_STIPPLE);
		
		g.drawRect(getLocation().x, getLocation().y, getSize().getWidth(), getSize().getHeight());
		
		GL11.glDisable(GL11.GL_LINE_STIPPLE);
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}
}
