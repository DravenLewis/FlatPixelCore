package io.infinitestrike.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class ResourceLoader {
	public static InputStream loadResource(String path){
		return ResourceLoader.class.getClass().getResourceAsStream(path);
	}
	
	public static InputStream loadResourceFromFile(File f) {
		try {
			InputStream fis = new FileInputStream(f);
			InputStream bis = new BufferedInputStream(fis);
			return bis;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, "Could not load file: " + f.getAbsolutePath());
		}
		return null;
	}
}
