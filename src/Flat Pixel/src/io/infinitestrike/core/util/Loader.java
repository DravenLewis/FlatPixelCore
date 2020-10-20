package io.infinitestrike.core.util;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class Loader {
	public static LoaderResult readFile(String f) throws FileNotFoundException {
		return Loader.readFile(new File(f));
	}

	public static LoaderResult readFile(File f) throws FileNotFoundException {
		String settingsFile = ASCIIFileReader.getFileContents(f);
		LoaderResult resultsCache = new LoaderResult();
		// split based on newlines
		String[] lines = settingsFile.split("\n");
		int currentLine = 0;
		for (String line : lines) {
			if(line.startsWith("##")) continue;
			if(line.trim().isEmpty()) continue;
			line = line.replace(";", "").replace((char) 18 + "", ""); // remove delimiter, remove error line ending.
			// =================================================
			String[] typeSplit = line.split(":");
			LoaderEntry entry = new LoaderEntry();
			LoaderType type = getType(typeSplit[0]);
			entry.entryType = type;
			// Check
			if(!typeSplit[1].contains("=")) {
				LogBot.logData(Status.ERROR, "SyntaxError on line: " + currentLine + ". No '=' Delimiter in key-value pair.");
				LogBot.logData(Status.ERROR,line);
				LogBot.logData(Status.ERROR, "^");
				return null;
			}
			String[] keyValuePair = typeSplit[1].split("=");

			String key = keyValuePair[0];
			String value = keyValuePair[1];
			entry.entryValue = value;

			resultsCache.set(key, entry);
			currentLine++;
		}
		return resultsCache;
	}

	protected static LoaderType getType(String s) {
		String typeString = s.toLowerCase();
		switch (typeString) {
		case "l":
		case "long":
			return LoaderType.INT_64;
		case "i":
		case "integer":
			return LoaderType.INT_32;
		case "f":
		case "float":
			return LoaderType.FLOAT;
		case "s":
		case "string":
			return LoaderType.STRING;
		case "h":
		case "hex":
			return LoaderType.HEX;
		case "a":
		case "array":
			return LoaderType.ARRAY;
		case "b":
		case "boolean":
			return LoaderType.BOOL;
		case "d":
		case "directory":
			return LoaderType.DIR;
		case "o":
		case "option":
			return LoaderType.OPTION;
		default:
			return LoaderType.STRING;
		}
	}
	
	protected static String getNmonic(LoaderType s) {
		switch(s) {
			case INT_32:
				return "Integer";
			case INT_64:
				return "Long";
			case FLOAT:
				return "Float";
			case STRING:
				return "String";
			case HEX:
				return "Hex";
			case ARRAY:
				return "Array";
			case BOOL:
				return "Boolean";
			case DIR:
				return "Directory";
			case OPTION:
				return "Option";
			default:
				throw new IllegalArgumentException("Corrupted Loader Type.");
		}
	}
	
	public static String LoaderResultToString(LoaderResult r) {
		String finalFile = "";
		finalFile += "\n## This file is machine generated, do not edit. \n";
		finalFile += "## Generated on " + new Date() + "\n\n";
		for(int i = 0; i < r.size(); i++) {
			String line = "";
			line += getNmonic(r.getValue(i).entryType) + ":";
			line += r.getKey(i) + "=";
			line += r.getValue(i).entryValue + ";";
			finalFile += line + "\n";
		}
		return finalFile;
	}
	
	public static class LoaderResult {
		private final HashMap<String, LoaderEntry> results = new HashMap<String, LoaderEntry>();

		public int size() {
			return results.size();
		}
		
		public String getKey(int index) {
			return (String) this.results.keySet().toArray()[index];
		}
		
		public LoaderEntry getValue(int index) {
			return (LoaderEntry) this.results.values().toArray()[index];
		}
		
		public void set(String key, LoaderEntry value) {
			this.results.put(key, value);
		}

		public LoaderEntry valueOf(String key, String fallback) {
			if (this.results.containsKey(key)) {
				return this.results.get(key);
			}
		
			return new LoaderEntry(LoaderType.STRING, fallback);
		}
		
		public HashMap<String, LoaderEntry> getCopy(){
			return new HashMap<String, LoaderEntry>(results);
		}
		
		public void setMap(HashMap<String, LoaderEntry> copy) {
			LogBot.logData(Status.INFO,"Reverting Changes");
			Core.HashMapCopy(copy, results, true);
		}
		
		public boolean isMapSame(HashMap<String,LoaderEntry> m) {
			return this.results.equals(m);
		}
	}
	
	public static class LoaderEntry {
		
		public LoaderEntry() {}
		
		public LoaderEntry(LoaderType t, Object value) {
			this.entryType = t;
			this.entryValue = value.toString();
		}
		public LoaderType entryType;
		public String entryValue;
		
		public String[] entryValueToArray() {
			if(entryValue.contains(",")) {
				return entryValue.split(",");
			}else {
				return new String[] {entryValue};
			}
		}
		
		public static LoaderEntry makeEntry(int value) {
			return new LoaderEntry(LoaderType.INT_32,value);
		}
		
		public static LoaderEntry makeEntry(long value) {
			return new LoaderEntry(LoaderType.INT_64,value);
		}
		
		public static LoaderEntry makeEntry(float value) {
			return new LoaderEntry(LoaderType.FLOAT,value);
		}
		
		public static LoaderEntry makeEntry(String value) {
			return new LoaderEntry(LoaderType.STRING,value);
		}
		
		public static LoaderEntry makeHexEntry(String value) {
			return new LoaderEntry(LoaderType.HEX,value);
		}
		
		public static LoaderEntry makeEntry(Object[] arr) {
			String srr = "";
			for(Object o : arr) srr += o.toString() + ",";
			srr = srr.substring(0, srr.length()-1);
			return new LoaderEntry(LoaderType.ARRAY,srr);
		}
		
		public static LoaderEntry makeEntry(boolean value) {
			return new LoaderEntry(LoaderType.BOOL,value);
		}
		
		public static LoaderEntry makeEntryDir(String value) {
			return new LoaderEntry(LoaderType.DIR,value);
		}
		
		public static LoaderEntry makeEntryOption(int index, String key) {
			return Loader.constructOptionEntry(index, key);
		}
		
		public String toString() {
			return "{Entry[type='"+this.entryType+"', value='"+this.entryValue+"']}";
		}
	}

	public static enum LoaderType {
		// B, L, F, S
		INT_32, INT_64, FLOAT, STRING, HEX, ARRAY, BOOL, DIR, OPTION
	}

	public static boolean getValueBoolean(LoaderEntry e) {
		if (e.entryType == LoaderType.BOOL) {
			try {
				return Boolean.parseBoolean(e.entryValue);
			} catch (Exception ex) {
				LogBot.logDataVerbose(ex,Status.ERROR,"Loaded Value-Type Was Not Bool.");
			}
		}
		throw new IllegalArgumentException("Loaded Value-Type Was Not Bool.");
	}

	public static int getValueInt(LoaderEntry e) {
		try {
			if (e.entryType == LoaderType.INT_32) {
				return Integer.parseInt(e.entryValue);
			}
		} catch (Exception ex) {
			LogBot.logDataVerbose(ex,Status.ERROR,"Loaded Value-Type Was Not Integer.");
		}
		throw new IllegalArgumentException("Loaded Value-Type Was Not Integer.");
	}

	public static int getValueHex(LoaderEntry e) {
		try {
			if (e.entryType == LoaderType.HEX) {
				return Integer.parseInt(e.entryValue, 16);
			}
		} catch (Exception ex) {
			LogBot.logDataVerbose(ex,Status.ERROR,"Loaded Value-Type Was Not Hex.");
		}
		throw new IllegalArgumentException("Loaded Value-Type Was Not Hex.");
	}
	
	public static long getValueLong(LoaderEntry e) {
		try {
			if (e.entryType == LoaderType.INT_64) {
				return Long.parseLong(e.entryValue);
			}
		} catch (Exception ex) {
			LogBot.logDataVerbose(ex,Status.ERROR,"Loaded Value-Type Was Not Long.");
		}
		throw new IllegalArgumentException("Loaded Value-Type Was Not Long.");
	}

	public static float getValueFloat(LoaderEntry e) {
		try {
			if (e.entryType == LoaderType.FLOAT) {
				return Float.parseFloat(e.entryValue);
			}
		} catch (Exception ex) {
			LogBot.logDataVerbose(ex,Status.ERROR,"Loaded Value-Type Was Not Float.");
		}
		throw new IllegalArgumentException("Loaded Value-Type Was Not Float.");
	}

	public static String[] getValueArray(LoaderEntry e) {
		if(e.entryType == LoaderType.ARRAY || e.entryType == LoaderType.STRING) { // single entry arrays can be read as strings
			if(e.entryValue.contains(",")) {
				String[] list = e.entryValue.split(",");
				for(String s : list) {
					if(s.length() == 0 || s.isEmpty()) {
						throw new IllegalArgumentException("Supplied Entry Contains a Syntax Error.");
					}
				}
				return list;
			}else {
				String[] list = new String[] {e.entryValue};
				return list;
			}
		}
		throw new IllegalArgumentException("Loaded Value-Type Was Not Array.");
	}
	
	public static String getValueOption(LoaderEntry e, LoaderResult source) {
		if(e.entryType == LoaderType.OPTION) {
			if(!e.entryValue.contains(",") || e.entryValue.isEmpty()) {
				throw new IllegalArgumentException("Supplied Entry Contains a Syntax Error.");
			}
			int index = Integer.parseInt(e.entryValue.split(",")[0]);
			try {
				String[] sourceArray = getValueArray(source.valueOf(e.entryValue.split(",")[1], ""));
				return sourceArray[index];
			}catch(IllegalArgumentException ex) {
				LogBot.logDataVerbose(ex,Status.ERROR,"Error Occurred while parsing elements: ");
			}
		}
		throw new IllegalArgumentException("Loaded Value-Type Was Not Option.");
	}
	
	public static LoaderEntry constructOptionEntry(int choice, String entryName) {
		return new LoaderEntry(LoaderType.OPTION,choice + "," + entryName);
	}
	
	public static String getValueString(LoaderEntry e) {
		return e.entryValue;
	}
	
	public static String keyNameToString(String keyName) {
		String format = keyName.replace("$", "").toLowerCase().replace("_", " ");
		return format.substring(0,1).toUpperCase() + format.substring(1).toLowerCase();
	}
}
