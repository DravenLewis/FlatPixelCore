package io.infinitestrike.js;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.core.util.ResourceLoader;



public class JavaScript {

	// this class will contain all of the
	// cool functons for the js applications we can
	// embed.
	
	public static Object getArray(Class<?> type, int length){
		return Array.newInstance(type, length);
	}
	
	public static void callFunction(String fileLocation, String functionName, Object...objects) throws IOException{
		String fileContents = "";
		String line = "";

		BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.loadResource(fileLocation)));
		while ((line = reader.readLine()) != null) {
			if(line.trim() == "" || line.indexOf("//") == 0){
				continue;
			}
			fileContents += line;
		}

		Context context = Context.enter();
		try {
		    ScriptableObject scope = context.initStandardObjects();
		    context.evaluateString(scope, fileContents, "script", 1, null);
		    Function fct = (Function)scope.get(functionName, scope);
		    Object result = fct.call(context, scope, scope, objects);
		    //System.out.println(Context.jsToJava(result, int.class));
		} finally {
		    Context.exit();
		}
	}
	
	public static void runJS(String fileLocation) throws IOException {
		String fileContents = "";
		String line = "";

		String[] pathname = fileLocation.split("/");
		String fileName = pathname[pathname.length - 1];

		BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.loadResource(fileLocation)));
		while ((line = reader.readLine()) != null) {
			fileContents += line;
		}

		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();

		Object result = cx.evaluateString(scope, fileContents, fileName, 0, null);

		Context.exit();
	}

	public static void runMultipleJS(String... strings) {
		for (String s : strings) {
			try {
				runJS(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
			}
		}
	}
}
