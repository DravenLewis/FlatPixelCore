package io.infinitestrike.ui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.util.font.FontLoader;
import io.infinitestrike.entity.InputEvent;

public class PasswordBox extends GUIObject {

	private String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyz" + 
								  "ABCDEFGHIJKLMNOPQRSTUVXXYZ-=_+[]{};'" +
			                      "\",.<>/?~`!@#$%^&*() ";

	private String text = "";
	private boolean wasFocused = false;;
	private int timer = 0;

	private Color textColor = Color.white;
	private Font textFont = FontLoader.getFallbackFont();

	private int caratPos = 0 + textFont.getWidth("A");

	private ArrayList<TextBoxListener> textBoxListeners = new ArrayList<TextBoxListener>();

	public PasswordBox(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final Color getTextColor() {
		return textColor;
	}

	public final void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public final Font getTextFont() {
		return textFont;
	}

	public final void setTextFont(Font textFont) {
		this.textFont = textFont;
	}

	public final void addTextboxListener(TextBoxListener l) {
		this.textBoxListeners.add(l);
	}

	public final void removeTextBoxListener(TextBoxListener l) {
		this.textBoxListeners.remove(l);
	}

	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		this.getSize().setWidth(250);
		this.getSize().setHeight(50);
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub

		arg2.setColor(Color.black);
		arg2.fill(getBounds());

		if (this.wasFocused == true && this.focus == false) {
			for (int i = 0; i < this.textBoxListeners.size(); i++) {
				this.textBoxListeners.get(i).onFocus(this, focus);
			}
		}

		if (this.wasFocused == false && this.focus == true) {
			for (int i = 0; i < this.textBoxListeners.size(); i++) {
				this.textBoxListeners.get(i).onFocus(this, focus);
			}
		}

		if (this.focus == true) {
			arg2.setColor(Color.white);
		} else {
			arg2.setColor(Color.darkGray);
		}
		arg2.draw(getBounds());
		this.wasFocused = focus;
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		float x = this.getLocation().x + this.textFont.getWidth("A");
		float y = this.getLocation().y + (this.getSize().getHeight() / 2) - (this.textFont.getHeight(text) / 2);

		
		
		// if the length is greater than 26 then get the last 26 chars of the string
		if(text.length() > 15){
			int index = (int) (text.length() - 15);
			String movedText = text.substring(index, text.length());
			// Just Draw 15 circles.
			int width = 10;
			int height = 10;
			int padding = 15;
			
			for(int i = 0; i < 15; i++){
				arg2.setColor(textColor);
				arg2.fillOval(x + (i * padding), y, width, height);
			}
			
			//textFont.drawString(x, y, movedText, textColor);
		}else{
			//textFont.drawString(x, y, text, textColor);
			int width = 10;
			int height = 10;
			int padding = 15;
			
			for(int i = 0; i < text.length(); i++){
				arg2.setColor(textColor);
				arg2.fillOval(x + (i * padding), y, width, height);
			}
		}

		/*
		 * arg2.setColor(textColor); arg2.drawLine(x + caratPos, y - 2, x +
		 * caratPos, y + this.textFont.getHeight("A") + 2);
		 */
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
		if(Keyboard.isKeyDown(Input.KEY_RETURN)){
			for (int i = 0; i < this.textBoxListeners.size(); i++) {
				this.textBoxListeners.get(i).onFire(this);
			}
			this.focus = false;
		}
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

	@Override
	public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

		if (this.focus == true && e.isKeyDown(keyCode)) {
			if (keyChar == '\b' && text.length() > 0) {
				int width = this.textFont.getWidth("" + this.text.charAt(text.length() - 1));
				this.text = text.substring(0, text.length() - 1);

				for (int i = 0; i < this.textBoxListeners.size(); i++) {
					this.textBoxListeners.get(i).onTextChanged(this, getText());
				}

				this.caratPos -= width;
			} else if (keyChar == '\b' && text.length() <= 0) {
				// do nothing.	
			} else {
				if (this.allowedChars.contains(keyChar + "")) {
					this.text += Keyboard.getEventCharacter();
					this.caratPos += this.textFont.getWidth(Keyboard.getEventCharacter() + "");

					for (int i = 0; i < this.textBoxListeners.size(); i++) {
						this.textBoxListeners.get(i).onTextChanged(this, getText());
					}
				}
			}
		}
	}

}
