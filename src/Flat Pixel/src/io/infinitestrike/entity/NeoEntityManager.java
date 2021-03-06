package io.infinitestrike.entity;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.TemplateGame;


// ONLY GONNA EXTENDS FOR LEGACY SUPPORT!! [My Remove Later!!]
public class NeoEntityManager extends EntityManager{
	
	public final static int MAXIMUM_LAYER_COUNT = 10;
	
	public NeoEntityManager(GameContainer cont, StateBasedGame game) {
		super(cont, game);
		// TODO Auto-generated constructor stub
	}

	public void delegatedObjectUpdate(GameContainer arg0, StateBasedGame arg1, Graphics arg2, int arg3) {
		
		this.isInterating = true;
		
		// TICK
		if(this.isPaused() && this.getPauseHandler() != null) {
			this.getPauseHandler().haultedUpdate(arg1, arg0, arg2, arg3);
		}
		
		for(Entity e : this.getEntities()) {
			if(!e.isHidden() && this.isOnScreen(e)) {
				for(int i = 0; i < MAXIMUM_LAYER_COUNT; i++) {
					if(e.getUpdatePriority() == i) {
						// TICK
						if(!this.isPaused()) {
							e.onPreTick(arg0, arg1, arg3);
							e.onTick(arg0, arg1, arg3);
							e.onPostTick(arg0, arg1, arg3);
						}
						
						
						// Rendering is local and relative to the entity, not the world
						// if you want an item to render before another, you should set
						// its layer (Priority) lower.
						e.onPreDraw(arg0, arg1, arg2);
						e.onDraw(arg0, arg1, arg2);
						e.onPostDraw(arg0, arg1, arg2);
						
						
						e.doEffects();
					}
				}
			}
		}
		
		this.isInterating = false;
		
		TemplateGame game = (TemplateGame) arg1;
		if (game.getUpdateTimer() % 5 == 0) {
			doInput();
		}

		// Perform the operations on the cached items and then
		// remove them from the list.
		for (Entity e : this.addcacheList) {
			this.addEntity(e);
		}

		for (Entity e : this.remcacheList) {
			this.removeEntity(e);
		}

		addcacheList = new ArrayList<Entity>();
		remcacheList = new ArrayList<Entity>();
	}
}
