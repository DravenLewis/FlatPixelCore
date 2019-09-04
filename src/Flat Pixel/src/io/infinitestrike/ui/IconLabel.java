package io.infinitestrike.ui;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

import io.infinitestrike.core.util.font.FontLoader;
import io.infinitestrike.entity.InputEvent;

public class IconLabel extends GUIObject{

	private String text = "";
	private Font font = FontLoader.getFallbackFont();
	private Color textColor = Color.white;
	private Color backgroundColor = new Color(0,0,0,0);
	
	public IconLabel(Image icon, String data, int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.setImage(icon);
		this.text = data;
	}

	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		arg2.setColor(backgroundColor);
		arg2.fill(getBounds());
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		this.draw();
		this.font.drawString(this.getLocation().x + this.getImage().getWidth(), this.getLocation().y, text, this.textColor);
	}

	@Override
	public void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		int calcW = (int) this.getImage().getWidth() + this.font.getWidth(text);
		int calcH = (int) this.getImage().getHeight() + this.font.getHeight(text);
		
		this.getSize().setSize(calcW, calcH);
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
	public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
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

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final Font getFont() {
		return font;
	}

	public final void setFont(Font font) {
		this.font = font;
	}

	public final Color getTextColor() {
		return textColor;
	}

	public final void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public final Color getBackgroundColor() {
		return backgroundColor;
	}

	public final void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
