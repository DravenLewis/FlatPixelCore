package io.infinitestrike.state;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.util.ImageLoader;
import io.infinitestrike.core.util.ItemList3D;
import io.infinitestrike.core.util.ItemList3D.Item;
import io.infinitestrike.core.util.PluginLoader;
import io.infinitestrike.level.TileBasedGameLevel;
import io.infinitestrike.plugin.Plugin;

public class PluginState extends LevelState{

	
	Image unknownImage = ImageLoader.getSlickImageFromPath("res/images/unknown.png");
	HashMap<String,Plugin> plugins = PluginLoader.loadPlugins("plugins/");
	ArrayList<Plugin> plugins_list = new ArrayList<Plugin>();
	
	ItemList3D<Image,String,String> pluginData = new ItemList3D<Image,String,String>();
	
	public PluginState(int id, int width, int height) {
		super(id, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		for(Object p : plugins.values().toArray()){
			if(p instanceof Plugin){
				Plugin d = (Plugin) p;
				plugins_list.add(d);
			}
		}

		
		for(Plugin p : this.plugins_list){
			Image pluginIcon = ImageLoader.getSlickImageFromStream(p.getFile("icon.png"));
			String name = p.getName();
			String version = p.getVersion();
			p.addConstantRunArg(this.getGame());
			pluginData.add(pluginIcon, name, version);
		}
	}

	@Override
	public void stateChange(LevelState previous) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		int index = 0;
		for(Item<Image,String,String> i : this.pluginData.getItems()){
			Image pluginIcon = i.type1;
			String name = i.type2;
			String version = i.type3;
			index += 1;
			
			if(pluginIcon != null){
				pluginIcon.draw(32, 32 + (index * 48));
			}else{
				this.unknownImage.draw(32, 32 + (index * 48));
			}
			
			arg2.setColor(Color.white);
			arg2.drawString("Plugin: " + name + " \nVersion: " + version, 96, 32 + (index * 48));
		}
	}

	@Override
	public void preTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLevelLoad(TileBasedGameLevel level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(int i, char c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyRelease(int i, char c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

}
