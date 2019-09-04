package io.infinitestrike.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.util.font.FontLoader;
import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.InputEvent;

public final class Button extends GUIObject{

	private String text = "";
	
	private Image img = null;
	private Color textColor = Color.black;
	private Color backgroundColor = Color.decode("#3e3e3e");
	private Font textFont = FontLoader.loadSystemSlickFont("Arial", 10);
	
	private ArrayList<ButtonListener> bEvents = new ArrayList<ButtonListener>();
	
	public Button(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	public final void setBackgroundImage(Image img){
		this.img = img;
	}
	
	public final void setBackgroundColor(Color col){
		this.backgroundColor = col;
	}
	
	public final void setTextColor(Color col){
		this.textColor = col;
	}
	
	public final void setFont(Font fnt){
		this.textFont = fnt;
	}
	
	public final void setString(String text){
		this.text = text;
	}
	
	public final String getString(){
		return this.text;
	}
	
	public final Font getFont(){
		return this.textFont;
	}
	
	public final Color getTextColor(){
		return this.textColor;
	}
	
	public final Color getBackgroundColor(){
		return this.backgroundColor;
	}
	
	public final Image getBackgroundImage(){
		return this.img;
	}
	
	public final void addButtonListener(ButtonListener e){
		this.bEvents.add(e);
	}
	
	public final void removeBttonListener(ButtonListener e){
		this.bEvents.remove(e);
	}
	
	public final void fireButtonEvent(){
		for(ButtonListener l : bEvents){
			l.buttonClicked(new InputEvent(),this);
		}
	}
	
	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		if(this.img != null){
			img.draw(getLocation().x,getLocation().y,getSize().getWidth(),getSize().getHeight());
		}else{
			if(backgroundColor != null){
				arg2.setColor(this.backgroundColor);
				arg2.fill(getBounds());
			}else{
				arg2.setColor(Color.decode("#e3e3e3"));
				arg2.fill(getBounds());
			}
		}
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		Rectangle rect = new Rectangle(getLocation().x,getLocation().y,textFont.getWidth(text),textFont.getHeight(text));
		
		float centerX = this.getBounds().getCenterX();
		float centerY = this.getBounds().getCenterY();
		
		float yy = centerY - (rect.getHeight() / 2);
		float xx = centerX - (rect.getWidth() / 2);
		
		arg2.setColor(textColor);
				
		if(this.textFont != null){
			textFont.drawString(xx, yy, text,textColor);
		}else{
			((TrueTypeFont) (arg2.getFont())).drawString(xx, yy, text);
		}
		
		//arg2.setColor(Color.blue);
		//arg2.drawRect(xx,yy,rect.getWidth(),rect.getHeight());
	}

	@Override
	public void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onMouseEvent(InputEvent e) {
		// TODO Auto-generated method stub
		if(this.getEntityManager().checkMouseInside(this)){
			for(ButtonListener l : this.bEvents){
				l.buttonHover(e,this);
			}
			if(this.getEntityManager().checkMouseDownInside(this)){
				for(ButtonListener l : this.bEvents){
					l.buttonClicked(e,this);
				}
			}
		}
	}

	@Override
	public void onDestroy(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
		// TODO Auto-generated method stub
		
	}
}
