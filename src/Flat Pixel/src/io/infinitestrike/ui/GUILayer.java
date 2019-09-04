package io.infinitestrike.ui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.InputEvent;

public class GUILayer extends Entity{

	
	private boolean drawBackground = true;
	private Image backgroundImage =  null;
	private Color backgroundColor = new Color(0,0,0,150);
	
	public GameContainer container = null;
	public StateBasedGame game = null;
	
	private int timerInt = 0;
	private int currentGUIObject = -1;
	private boolean drawselectedGuiBounds = false;
	
	private ArrayList<GUIObject> objects = new ArrayList<GUIObject>(); 
	private ArrayList<GUIChangeListener> changeListener = new ArrayList<GUIChangeListener>();
	
	public GUILayer(int x, int y, int w, int h){
		super(x,y,w,h);
	}
	
	public final void registerChangeListener(GUIChangeListener e){
		this.changeListener.add(e);
	}
	
	public final void deregisterChangeListener(GUIChangeListener e){
		this.changeListener.remove(e);
	}
	
	public final boolean canDrawBackground() {
		return drawBackground;
	}

	public final void setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
	}

	public final Image getBackgroundImage() {
		return backgroundImage;
	}

	public final void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	public void addGuiObject(GUIObject o){
		o.setGuiParent(this);
		o.setLocation(new Vector2f(getLocation().x + o.getxOffset(),getLocation().y + o.getyOffset()));
		if(container != null && game != null){
			o.onCreate(container, game);
		}
		if(this.getEntityManager() != null){
			o.registerEntityManager(getEntityManager());
		}
		this.objects.add(o);
	}
	
	public void removeGuiObject(GUIObject o){
		if(container != null && game != null){
			o.onDestroy(container, game);
		}
		if(this.getEntityManager() != null){
			o.deregisterEntityManager(getEntityManager());
		}
		this.objects.remove(o);
	}


	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		this.container = arg0;
		this.game = arg1;
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub

	}

	public final void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		if(this.drawBackground == true){
			if(this.backgroundImage == null){
				arg2.setColor(this.backgroundColor);
				arg2.fillRect(getLocation().x, getLocation().y, getSize().getWidth(), getSize().getHeight());
			}else{
				backgroundImage.draw(getLocation().x, getLocation().y, getSize().getWidth(), getSize().getHeight());
			}
		}
		
		for(int i = 0; i < objects.size(); i++){
			GUIObject o = objects.get(i);

			if(!o.isHidden()){
				o.onPreDraw(arg0, arg1, arg2);
				o.onDraw(arg0, arg1, arg2);
				o.onPostDraw(arg0, arg1, arg2);
			}
			
			if(i == this.currentGUIObject && this.drawselectedGuiBounds){
				arg2.setColor(Color.decode("#e3e3e3"));
				arg2.drawRect(o.getLocation().x - 5, o.getLocation().y - 5, o.getSize().getWidth() + 10, o.getSize().getHeight() + 10);
			}
		}
	}
	
	public final void setDrawSelector(boolean drawselectedGuiBounds) {
		this.drawselectedGuiBounds = drawselectedGuiBounds;
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
			for(GUIObject o : objects){
				if(this.getEntityManager() != null){
					if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON) || Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON)){
						if(!o.getEntityManager().checkMouseInside(o)){
							this.currentGUIObject = -1;
							o.focus = false;
						}else{
							this.currentGUIObject = objects.indexOf(o);
							o.focus = true;
						}
				}
				o.onPreTick(arg0, arg1, arg2);
				o.onTick(arg0, arg1, arg2);
				o.onPostTick(arg0, arg1, arg2);
			}
		}
		
		// TODO look for dangling references of the entuty manager.
		
		if(Keyboard.isKeyDown(Input.KEY_TAB) && this.timerInt % 5 == 0){
			if(this.currentGUIObject + 1 > this.objects.size() - 1){
				this.currentGUIObject = 0;
			}else{
				this.currentGUIObject += 1;
			}
		}
		
		if(Keyboard.isKeyDown(Input.KEY_SPACE ) || Keyboard.isKeyDown(Input.KEY_RETURN)){
			if(this.currentGUIObject >= 0 && this.objects.get(currentGUIObject) != null){
				this.objects.get(currentGUIObject).focus = true;
				
				for(int i = 0; i < this.objects.size(); i++){
					if(i != this.currentGUIObject){
						this.objects.get(i).focus = false;
					}
				}
			}
		}
		
		this.timerInt++;
	}

	public void increaseGuiObject(){
		if(this.currentGUIObject + 1 > this.objects.size() - 1){
			this.currentGUIObject = 0;
		}else{
			this.currentGUIObject += 1;
		}
	}
	
	public void decreaseGuiObject(){
		if(this.currentGUIObject - 1 < 0){
			this.currentGUIObject = this.objects.size() - 1;
		}else{
			this.currentGUIObject -= 1;
		}
	}
	
	@Override
	public void onPostTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyEvent(InputEvent e, int code, char chr) {
		// TODO Auto-generated method stub
		if(this.getEntityManager() == null){
			for(GUIObject o : this.objects){
				o.onKeyEvent(e,code,chr);
			}
		}
	}

	@Override
	public void onMouseEvent(InputEvent e) {
		// TODO Auto-generated method stub
		if(this.getEntityManager() == null){
			for(GUIObject o : this.objects){
				o.onMouseEvent(new InputEvent());
			}
		}
	}

	@Override
	public void onDestroy(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		for(GUIObject o : objects){
			o.destroy();
			o.deregisterEntityManager(getEntityManager());
		}
	}

	@Override
	public void onDimensionChange(Entity e) {
		// TODO Auto-generated method stub
		for(GUIObject o : objects){
			o.setLocation(new Vector2f(getLocation().x + o.getxOffset(),getLocation().y + o.getyOffset()));
		}
	}	
}
