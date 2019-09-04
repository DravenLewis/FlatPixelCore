package io.infinitestrike.grafx;

import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import io.infinitestrike.core.util.ImageLoader;

public class Art {

	// if the image file wont load from the jar 
	// this may cause an error

	//private static Image CHAR_SHEET = ImageLoader.getSlickImageFromPath("res/text/Text.png");
	private static Image CHAR_SHEET = ImageLoader.getSlickImageFromStream(Art.class.getResourceAsStream("Text.png"));
	
	private static Image CHAR_0 = getSubImage(CHAR_SHEET, 0, 0, 16);
	private static Image CHAR_1 = getSubImage(CHAR_SHEET, 1, 0, 16);
	private static Image CHAR_2 = getSubImage(CHAR_SHEET, 2, 0, 16);
	private static Image CHAR_3 = getSubImage(CHAR_SHEET, 3, 0, 16);
	private static Image CHAR_4 = getSubImage(CHAR_SHEET, 4, 0, 16);
	private static Image CHAR_5 = getSubImage(CHAR_SHEET, 5, 0, 16);
	private static Image CHAR_6 = getSubImage(CHAR_SHEET, 6, 0, 16);
	private static Image CHAR_7 = getSubImage(CHAR_SHEET, 7, 0, 16);
	private static Image CHAR_8 = getSubImage(CHAR_SHEET, 8, 0, 16);
	private static Image CHAR_9 = getSubImage(CHAR_SHEET, 9, 0, 16);
	private static Image CHAR_EXP = getSubImage(CHAR_SHEET, 10, 0, 16);
	private static Image CHAR_QST = getSubImage(CHAR_SHEET, 11, 0, 16);
	private static Image CHAR_DOL = getSubImage(CHAR_SHEET, 12, 0, 16);
	private static Image CHAR_PER = getSubImage(CHAR_SHEET, 13, 0, 16);
	private static Image CHAR_AMP = getSubImage(CHAR_SHEET, 14, 0, 16);
	
	private static Image CHAR_a = getSubImage(CHAR_SHEET, 0, 1, 16);
	private static Image CHAR_b = getSubImage(CHAR_SHEET, 1, 1, 16);
	private static Image CHAR_c = getSubImage(CHAR_SHEET, 2, 1, 16);
	private static Image CHAR_d = getSubImage(CHAR_SHEET, 3, 1, 16);
	private static Image CHAR_e = getSubImage(CHAR_SHEET, 4, 1, 16);
	private static Image CHAR_f = getSubImage(CHAR_SHEET, 5, 1, 16);
	private static Image CHAR_g = getSubImage(CHAR_SHEET, 6, 1, 16);
	private static Image CHAR_h = getSubImage(CHAR_SHEET, 7, 1, 16);
	private static Image CHAR_i = getSubImage(CHAR_SHEET, 8, 1, 16);
	private static Image CHAR_j = getSubImage(CHAR_SHEET, 9, 1, 16);
	private static Image CHAR_k = getSubImage(CHAR_SHEET, 10, 1, 16);
	private static Image CHAR_l = getSubImage(CHAR_SHEET, 11, 1, 16);
	private static Image CHAR_SPA = getSubImage(CHAR_SHEET, 12, 1, 16);
	private static Image CHAR_LPR = getSubImage(CHAR_SHEET, 13, 1, 16);
	private static Image CHAR_RPR = getSubImage(CHAR_SHEET, 14, 1, 16);
	
	private static Image CHAR_m = getSubImage(CHAR_SHEET, 0, 2, 16);
	private static Image CHAR_n = getSubImage(CHAR_SHEET, 1, 2, 16);
	private static Image CHAR_o = getSubImage(CHAR_SHEET, 2, 2, 16);
	private static Image CHAR_p = getSubImage(CHAR_SHEET, 3, 2, 16);
	private static Image CHAR_q = getSubImage(CHAR_SHEET, 4, 2, 16);
	private static Image CHAR_r = getSubImage(CHAR_SHEET, 5, 2, 16);
	private static Image CHAR_s = getSubImage(CHAR_SHEET, 6, 2, 16);
	private static Image CHAR_t = getSubImage(CHAR_SHEET, 7, 2, 16);
	private static Image CHAR_u = getSubImage(CHAR_SHEET, 8, 2, 16);
	private static Image CHAR_v = getSubImage(CHAR_SHEET, 9, 2, 16);
	private static Image CHAR_w = getSubImage(CHAR_SHEET, 10, 2, 16);
	private static Image CHAR_x = getSubImage(CHAR_SHEET, 11, 2, 16);
	private static Image CHAR_EQL = getSubImage(CHAR_SHEET, 12, 2, 16); // nre
	private static Image CHAR_AST = getSubImage(CHAR_SHEET, 13, 2, 16); // end new
	
