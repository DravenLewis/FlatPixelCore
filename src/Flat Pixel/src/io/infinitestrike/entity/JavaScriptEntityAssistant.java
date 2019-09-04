package io.infinitestrike.entity;

import java.io.IOException;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.js.JavaScript;

public class JavaScriptEntityAssistant {
	public static void applyDataFromScript(String script, Entity e){
		try {
			JavaScript.callFunction(script, "onCall", e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e1, Status.ERROR, e1.getLocalizedMessage());
		}
	}
}
