package io.infinitestrike.js;

import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.util.ASCIIFileReader;

public class Script {

	private Context context;
	private ScriptableObject scope;
	private String fileLocation = "";
	
	public Script(String s) {
		this.fileLocation = s;
		context = Context.enter();
		scope = context.initStandardObjects();
		load(s);
	}

	public void load(String fileLocation) {
			context.evaluateString(scope, ASCIIFileReader.getFileContents(fileLocation), "script", 1, null);
	}

	public void callFunction(String functionName, Object... objects){
		try{
			Function fct = (Function) scope.get(functionName, scope);
			Object result = fct.call(context, scope, scope, objects);
		}catch(Exception e){
			LogBot.logData(LogBot.Status.ERROR,"No function with name: " + functionName + "Error: " + e.getLocalizedMessage());
		}
	}
}
