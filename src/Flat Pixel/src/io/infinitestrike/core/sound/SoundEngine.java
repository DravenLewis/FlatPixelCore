package io.infinitestrike.core.sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.core.util.ResourceLoader;

public class SoundEngine {
	
	private static ArrayList<Sound> soundList = new ArrayList<Sound>();
	
	public static final int SOUND_DOES_NOT_EXIST = -255;
	
	//public static final Sound SYSTEM_BEEP = SoundEngine.loadTracelessSound("SYSTEM_BEEP.wav");
	
	public static final void stopAllSounds(){
		for(Sound s : soundList){
			s.stop();
		}
	}
	
	public static final void removeSound(Sound s){
		soundList.remove(s);
	}
	
	public static final void removeSound(int i){
		soundList.remove(i);
	}
	
	public static final Sound getSound(int index){
		if(index > soundList.size() || index < 0){return null;}
		return soundList.get(index);
	}
	
	public static final int getSoundCount(){
		return soundList.size();
	}
	
	public static final Sound getSoundFromWAVFile(String location){
		Audio a = null;
		try {
			a = AudioLoader.getAudio("WAV",ResourceLoader.loadResourceFromFile(new File(location)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		Sound s = new Sound();
		s.MD5_HASH = Core.MD5(location);
		s.setAudio(a);
		soundList.add(s);
		return s;
	}
	
	// Load a sound without addin it into the sound cache.
	public static final Sound loadTracelessSound(String location){
		Audio a = null;
		try {
			a = AudioLoader.getAudio("WAV", new SoundEngine().getClass().getResourceAsStream(location));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		Sound s = new Sound();
		s.MD5_HASH = Core.MD5(location);
		s.setAudio(a);
		return s;
	}
	
	public static final Sound getSoundFromOGGFile(String location){
		Audio a = null;
		try {
			a = AudioLoader.getAudio("OGG", ResourceLoader.loadResourceFromFile(new File(location)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		Sound s = new Sound();
		s.setAudio(a);
		s.MD5_HASH = Core.MD5(location);
		soundList.add(s);
		return s;
	}
	
	public static final Sound getSoundFromOGGStream(InputStream st){
		Audio a = null;
		try {
			a = AudioLoader.getAudio("OGG", st);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		Sound s = new Sound();
		s.setAudio(a);
		s.MD5_HASH = Core.MD5(st.toString());
		soundList.add(s);
		return s;
	}
	
	public static final Sound getSoundFromWAVStream(InputStream st){
		Audio a = null;
		try {
			a = AudioLoader.getAudio("WAV", st);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		Sound s = new Sound();
		s.setAudio(a);
		s.MD5_HASH = Core.MD5(st.toString());
		soundList.add(s);
		return s;
	}
	
	
	public static final Sound getStreamableSoundFromOGGFile(String location){
		
		// This sound is streabable so its not a final sound and will not be 
		// registered to the internal sound buffer.
		
		Audio a = null;
		try{
			a = AudioLoader.getStreamingAudio("OGG", new URL(location));
		}catch(IOException e){
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		Sound s = new Sound();
		s.setAudio(a);
		s.MD5_HASH = Core.MD5(location);
		return s;
	}
	
	public static final Sound getStreamableSoundFromWAVFile(String location){

		// This sound is streabable so its not a final sound and will not be 
		// registered to the internal sound buffer.
		
		Audio a = null;
		try{
			a = AudioLoader.getStreamingAudio("WAV", new URL(location));
		}catch(IOException e){
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
		Sound s = new Sound();
		s.setAudio(a);
		s.MD5_HASH = Core.MD5(location);
		return s;
	}
	
	public static int soundExists(Sound s){
		for(int i = 0; i < soundList.size(); i++){
			Sound sn = soundList.get(i);
			if(sn.MD5_HASH.equals(s.MD5_HASH)){
				return i;
			}
		}
		return -255;
	}
}