	private static Image CHAR_y = getSubImage(CHAR_SHEET, 0, 3, 16);
	private static Image CHAR_z = getSubImage(CHAR_SHEET, 1, 3, 16);
	private static Image CHAR_PRD = getSubImage(CHAR_SHEET, 2, 3, 16);
	private static Image CHAR_COM = getSubImage(CHAR_SHEET, 3, 3, 16);
	private static Image CHAR_FSL = getSubImage(CHAR_SHEET, 4, 3, 16);
	private static Image CHAR_DSH = getSubImage(CHAR_SHEET, 5, 3, 16); // new
	private static Image CHAR_USC = getSubImage(CHAR_SHEET, 6, 3, 16);
	private static Image CHAR_LAB = getSubImage(CHAR_SHEET, 7, 3, 16);
	private static Image CHAR_RAB = getSubImage(CHAR_SHEET, 8, 3, 16);
	private static Image CHAR_LCB = getSubImage(CHAR_SHEET, 9, 3, 16);
	private static Image CHAR_RCB = getSubImage(CHAR_SHEET, 10, 3, 16);
	private static Image CHAR_LSB = getSubImage(CHAR_SHEET, 11, 3, 16);
	private static Image CHAR_RSB = getSubImage(CHAR_SHEET, 12, 3, 16);
	private static Image CHAR_ATS = getSubImage(CHAR_SHEET, 13, 3, 16);
	private static Image CHAR_PLS = getSubImage(CHAR_SHEET, 14, 3, 16);// end new 
	
	private static Image CHAR_I_RAR = getSubImage(CHAR_SHEET, 0, 5, 16);
	private static Image CHAR_I_LAR = getSubImage(CHAR_SHEET, 1, 5, 16);
	private static Image CHAR_I_UAR = getSubImage(CHAR_SHEET, 2, 5, 16);
	private static Image CHAR_I_DAR = getSubImage(CHAR_SHEET, 3, 5, 16);
	private static Image CHAR_I_UFC = getSubImage(CHAR_SHEET, 4, 5, 16);
	private static Image CHAR_I_HFC = getSubImage(CHAR_SHEET, 5, 5, 16);
	private static Image CHAR_I_EFC = getSubImage(CHAR_SHEET, 6, 5, 16);
	private static Image CHAR_I_SFC = getSubImage(CHAR_SHEET, 7, 5, 16);
	
	private static Image CHAR_S_SWD = getSubImage(CHAR_SHEET, 8, 5, 16);
	private static Image CHAR_S_SHD = getSubImage(CHAR_SHEET, 9, 5, 16);
	private static Image CHAR_S_UKN = getSubImage(CHAR_SHEET, 10, 5, 16);
	
	private static HashMap<Character,Image> charMap = new HashMap<Character,Image>();
	
	public static final char RIGHT_ARROW 		= (char) 215;
	public static final char LEFT_ARROW 		= (char) 216;
	public static final char UP_ARROW 			= (char) 217;
	public static final char DOWN_ARROW 		= (char) 218;
	public static final char UNCHANGED_FACE 	= (char) 219;
	public static final char HAPPY_FACE 		= (char) 220;
	public static final char EVIL_FACE 			= (char) 221;
	public static final char SAD_FACE 			= (char) 222;
	public static final char SWORD 				= (char) 223;
	public static final char SHIELD 			= (char) 224;
	public static final char UNKNOWN_SYMBOL 	= (char) 225;
	
	public static final char RANDOM_SYMBOL      = (char) 255;
	
