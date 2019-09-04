package io.infinitestrike.core.util;

import org.newdawn.slick.Color;

public class Utils {
	public static Color getColorFromHexValues(String value){
		String manip_value = value;
		if(manip_value.contains("#")){manip_value.replace("#","");}
		if(manip_value.contains("0x")){manip_value.replace("0x", "");}
		
		return Color.decode(manip_value);
	}
}
