package io.infinitestrike.entity.item;

import java.util.HashMap;

public class ItemMetaData {
	private String METAURL = ""; // x=0;y=0;w=0;h=0;
	
	public void addMETATag(String tagName, String value){
		METAURL += tagName + "=" + value + ";";
	}
}
