package io.infinitestrike.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface AbstractGameObject {
	public abstract void onCreate(GameContainer arg0, StateBasedGame arg1);
	
	public abstract void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2);
	public abstract void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2);
	public abstract void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2);
	
	public abstract void onPreTick(GameContainer arg0, StateBasedGame arg1, int arg2);
	public abstract void onTick(GameContainer arg0, StateBasedGame arg1, int arg2);
	public abstract void onPostTick(GameContainer arg0, StateBasedGame arg1, int arg2);
	
	public abstract void onKeyEvent(InputEvent e, int keyCode, char keyChar);
	public abstract void onMouseEvent(InputEvent e);
	
	public abstract void onDestroy(GameContainer arg0, StateBasedGame arg1);
}
