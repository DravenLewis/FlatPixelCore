package io.infinitestrike.entity;

import java.util.ArrayList;

import io.infinitestrike.js.Script;

public class AI {
	Script s = null;
	
	public AI(Script s){
		// call the main fuction of the app.
		s.callFunction("main", this);
		this.s = s;
	}
	
	public void runCall(Entity e, int keyCode, String keyChar){
		s.callFunction("onAIUpdate", this, e);
	}
}
