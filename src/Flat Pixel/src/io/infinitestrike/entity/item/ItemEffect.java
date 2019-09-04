package io.infinitestrike.entity.item;

import org.newdawn.slick.state.StateBasedGame;

public interface ItemEffect {	
	public abstract void onItemUpdate(Item sender, StateBasedGame game);
}
