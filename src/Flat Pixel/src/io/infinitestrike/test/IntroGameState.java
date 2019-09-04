package io.infinitestrike.test;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.entity.EntityManager.PauseHandler;
import io.infinitestrike.grafx.Art;
import io.infinitestrike.level.TileBasedGameLevel;
import io.infinitestrike.mechanics.leaderboard.LeaderBoard;
import io.infinitestrike.state.LevelState;
import io.infinitestrike.ui.GUILayer;
import io.infinitestrike.ui.LeaderBoardUI;
import io.infinitestrike.ui.Window;

public class IntroGameState extends LevelState{

	public GUILayer layer = null;
	
	public IntroGameState(int id, int width, int height) {
		super(id, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		layer = new GUILayer(0,0,arg0.getWidth(),arg0.getHeight());
		this.addNewEntity(layer);
		
		//TextBox b = new TextBox(256,64);
		LeaderBoard board = new LeaderBoard("test");
		board.setPlayerScore("Draven", 100000);
		LeaderBoardUI button = new LeaderBoardUI(0, 0, 300, 500);
		button.setBoard(board);
		
		layer.addGuiObject(button);
		
		layer.addGuiObject(new Window(30, 30, 300, 300));
	}

	@Override
	public void preDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		arg2.setColor(Color.white);
		arg2.fill(getBounds());
	}

	@Override
	public void draw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		Art.makeText(arg2, 30, 400, "" + Art.RANDOM_SYMBOL + Art.RANDOM_SYMBOL + Art.RANDOM_SYMBOL + Art.RANDOM_SYMBOL + Art.RANDOM_SYMBOL, Color.green);
	}

	@Override
	public void postDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		if(Keyboard.isKeyDown(Input.KEY_SPACE)) {
			int i = new Random().nextInt(100);
			
			
			this.pause(new PauseHandler() {
				
				int j = 0;
				
				@Override
				public void haultedUpdate(StateBasedGame g, GameContainer c, Graphics b, double time) {
					// TODO Auto-generated method stub
					b.setColor(Color.blue);
					b.fillRect(32, 32, 256, 64);
					b.setColor(Color.white);
					b.drawString("Haulted, Press X\n#[h]:" + i + "\n#[r]:" + j, 40, 40);
					
					j++;
					
					if(Keyboard.isKeyDown(Input.KEY_X)) {
						this.unpause();
					}
				}
			});
		}
	}

	@Override
	public void postTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(int i, char c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyRelease(int i, char c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLevelLoad(TileBasedGameLevel level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChange(LevelState previous) {
		// TODO Auto-generated method stub
		
	}

}
