package io.infinitestrike.mechanics.message;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.util.font.FontLoader;
import io.infinitestrike.entity.InputEvent;
import io.infinitestrike.ui.GUIObject;

public class MessageBox extends GUIObject{

	private Color textColor = Color.white;
	private Color backGroundColor = Color.black;
	private Image backGoundImage = null;
	private Font font = FontLoader.getFallbackFont();
	
	String message = "";
	
	protected ArrayList<String> messages = new ArrayList<String>();
	private int index = 0;
	
	public MessageBox(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public void showMessage(String s){		
		if(this.isHidden() == false){
			// one is already open;
			messages.add(s);
		}else{
			this.setHidden(false);
			message = s;
			messages.add(s);
		}
	}
	
	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		this.setHidden(true);
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		if(message.length() >= 50*3){
			message = message.substring(0, (50*3));
		}
		
		String out = "";
		for(int i  = 0 ; i < this.message.length(); i++){
			out += message.charAt(i);
			if(out.length() % 50 == 0){
				out += "\n";
			}
		}
		
		if(this.backGoundImage != null){
			this.backGoundImage.draw(this.getLocation().x, this.getLocation().y);
		}else{
			arg2.setColor(this.backGroundColor);
			arg2.fill(this.getBounds());
		}
		
		if(this.font != null){
				// TODO do offset?
				font.drawString(this.getLocation().x + 10,this.getLocation().y + 5, out,this.textColor);
		}else{
				arg2.setColor(this.textColor);
				arg2.drawString(out,this.getLocation().x + 10, this.getLocation().y +5);
		}
		
		//arg2.setColor(Color.white);
		//arg2.drawString("This is a test", this.getLocation().x, this.getLocation().y);
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
	public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
		// TODO Auto-generated method stub
		if(Keyboard.isKeyDown(Input.KEY_SPACE) || Keyboard.isKeyDown(Input.KEY_SPACE)){
			if(this.index < this.messages.size() - 1){
				index++;
				this.message = this.messages.get(index);
			}else{
				this.setHidden(true);
				this.messages = new ArrayList<String>();
				this.index = 0;
			}
		}
	}

	public boolean hasNext(){
		if(this.index < this.messages.size()){
			return true;
		}
		return false;
	}
	
	public boolean isActive(){
		return !this.isHidden();
	}
	
	@Override
	public void onMouseEvent(InputEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	public final Color getTextColor() {
		return textColor;
	}

	public final void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public final Color getBackGroundColor() {
		return backGroundColor;
	}

	public final void setBackGroundColor(Color backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	public final Image getBackGoundImage() {
		return backGoundImage;
	}

	public final void setBackGoundImage(Image backGoundImage) {
		this.backGoundImage = backGoundImage;
	}

	public final Font getFont() {
		return font;
	}

	public final void setFont(Font font) {
		this.font = font;
	}

}
