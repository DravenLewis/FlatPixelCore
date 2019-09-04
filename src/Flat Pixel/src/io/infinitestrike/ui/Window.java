package io.infinitestrike.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.entity.InputEvent;

public class Window extends GUIObject{

	private GUILayer objectContainer;
	private String name = "";
	private Button b = null;
	
	public Window(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
		objectContainer = new GUILayer(0,0,w - 10,h - 40);
		objectContainer.setBackgroundColor(Color.white);
	}

	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		this.getEntityManager().addEntity(objectContainer);
		

		b = new Button(0,0,25,25);
		b.setTextColor(Color.white);
		b.setString("X");
		b.setBackgroundColor(Color.black);
		b.addButtonListener(new ButtonListener(){

			@Override
			public void buttonClicked(InputEvent e, Button b) {
				// TODO Auto-generated method stub
				destroy();
			}

			@Override
			public void buttonHover(InputEvent e, Button b) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.getEntityManager().addEntity(b);
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		arg2.setColor(Color.black);
		arg2.fill(getBounds());
		arg2.setColor(Color.white);
		arg2.drawString(name, getLocation().x + 5, getLocation().y + 5);
	}

	@Override
	public void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		this.objectContainer.setLocation(new Vector2f(getLocation().x + 5, getLocation().y + 35));
		b.setLocation(new Vector2f(getLocation().x + getSize().getWidth() - b.getSize().getWidth() - 5,getLocation().y + 5));
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
		this.objectContainer.destroy();
		b.destroy();
	}

	
	public void addGUIObject(GUIObject i){
		this.objectContainer.addGuiObject(i);
	}
	
	public void removeGUIObject(GUIObject i){
		this.objectContainer.removeGuiObject(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
