package io.infinitestrike.objdi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;


/**
 * 
 * Class that allows for a generic version of a class.
 *
 */
public class ObjectDataInformation {	
	// we will load the data from a file 
	// then parse it into a hashmap
	// the # is ignored.
	
	private ArrayList<String> programmingLines = new ArrayList<String>();
	private HashMap<String,String> mappedValues = new HashMap<String,String>();
	
	
	public void setDataFromFile(File f){
		this.resetData();
		this.loadFile(f);
		this.parseDataIntoMap();
	}
	
	public void setDataFromStream(InputStream s){
		this.resetData();
		this.loadFileDataFromStream(s);
		this.parseDataIntoMap();
	}
	
	public void loadFileDataFromStream(InputStream stream){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			while((line = reader.readLine()) != null){
				this.programmingLines.add(line.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}
	}
	
	public void loadFile(File f){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = reader.readLine()) != null){
				this.programmingLines.add(line.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}
	}
	
	public void parseDataIntoMap(){
		int lineIndex = 0;
		for(String s : this.programmingLines){
			
			if(s.length() <= 0){
				continue;
			}
			
			if(s.charAt(0) == '#'){
				// if the line starts with #
				// then its a comment and it shoulnt
				// be interpreted.
				
				lineIndex++;
				continue;
			}
			// Standard format for data entry is
			// key = data
			if(s.contains("=")){
				String key = "";
				String value = "";
				
				String[] stringSinglet = s.split("=", 2);
				if(stringSinglet.length >= 1){
					key = stringSinglet[0];
					
					if(stringSinglet.length < 2){
						value = "null";
					}else if(stringSinglet.length >= 2){
						value = stringSinglet[1];
					}
				}
				
				this.mappedValues.put(key.trim(), value.trim());
			}else{
				LogBot.logData(LogBot.Status.WARNING, String.format("Invalid Syntax on Line %s; Valid Syntax is key = value", lineIndex));
			}
			lineIndex++;
		}
	}
	
	public void resetData(){
		this.programmingLines = new ArrayList<String>();
		this.mappedValues = new HashMap<String,String>();
	}
	
	public HashMap<String,String> getMapData(){
		return this.mappedValues;
	}
}