	static{
		charMap.put('0', CHAR_0); charMap.put('a', CHAR_a); charMap.put('m', CHAR_m); charMap.put(',', CHAR_COM);  charMap.put('_', CHAR_USC); 
		charMap.put('1', CHAR_1); charMap.put('b', CHAR_b); charMap.put('n', CHAR_n); charMap.put('/', CHAR_FSL);  charMap.put('<', CHAR_LAB);
		charMap.put('2', CHAR_2); charMap.put('c', CHAR_c); charMap.put('o', CHAR_o); charMap.put('=', CHAR_EQL);  charMap.put('>', CHAR_RAB);
		charMap.put('3', CHAR_3); charMap.put('d', CHAR_d); charMap.put('p', CHAR_p); charMap.put((char) 215, CHAR_I_RAR);  charMap.put('{', CHAR_LCB);
		charMap.put('4', CHAR_4); charMap.put('e', CHAR_e); charMap.put('q', CHAR_q); charMap.put((char) 216, CHAR_I_LAR);  charMap.put('}', CHAR_RCB);
		charMap.put('5', CHAR_5); charMap.put('f', CHAR_f); charMap.put('r', CHAR_r); charMap.put((char) 217, CHAR_I_UAR);  charMap.put('[', CHAR_LSB);
		charMap.put('6', CHAR_6); charMap.put('g', CHAR_g); charMap.put('s', CHAR_s); charMap.put((char) 218, CHAR_I_DAR);  charMap.put(']', CHAR_RSB);
		charMap.put('7', CHAR_7); charMap.put('h', CHAR_h); charMap.put('t', CHAR_t); charMap.put((char) 219, CHAR_I_UFC);  charMap.put('@', CHAR_ATS);
		charMap.put('8', CHAR_8); charMap.put('i', CHAR_i); charMap.put('u', CHAR_u); charMap.put((char) 220, CHAR_I_HFC);  charMap.put('+', CHAR_PLS);
		charMap.put('9', CHAR_9); charMap.put('j', CHAR_j); charMap.put('v', CHAR_v); charMap.put((char) 221, CHAR_I_EFC);  
		charMap.put('!', CHAR_EXP); charMap.put('k', CHAR_k); charMap.put('w', CHAR_w); charMap.put((char) 222, CHAR_I_SFC);
		charMap.put('?', CHAR_QST); charMap.put('l', CHAR_l); charMap.put('x', CHAR_x); charMap.put('*', CHAR_AST);
		charMap.put('$', CHAR_DOL); charMap.put(' ', CHAR_SPA); charMap.put('y', CHAR_y); charMap.put((char) 223, CHAR_S_SWD);
		charMap.put('%', CHAR_PER); charMap.put('(', CHAR_LPR); charMap.put('z', CHAR_z); charMap.put((char) 224, CHAR_S_SHD);
		charMap.put('&', CHAR_AMP); charMap.put(')', CHAR_RPR); charMap.put('.', CHAR_PRD); charMap.put('-', CHAR_DSH);  charMap.put((char) 225, CHAR_S_UKN);
	}
	

