package io.infinitestrike.core;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.core.annote.Broken;
import io.infinitestrike.core.sound.SoundEngine;
import io.infinitestrike.core.util.FPMath.Vector2i;
import io.infinitestrike.state.LevelState;

public abstract class TemplateGame extends StateBasedGame {

	public static final int FULLSCREEN 	= 0b00000001;
	public static final int VSYNC 		= 0b00000010;
	//public static final int RESIZE      = 0b00000100;
	
	public static final int DEFAULT 	= 0b00000011;
	public static final int NONE 		= 0b00000000;
	
	
	private int updateTimer = 0;
	private int secondTimer = 0;
	private int elapsedTime = 0;

	private int deltaTime = 0;

	private Time timer = new Time();

	public static int GAME_FREQUENCY = 60;

	public ArrayList<BasicGameState> stateList = new ArrayList<BasicGameState>();

	// EventLists
	private final ArrayList<GlobalAction> actions = new ArrayList<GlobalAction>();
	private final ArrayList<ShutdownHook> shutdownActions = new ArrayList<ShutdownHook>();
	private final ArrayList<Killable> killActions = new ArrayList<Killable>();

	public TemplateGame(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public final void registerGlobalAction(GlobalAction a) {
		actions.add(a);
	}

	public final void deregisterGlobalAction(GlobalAction a) {
		actions.remove(a);
	}

	public final void registerShutdownHook(ShutdownHook a) {
		shutdownActions.add(a);
	}

	public final void deregisterShutdownHook(ShutdownHook a) {
		shutdownActions.remove(a);
	}

	public final void addKillableObject(Killable k) {
		killActions.add(k);
	}

	public final void removeKillableObject(Killable k) {
		killActions.remove(k);
	}

	public final void exit(int status) {
		SoundEngine.stopAllSounds();
		for (ShutdownHook h : shutdownActions) {
			h.fireHook(this);
		}
		for (Killable k : killActions) {
			k.kill(this);
		}
		System.exit(status);
	}

	/*
	 * Unneeded, use built in functions
	 * 
	public void addNewState(BasicGameState state, String name) {
		if (state instanceof LevelState) {
			LevelState state1 = (LevelState) state;
			state1.id = stateList.size();
			this.stateList.add(state1);
		} else {
			this.stateList.add(state);
		}
	}

	public int getStateIndex(LevelState state) {
		if (stateList.contains(state)) {
			return stateList.indexOf(state);
		} else
			return 0;
	}

	/*
	 * public void enterLevelState(LevelState state){ if(this.getCurrentState()
	 * != null){ LevelState lstate = (LevelState) this.getCurrentState();
	 * lstate.terminate(getContainer(), this); }
	 * this.enterState(getStateIndex(state)); }
	 *

	public ArrayList<BasicGameState> getGameStateObjects() {
		return this.stateList;
	}

	*/
	
	public int getSecondTime() {
		return this.secondTimer;
	}

	public void changeState(int index) {
		try {
			this.enterState(index);
			BasicGameState state = (BasicGameState) this.getState(index);
			if (state instanceof LevelState) {
				LevelState levelState = (LevelState) state;
				levelState.stateChange((LevelState) this.getCurrentState());
			}
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
	}

	public final void checkGlobalUpdates() {
		this.updateTimer++;
		this.elapsedTime = updateTimer - this.elapsedTime;
		this.deltaTime = timer.getDelta();

		if (this.getContainer().getFPS() != 0 && this.updateTimer % this.getContainer().getFPS() == 0) {
			this.secondTimer += 1;
		}
		for (GlobalAction e : actions) {
			e.performAction(this);
		}
	}

	public final void enterStateWithCleanup(int id) {
		GameState state = this.getCurrentState();
		if (state instanceof LevelState) {
			LevelState lState = (LevelState) state;
			lState.cleanup();
			lState.enterState(id);
			try {
				lState.init(this.getContainer(), this);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
			}
		}
	}

	public static final AppGameContainer doBasicGameCreate(TemplateGame game, Vector2i windowSize, int controls) {
		boolean isFullscreen 	= false;
		boolean useVsync 		= false;
		//boolean useResize 		= false;
		// 0 0 0 0 0 0 R V F
		
		
		isFullscreen = ((controls) & 0b1) != 0;
		useVsync = ((controls >> 0b1) & 0b1) != 0;
		//useResize = ((controls >> 0b10) & 0b1) != 0;
		
		try {
			AppGameContainer c = new AppGameContainer(game);
			c.setVSync(useVsync);
			c.setDisplayMode(windowSize.x, windowSize.y, isFullscreen);
			c.setShowFPS(false);
			c.start();
			return c;
		}catch(Exception e) {
			LogBot.logDataVerbose(e, Status.SEVERE, "Cannot Start Game: " + e.getLocalizedMessage());
		}
		return null;
	}
	
	public final int getUpdateTimer() {
		return updateTimer;
	}

	public final int getElapsedTime() {
		return elapsedTime;
	}

	public final int getDeltaTime() {
		return this.deltaTime;
	}

}
