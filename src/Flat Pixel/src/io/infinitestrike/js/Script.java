package io.infinitestrike.js;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.core.util.ASCIIFileReader;

// gonna use nashorn this time
public class Script {

	private String scriptText = "";
	private final ScriptEngine nashorn;
	private final File location;

	public Script(File location) {
		this.location = location;
		if (this.location.isFile()) {
			this.scriptText = ASCIIFileReader.getFileContents(location);
			if(Core.getJavaVersion() > 9) {
				LogBot.logData(Status.INFO, "This application uses the Nashorn JavaScript Engine, and supports up to JDK 12");
				LogBot.logData(Status.INFO, "This Support May Be Removed in later versions of Java");
			}
			this.nashorn = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				this.processIncludes();
				String rawScript = this.scriptText.replace(""+ASCIIFileReader.LINE_DATA_SEP, "");
				this.nashorn.eval(rawScript);
			} catch (Exception e) {
				LogBot.logDataVerbose(e, Status.WARNING, e.getLocalizedMessage());
			}
		} else {
			this.nashorn = null;
			LogBot.logData(Status.WARNING, "Script Must Be a File, and Not a DIR");
			LogBot.logData(Status.INFO, "The Path that was passed was: " + this.location);
		}
	}
	
	public void processIncludes() {
		String currentDir = this.location.getParent();
		if(!currentDir.endsWith(File.separator)) currentDir += File.separator;
		String[] lines = this.scriptText.split(""+ASCIIFileReader.LINE_DATA_SEP);
		for(String s : lines) {
			String stripped = s.trim();
			if(stripped.startsWith("//@include")) {
				String dir = currentDir + (stripped.replace("//@include", "")).trim();
				dir = dir.replace("/", File.separator);
				dir = dir.replace("\\", File.separator);
				dir = dir.trim();
				
				try {
					this.nashorn.eval(new FileReader(new File(dir)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
				}
			}
		}
	}

	public Object runFunction(String name, Object... args) {
		if(this.nashorn == null) {
			LogBot.logData(Status.ERROR, "The Internal script Engine Is Null");
			return null;
		}
		Invocable inv = (Invocable) this.nashorn;
		try {
			return inv.invokeFunction(name, args);
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.WARNING, e.getLocalizedMessage());
			return null;
		}
	}
}