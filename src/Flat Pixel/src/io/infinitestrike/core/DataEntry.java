package io.infinitestrike.core;

import java.util.ArrayList;

public class DataEntry {
	int dataEntryInt = 0;
	String dataEntryString = "";
	
	public final int getDataEntryInt() {
		return dataEntryInt;
	}

	public final String getDataEntryString() {
		return dataEntryString;
	}

	public DataEntry(String name, int value){
		this.dataEntryInt = value;
		this.dataEntryString = name;
	}
	
	public static final boolean containsValue(ArrayList<DataEntry> entry, String name){
		for(DataEntry e : entry){
			if(e.getDataEntryString().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public static final boolean containsValue(DataEntry[] entry, String name){
		for(DataEntry e : entry){
			if(e.getDataEntryString().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public static final DataEntry getEntry(DataEntry[] entry, String name){
		for(DataEntry e : entry){
			if(e.getDataEntryString().equals(name)){
				return e;
			}
		}
		return null;
	}
	
	public static final DataEntry getEntry(ArrayList<DataEntry> entry, String name){
		for(DataEntry e : entry){
			if(e.getDataEntryString().equals(name)){
				return e;
			}
		}
		return null;
	}
	
	public static final DataEntry[] sort(DataEntry[] dat){
		for (int pass = 0; pass < dat.length; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				DataEntry index_i = dat[i];
				DataEntry index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.dataEntryInt  < index_i.dataEntryInt) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static final DataEntry[] asort(DataEntry[] dat){
		for (int pass = 0; pass < dat.length; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				DataEntry index_i = dat[i];
				DataEntry index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.dataEntryInt  > index_i.dataEntryInt) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		return dat;
	}
	
	public static final ArrayList<DataEntry> sortAlt(DataEntry[] dat){
		for (int pass = 0; pass < dat.length; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				DataEntry index_i = dat[i];
				DataEntry index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.dataEntryInt  < index_i.dataEntryInt) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		
		ArrayList<DataEntry> e = new ArrayList<DataEntry>();
		for(DataEntry d : dat){
			e.add(d);
		}
		return e;
	}
	
	public static final ArrayList<DataEntry> asortAlt(DataEntry[] dat){
		for (int pass = 0; pass < dat.length; pass++) {
			for (int i = 0; i < dat.length - 1; i++) {
				DataEntry index_i = dat[i];
				DataEntry index_nxt = dat[i + 1];

				// Greatest to least
				if (index_nxt.dataEntryInt  > index_i.dataEntryInt) {
					dat[i + 1] = index_i;
					dat[i] = index_nxt;
				}
			}
		}
		
		ArrayList<DataEntry> e = new ArrayList<DataEntry>();
		for(DataEntry d : dat){
			e.add(d);
		}
		return e;
	}
	
	public static final ArrayList<DataEntry> convertToArrayList(DataEntry[] dat){
		ArrayList<DataEntry> e = new ArrayList<DataEntry>();
		for(DataEntry s : dat){
			e.add(s);
		}
		return e;
	}
	
	public static final DataEntry[] convertToArray(ArrayList<DataEntry> dat){
		DataEntry[] e = new DataEntry[dat.size()];
		for(int i = 0; i < dat.size(); i++){
			e[i] = dat.get(i);
		}
		return e;
	}
}
