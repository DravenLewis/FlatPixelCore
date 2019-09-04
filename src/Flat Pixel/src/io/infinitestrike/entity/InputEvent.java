package io.infinitestrike.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class InputEvent {
	public int mouse_x = 0;
	public int mouse_y = 0;
	public boolean mouse_left_button = false;
	public boolean mouse_middle_button = false;
	public boolean mouse_right_button = false;
	public boolean[] keys = new boolean[256];
	
	public static int MOUSE_DELTA_X = 0;
	public static int MOUSE_DELTA_Y = 0;
	
	public static int MOUSE_LAST_X = 0;
	public static int MOUSE_LAST_Y = 0;
	
	public InputEvent(){
		getKeyData();
		getMouseData();
	}
	
	private void getKeyData(){
		for(int i = 0; i < keys.length; i++){
			keys[i] = Keyboard.isKeyDown(i);
		}
	}
	
	private void getMouseData(){
		mouse_x = Mouse.getX();
		mouse_y = Mouse.getY();
		mouse_left_button = Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON);
		mouse_middle_button = Mouse.isButtonDown(Input.MOUSE_MIDDLE_BUTTON);
		mouse_right_button = Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON);
	}
	
	public boolean isKeyDown(int keyCode){
		return keys[keyCode];
	}
}
