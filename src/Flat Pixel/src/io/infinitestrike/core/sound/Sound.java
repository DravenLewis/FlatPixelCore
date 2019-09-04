package io.infinitestrike.core.sound;

import org.newdawn.slick.openal.Audio;

public final class Sound {
	private Audio sound = null;
	private float pitch = 1.0f;
	private float gain  = 1.0f;
	private boolean loop = false;
	protected String MD5_HASH = "";
	
	
	private float x,y,z = 0;
	
	private float position = 0;
	private int mode = 0;
	
	public void seek(float pos){
		position = pos;
		sound.setPosition(position);
	}
	
	public float getPitch() {
		return pitch;
	}

	public float getGain() {
		return gain;
	}

	public float getPosition() {
		return position;
	}
	
	public void setAudio(Audio a){
		this.sound = a;
	}
	
	public void setPitch(float pitch){
		this.pitch = pitch;
	}
	
	public void setGain(float gain){
		this.gain = gain;
	}
	
	public void setLoop(boolean loop){
		this.loop = loop;
	}
	
	public void playAsMusic(){
		sound.playAsMusic(pitch, gain, loop);
		mode = 0;
	}
	
	public void playAsEffect(){
		sound.playAsSoundEffect(pitch, gain, loop);
		mode = 1;
	}
	
	public void playAsEffect3D(float x, float y, float z){
		sound.playAsSoundEffect(pitch, gain, loop,x,y,z);
		this.x = x;
		this.y = y;
		this.z = z;
		mode = 2;
	}
	
	public void pause(){
		this.position = sound.getPosition();
		sound.stop();
	}
	
	public void resume(){
		switch(mode){
			case 0:
				sound.playAsMusic(pitch, gain, loop);
				break;
			case 1:
				sound.playAsSoundEffect(pitch, gain, loop);
				break;
			case 2:
				sound.playAsSoundEffect(pitch, gain, loop, x, y, z);
				break;
		}
	}
	
	public void stop(){
		sound.stop();
	}
}