	public static void setCharacterSheet(Image i){
		CHAR_SHEET = i;
		
		
		 CHAR_0 = getSubImage(CHAR_SHEET, 0, 0, 16);
		 CHAR_1 = getSubImage(CHAR_SHEET, 1, 0, 16);
		 CHAR_2 = getSubImage(CHAR_SHEET, 2, 0, 16);
		 CHAR_3 = getSubImage(CHAR_SHEET, 3, 0, 16);
		 CHAR_4 = getSubImage(CHAR_SHEET, 4, 0, 16);
		 CHAR_5 = getSubImage(CHAR_SHEET, 5, 0, 16);
		 CHAR_6 = getSubImage(CHAR_SHEET, 6, 0, 16);
		 CHAR_7 = getSubImage(CHAR_SHEET, 7, 0, 16);
		 CHAR_8 = getSubImage(CHAR_SHEET, 8, 0, 16);
		 CHAR_9 = getSubImage(CHAR_SHEET, 9, 0, 16);
		 CHAR_EXP = getSubImage(CHAR_SHEET, 10, 0, 16);
		 CHAR_QST = getSubImage(CHAR_SHEET, 11, 0, 16);
		 CHAR_DOL = getSubImage(CHAR_SHEET, 12, 0, 16);
		 CHAR_PER = getSubImage(CHAR_SHEET, 13, 0, 16);
		 CHAR_AMP = getSubImage(CHAR_SHEET, 14, 0, 16);
		
		 CHAR_a = getSubImage(CHAR_SHEET, 0, 1, 16);
		 CHAR_b = getSubImage(CHAR_SHEET, 1, 1, 16);
		 CHAR_c = getSubImage(CHAR_SHEET, 2, 1, 16);
		 CHAR_d = getSubImage(CHAR_SHEET, 3, 1, 16);
		 CHAR_e = getSubImage(CHAR_SHEET, 4, 1, 16);
		 CHAR_f = getSubImage(CHAR_SHEET, 5, 1, 16);
		 CHAR_g = getSubImage(CHAR_SHEET, 6, 1, 16);
		 CHAR_h = getSubImage(CHAR_SHEET, 7, 1, 16);
		 CHAR_i = getSubImage(CHAR_SHEET, 8, 1, 16);
		 CHAR_j = getSubImage(CHAR_SHEET, 9, 1, 16);
		 CHAR_k = getSubImage(CHAR_SHEET, 10, 1, 16);
		 CHAR_l = getSubImage(CHAR_SHEET, 11, 1, 16);
		 CHAR_SPA = getSubImage(CHAR_SHEET, 12, 1, 16);
		 CHAR_LPR = getSubImage(CHAR_SHEET, 13, 1, 16);
		 CHAR_RPR = getSubImage(CHAR_SHEET, 14, 1, 16);
		
		 CHAR_m = getSubImage(CHAR_SHEET, 0, 2, 16);
		 CHAR_n = getSubImage(CHAR_SHEET, 1, 2, 16);
		 CHAR_o = getSubImage(CHAR_SHEET, 2, 2, 16);
		 CHAR_p = getSubImage(CHAR_SHEET, 3, 2, 16);
		 CHAR_q = getSubImage(CHAR_SHEET, 4, 2, 16);
		 CHAR_r = getSubImage(CHAR_SHEET, 5, 2, 16);
		 CHAR_s = getSubImage(CHAR_SHEET, 6, 2, 16);
		 CHAR_t = getSubImage(CHAR_SHEET, 7, 2, 16);
		 CHAR_u = getSubImage(CHAR_SHEET, 8, 2, 16);
		 CHAR_v = getSubImage(CHAR_SHEET, 9, 2, 16);
		 CHAR_w = getSubImage(CHAR_SHEET, 10, 2, 16);
		 CHAR_x = getSubImage(CHAR_SHEET, 11, 2, 16);
		 CHAR_EQL = getSubImage(CHAR_SHEET, 12, 2, 16); // nre
		 CHAR_AST = getSubImage(CHAR_SHEET, 13, 2, 16); // end new
		
		 CHAR_y = getSubImage(CHAR_SHEET, 0, 3, 16);
		 CHAR_z = getSubImage(CHAR_SHEET, 1, 3, 16);
		 CHAR_PRD = getSubImage(CHAR_SHEET, 2, 3, 16);
		 CHAR_COM = getSubImage(CHAR_SHEET, 3, 3, 16);
		 CHAR_FSL = getSubImage(CHAR_SHEET, 4, 3, 16);
		 CHAR_DSH = getSubImage(CHAR_SHEET, 5, 3, 16); // new
		 CHAR_USC = getSubImage(CHAR_SHEET, 6, 3, 16);
		 CHAR_LAB = getSubImage(CHAR_SHEET, 7, 3, 16);
		 CHAR_RAB = getSubImage(CHAR_SHEET, 8, 3, 16);
		 CHAR_LCB = getSubImage(CHAR_SHEET, 9, 3, 16);
		 CHAR_RCB = getSubImage(CHAR_SHEET, 10, 3, 16);
		 CHAR_LSB = getSubImage(CHAR_SHEET, 11, 3, 16);
		 CHAR_RSB = getSubImage(CHAR_SHEET, 12, 3, 16);
		 CHAR_ATS = getSubImage(CHAR_SHEET, 13, 3, 16);
		 CHAR_PLS = getSubImage(CHAR_SHEET, 14, 3, 16);// end new 
		
		 CHAR_I_RAR = getSubImage(CHAR_SHEET, 0, 5, 16);
		 CHAR_I_LAR = getSubImage(CHAR_SHEET, 1, 5, 16);
		 CHAR_I_UAR = getSubImage(CHAR_SHEET, 2, 5, 16);
		 CHAR_I_DAR = getSubImage(CHAR_SHEET, 3, 5, 16);
		 CHAR_I_UFC = getSubImage(CHAR_SHEET, 4, 5, 16);
		 CHAR_I_HFC = getSubImage(CHAR_SHEET, 5, 5, 16);
		 CHAR_I_EFC = getSubImage(CHAR_SHEET, 6, 5, 16);
		 CHAR_I_SFC = getSubImage(CHAR_SHEET, 7, 5, 16);
		
		 CHAR_S_SWD = getSubImage(CHAR_SHEET, 8, 5, 16);
		 CHAR_S_SHD = getSubImage(CHAR_SHEET, 9, 5, 16);
		 CHAR_S_UKN = getSubImage(CHAR_SHEET, 10, 5, 16);
		
		
		charMap.put('0', CHAR_0); charMap.put('a', CHAR_a); charMap.put('m', CHAR_m); charMap.put(',', CHAR_COM);  charMap.put('_', CHAR_USC); 
		charMap.put('1', CHAR_1); charMap.put('b', CHAR_b); charMap.put('n', CHAR_n); charMap.put('/', CHAR_FSL);  charMap.put('<', CHAR_LAB);
		charMap.put('2', CHAR_2); charMap.put('c', CHAR_c); charMap.put('o', CHAR_o); charMap.put('=', CHAR_EQL);  charMap.put('>', CHAR_RAB);
		charMap.put('3', CHAR_3); charMap.put('d', CHAR_d); charMap.put('p', CHAR_p); charMap.put((char) 215, CHAR_I_RAR);  charMap.put('{', CHAR_LCB);
		charMap.put('4', CHAR_4); charMap.put('e', CHAR_e); charMap.put('q', CHAR_q); charMap.put((char) 216, CHAR_I_LAR);  charMap.put('}', CHAR_RCB);
		charMap.put('5', CHAR_5); charMap.put('f', CHAR_f); charMap.put('r', CHAR_r); charMap.put((char) 217, CHAR_I_UAR);  charMap.put('[', CHAR_LSB);
		charMap.put('6', CHAR_6); charMap.put('g', CHAR_g); charMap.put('s', CHAR_s); charMap.put((char) 218, CHAR_I_DAR);  charMap.put(']', CHAR_RSB);
		charMap.put('7', CHAR_7); charMap.put('h', CHAR_h); charMap.put('t', CHAR_t); charMap.put((char) 219, CHAR_I_UFC);  charMap.put('@', CHAR_ATS);
		charMap.put('8', CHAR_8); charMap.put('i', CHAR_i); charMap.put('u', CHAR_u); charMap.put((char) 220, CHAR_I_HFC);  charMap.put('+', CHAR_PLS);
		charMap.put('9', CHAR_9); charMap.put('j', CHAR_j); charMap.put('v', CHAR_v); charMap.put((char) 221, CHAR_I_EFC);  
		charMap.put('!', CHAR_EXP); charMap.put('k', CHAR_k); charMap.put('w', CHAR_w); charMap.put((char) 222, CHAR_I_SFC);
		charMap.put('?', CHAR_QST); charMap.put('l', CHAR_l); charMap.put('x', CHAR_x); charMap.put('*', CHAR_AST);
		charMap.put('$', CHAR_DOL); charMap.put(' ', CHAR_SPA); charMap.put('y', CHAR_y); charMap.put((char) 223, CHAR_S_SWD);
		charMap.put('%', CHAR_PER); charMap.put('(', CHAR_LPR); charMap.put('z', CHAR_z); charMap.put((char) 224, CHAR_S_SHD);
		charMap.put('&', CHAR_AMP); charMap.put(')', CHAR_RPR); charMap.put('.', CHAR_PRD); charMap.put('-', CHAR_DSH);  charMap.put((char) 225, CHAR_S_UKN);
	}


