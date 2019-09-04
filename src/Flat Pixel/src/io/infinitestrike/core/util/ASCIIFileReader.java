package io.infinitestrike.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class ASCIIFileReader {
	public static String getFileContents(String path) {
		String fileContents = "";
		String line = "";

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.loadResource(path)));
			while ((line = reader.readLine()) != null) {
				if (line.trim() == "" || line.indexOf("//") == 0) {
					continue;
				}
				fileContents += line;
			}
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.WARNING, "Problem while loading file: " + e.getLocalizedMessage());
		}
		return fileContents;
	}
}
