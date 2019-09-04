package io.infinitestrike.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface EntityEffect {
	public abstract void onEntityUpdate(Entity e, StateBasedGame game, GameContainer cont); 
}