	public static void makeText(Graphics g, int x, int y, char[] c){
		int row = 0;
		int col = 0;
		
		StringBuilder b = new StringBuilder();
		b.append(c);
		
		String lowerInput = b.toString();
		
		for(int i = 0; i < lowerInput.length(); i++){
			if(lowerInput.charAt(i) == '\n'){
				row += 16;
				col = 0;
			}else if(lowerInput.charAt(i) == (char) 255){
				g.drawImage((Image) charMap.values().toArray()[new Random().nextInt(charMap.size())], x + (col * 16), y + row);
			}else if(!charMap.containsKey(lowerInput.charAt(i))){
				g.drawImage(charMap.get((char) 225), x + (col * 16), y + row);
			}else{
				g.drawImage(charMap.get(lowerInput.charAt(i)), x + (col * 16), y + row);
			}
			col+=1;
		}
	}
	
	public static void makeText(Graphics g, int x, int y, int[] c){
		int row = 0;
		int col = 0;
		
		String lowerInput = "";
		
		for(int i = 0; i < c.length; i++){
			lowerInput += (char) c[i];
		}
		
		for(int i = 0; i < lowerInput.length(); i++){
			if(lowerInput.charAt(i) == '\n'){
				row += 16;
				col = 0;
			}else if(lowerInput.charAt(i) == (char) 255){
				g.drawImage((Image) charMap.values().toArray()[new Random().nextInt(charMap.size())], x + (col * 16), y + row);
			}else if(!charMap.containsKey(lowerInput.charAt(i))){
				g.drawImage(charMap.get((char) 225), x + (col * 16), y + row);
			}else{
				g.drawImage(charMap.get(lowerInput.charAt(i)), x + (col * 16), y + row);
			}
			col+=1;
		}
	}
	
