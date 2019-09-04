package io.infinitestrike.core.anim;

import org.newdawn.slick.Image;

public class Animation {
	private final Image[] frames;
	private int index;
	
	
	public Animation(Image... images){
		this.frames = images;
	}
	
	public Image next(){
		if(index + 1 < frames.length){
			index += 1;
		}else{
			index = 0;
		}
		
		return frames[index];
	}
	
	public Image previous(){
		if(index - 1 >= 0){
			index -= 1;
		}else{
			index = frames.length - 1;
		}
		
		return frames[index];
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public Image getImage(int index){
		if(index > frames.length || index < 0){
			throw new IndexOutOfBoundsException(index < 0 ? index + " < 0"  :  index + " > " + frames.length);
		}
		return frames[index];
	}
	
	public Image first(){
		return frames[0];
	}
	
	public Image last(){
		return frames[frames.length - 1];
	}
	
	public int getSize(){
		return this.frames.length;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public int indexOf(Image img){
		for(int i = 0; i < this.frames.length; i++){
			if(frames[i].equals(img)){
				return i;
			}
		}
		return -1;
	}
}
