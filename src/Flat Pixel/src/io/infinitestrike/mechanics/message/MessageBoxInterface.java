package io.infinitestrike.mechanics.message;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class MessageBoxInterface {
	
	private MessageBox box = null;
	
	public MessageBoxInterface(MessageBox box){
		this.box = box;
	}
	
	public void close(){
		this.box.messages = new ArrayList<String>();
		this.box.setHidden(true);
	}
	
	
	public MessageBox getInernalRefrence(){
		return this.box;
	}
	
	public boolean getOpen(){
		return box.isActive();
	}
	
	public void showMessage(String s){
		box.showMessage(s);
	}
	
	public void setSize(int w, int h){
		box.getSize().setSize(w, h);
	}
	
	public void setLocation(float x, float y){
		box.setLocation(new Vector2f(x,y));
	}
	
	public void setOffset(float x, float y){
		box.setOffset(x, y);
	}
}
