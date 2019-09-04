package io.infinitestrike.core.sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class MusicSource {
	private Music sound = null;

	private float volume = 1f;
	private float pitch = 1f;

	public MusicSource(String path) {
		try {
			sound = new Music(path);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, "Failed to load sound! " + e.getLocalizedMessage());
		}
	}

	public void play() {
		sound.play(pitch, volume);
	}

	public void loop() {
		sound.loop(pitch, volume);
	}

	public void stop() {
		sound.stop();
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
	
	
}
