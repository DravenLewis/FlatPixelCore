package io.infinitestrike.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class ASCIIFileReader {
	
	public static final char LINE_DATA_SEP = (char) 0x12;
	
	public static String getFileContents(File f) {
		String fileContents = "";
		String line = "";
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(f));
			while ((line = reader.readLine()) != null) {
				fileContents += line + LINE_DATA_SEP + '\n';
			}
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.WARNING, "Problem while loading file: " + e.getLocalizedMessage());
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileContents;
	}
}
