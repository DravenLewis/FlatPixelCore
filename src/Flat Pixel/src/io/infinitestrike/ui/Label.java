package io.infinitestrike.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.util.font.FontLoader;
import io.infinitestrike.entity.InputEvent;

public class Label extends GUIObject{

	private String value = "";
	private Font font = FontLoader.getFallbackFont();
	private Color labelColor = Color.white;
	private Color backgroundColor = Color.black;
	
	private boolean drawBackground = false;
	
	public Label(String value, int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		if(this.drawBackground){
			arg2.setColor(backgroundColor);
			arg2.fill(new Rectangle(this.getLocation().x - 2,this.getLocation().y - 2, this.getSize().getWidth() + 4, this.getSize().getHeight() + 4));
		}
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		this.font.drawString(this.getLocation().x, this.getLocation().y, value, labelColor);
	}

	@Override
	public void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		this.getSize().setWidth(this.font.getWidth(value));
		this.getSize().setHeight(this.font.getHeight(value));
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
		
	}

	@Override
	public void onDestroy(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	public final Color getLabelColor() {
		return labelColor;
	}

	public final void setLabelColor(Color labelColor) {
		this.labelColor = labelColor;
	}

	public final String getString() {
		return value;
	}

	public final void setString(String value) {
		this.value = value;
	}

	public final Font getFont() {
		return font;
	}

	public final void setFont(Font font) {
		this.font = font;
	}

	public final Color getBackgroundColor() {
		return backgroundColor;
	}

	public final void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final boolean isDrawBackground() {
		return drawBackground;
	}

	public final void setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
	}

	@Override
	public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
		// TODO Auto-generated method stub
		
	}

	public Rectangle getBounds(){
		return new Rectangle(this.getLocation().x,this.getLocation().y,this.font.getWidth(value),this.font.getHeight(value));
	}
}
