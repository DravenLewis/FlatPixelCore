package io.infinitestrike.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.plugin.*;

public class PluginLoader {
	public static HashMap<String, Plugin> loadPlugins(String location){
		LogBot.logData(LogBot.Status.DEBUG, new File(location).getPath() + "");
		ArrayList<File> files = ZIPFIleSeeker.getFilesInDirectory(new File(location).getPath());
		for(File f : files){
			if(f != null){
				LogBot.logData(LogBot.Status.INFO, "[Plugin Loader] Loading: " + f);
				Plugin.loadPlugin(f.getAbsolutePath(), PluginManager.code);
			}
		}
		
		return PluginManager.plugins;
	}
}
