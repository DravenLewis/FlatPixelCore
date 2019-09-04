package io.infinitestrike.core;

import org.newdawn.slick.state.BasicGameState;

/**
 * 
 * @author 17dclewis 
 * 		   StateUnit is a basic class that allows for HashMap type
 *         functionality with an ArrayList, so that objects can be ordered
 *         and indexed for naming later.
 */
final class StateUnit {
	public final BasicGameState game;
	public String name;

	public StateUnit(String name, BasicGameState game) {
		this.name = name;
		this.game = game;
	}

	public void rename(String str) {
		this.name = str;
	}
}