	public static void makeText(Graphics g, int x, int y, String s){
		int row = 0;
		int col = 0;
		String lowerInput = s;
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '\n'){
				row += 16;
				col = 0;
			}else if(lowerInput.charAt(i) == (char) 255){
				g.drawImage((Image) charMap.values().toArray()[new Random().nextInt(charMap.size())], x + (col * 16), y + row);
			}else if(!charMap.containsKey(lowerInput.charAt(i))){
				g.drawImage(charMap.get((char) 225), x + (col * 16), y + row);
			}else{
				g.drawImage(charMap.get(s.charAt(i)), x + (col * 16), y + row);
			}
			col+=1;
		}
	}

	
	public static void makeText(Graphics g, int x, int y, String s, Color c){
		int row = 0;
		int col = 0;
		String lowerInput = s;
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '\n'){
				row += 16;
				col = 0;
			}else if(lowerInput.charAt(i) == (char) 255){
				g.drawImage((Image) charMap.values().toArray()[new Random().nextInt(charMap.size())], x + (col * 16), y + row);
			}else if(!charMap.containsKey(lowerInput.charAt(i))){
				g.drawImage(charMap.get((char) 225), x + (col * 16), y + row, c);
			}else{
				g.drawImage(charMap.get(s.charAt(i)), x + (col * 16), y + row, c);
			}
			col+=1;
		}
	}
	
	public static void makeText(Graphics g, int x, int y, String s, Color[] cl){
		int row = 0;
		int col = 0;
		String lowerInput = s;
		for(int i = 0; i < s.length(); i++){
			
			Color c = i < cl.length ? cl[i] : Color.white;
			
			if(s.charAt(i) == '\n'){
				row += 16;
				col = 0;
			}else if(lowerInput.charAt(i) == (char) 255){
				g.drawImage((Image) charMap.values().toArray()[new Random().nextInt(charMap.size())], x + (col * 16), y + row);
			}else if(!charMap.containsKey(lowerInput.charAt(i))){
				g.drawImage(charMap.get((char) 225), x + (col * 16), y + row, c);
			}else{
				g.drawImage(charMap.get(s.charAt(i)), x + (col * 16), y + row, c);
			}
			col+=1;
		}
	}
	
	private static Image getSubImage(Image img, int x, int y, int tilesize){
		return img.getSubImage(x * tilesize, y * tilesize, tilesize, tilesize);
	}
}
