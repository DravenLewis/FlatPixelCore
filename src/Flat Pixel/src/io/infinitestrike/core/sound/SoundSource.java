package io.infinitestrike.core.sound;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class SoundSource {
	private Sound sound = null;
	
	private float volume = 1f;
	private float pitch  = 1f;
	
	public SoundSource(String path){
		try {
			sound = new Sound(path);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE,"Failed to load sound! " + e.getLocalizedMessage());
		}
	}
	
	public final float getVolume() {
		return volume;
	}

	public final void setVolume(float volume) {
		this.volume = volume;
	}

	public final float getPitch() {
		return pitch;
	}

	public final void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void play(){
		sound.play(pitch, volume);
	}
	
	public void loop(){
		sound.loop(pitch, volume);
	}
	
	public void play3D(float x, float y, float z){
		sound.playAt(pitch, volume, x, y, z);
	}
	
	public void stop(){
		sound.stop();
	}
}
