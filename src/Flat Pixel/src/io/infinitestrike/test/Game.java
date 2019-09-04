package io.infinitestrike.test;



import java.util.logging.Level;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.GlobalAction;
import io.infinitestrike.core.LauncherDialog;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.TemplateGame;
import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.InputEvent;
import io.infinitestrike.state.PluginState;

public class Game extends TemplateGame{

	public Game(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * EntryPoint of the application
	 * @param args - external JVM args
	 * @throws Exception - errors that may be thrown by the initilization of OpenGL. 
	 */
	public static void main(String[] args) throws Exception{
		/**LauncherDialog dialog = new LauncherDialog();
		while(!dialog.isInputChosen){
			
		}
		dialog.destroy();*/
		
		Game game = new Game("Program");
		game.registerGlobalAction(new GlobalAction(){
			@Override
			public void performAction(TemplateGame game) {
				// TODO Auto-generated method stub
				if(Keyboard.isKeyDown(Input.KEY_ESCAPE)){
					System.exit(0);
				}
				
				if(Keyboard.isKeyDown(Input.KEY_F11)){
					try {
						game.getContainer().setFullscreen(!game.getContainer().isFullscreen());
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		AppGameContainer cont = new AppGameContainer(game);
			cont.setDisplayMode(640,480,false);
			cont.setVSync(true);
			cont.start();
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		//IntroGameState state = new IntroGameState(0,arg0.getWidth(),arg0.getHeight());
		IntroGameState state = new IntroGameState(0,arg0.getWidth(),arg0.getHeight());
		this.addState(state);
	}
	
	static class Player extends Entity{
		public Player(int x, int y, int w, int h) {
			super(x, y, w, h);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(GameContainer arg0, StateBasedGame arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
			// TODO Auto-generated method stub
			arg2.setColor(Color.red);
			arg2.fillRect(getLocation().x, getLocation().y, getSize().getWidth(),getSize().getHeight());
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
			if(Keyboard.isKeyDown(Input.KEY_DOWN)){
				move(0,5);
			}
			if(Keyboard.isKeyDown(Input.KEY_UP)){
				move(0,-5);
			}
			if(Keyboard.isKeyDown(Input.KEY_LEFT)){
				move(-5,0);
			}
			if(Keyboard.isKeyDown(Input.KEY_RIGHT)){
				move(5,0);
			}
		}

		@Override
		public void onPostTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onMouseEvent(InputEvent e) {
			// TODO Auto-generated method stub
			if(this.getEntityManager().checkMouseDownInside(this) && e.mouse_right_button){
				this.destroy();
			}
		}

		@Override
		public void onDestroy(GameContainer arg0, StateBasedGame arg1) {
			// TODO Auto-generated method stub
			LogBot.logData(this, "Destorying A Player!", Level.INFO);
		}

		@Override
		public void onDimensionChange(Entity e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
