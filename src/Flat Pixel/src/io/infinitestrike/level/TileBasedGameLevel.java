package io.infinitestrike.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.item.Item;
import io.infinitestrike.js.Script;
import io.infinitestrike.state.LevelState;

public abstract class TileBasedGameLevel {

	private TiledMap map = null;

	private int mapTileSizeX = 16;
	private int mapTileSizeY = 16;

	private LevelState parent = null;
	private boolean visible = true;

	private int levelOffsetX = 0;
	private int levelOffsetY = 0;

	private boolean isInfinite = false;

	private int width = 0;
	private int height = 0;
	//private int scale = 1;

	private ArrayList<Class<?>> classObject = new ArrayList<Class<?>>();
	private ArrayList<TileEntity> tileData = new ArrayList<TileEntity>();
	private HashMap<String, String> mapProperties = new HashMap<String,String>();
	
	private int lastX = 0;
	private int lastY = 0;

	public TileBasedGameLevel(int w, int h) {
		this.width = w;
		this.height = h;

		// TODO implement object scrolling
		this.defineWorldObjectClasses(TileEntity.class);
		this.defineWorldObjectClasses(Item.class);
	}

	public final void defineWorldObjectClasses(Class<?> type) {
		this.classObject.add(type);
	}

	public final boolean isAtXLimit() {
		if (this.getLevelOffsetX() == 0 || this.getLevelOffsetX() + this.getMapSize().getWidth() == this.getParent()
				.getContainer().getWidth()) {
			return true;
		}
		return false;
	}

	// TODO implement vertical scrolling.
	public final boolean isAtYLimit() {
		if (this.getLevelOffsetY() == 0 || this.getLevelOffsetY() + this.getMapSize().getHeight() == this.getParent()
				.getContainer().getHeight()) {
			return true;
		}
		return false;
	}

	public final void scroll(int valuex, int valuey) {
		this.lastX = this.getLevelOffsetX();
		this.lastY = this.getLevelOffsetY();

		if (canMoveX()) {
			setLevelOffsetX(getLevelOffsetX() + valuex);
		}

		if (canMoveY()) {
			setLevelOffsetY(getLevelOffsetY() + valuey);
		}

		this.reAllign();
	}

	public final boolean canMoveX() {

		if (this.isInfinite == true) {
			return true;
		}

		if (this.getLevelOffsetX() < 0
				|| this.getLevelOffsetX() + this.getMapSize().getWidth() > this.getParent().getContainer().getWidth()) {
			return true;
		}
		return false;
	}

	public final boolean canMoveY() {

		if (this.isInfinite == true) {
			return true;
		}

		if (this.getLevelOffsetY() <= 0 || this.getLevelOffsetY() + this.getMapSize().getHeight() > this.getParent()
				.getContainer().getHeight()) {
			return true;
		}
		return false;
	}

	public final void reAllign() {
		/*
		 * System.out.println(String.format("Get Level Offset X: %s",
		 * this.getLevelOffsetX()));
		 * System.out.println(String.format("Get Map Size X: %s",
		 * this.getMapSize().getWidth()));
		 * System.out.println(String.format("Parent Width: %s",
		 * this.getParent().getContainer().getWidth()));
		 * 
		 * System.out.println(String.format("Get Level Offset Y: %s",
		 * this.getLevelOffsetY()));
		 * System.out.println(String.format("Get Map Size Y: %s",
		 * this.getMapSize().getHeight()));
		 * System.out.println(String.format("Parent Height: %s",
		 * this.getParent().getContainer().getHeight()));
		 * 
		 * System.exit(-255);
		 */

		if (this.isInfinite == false) {

			if (this.getLevelOffsetX() > 0) {
				this.levelOffsetX -= this.levelOffsetX;
			}

			if (this.getLevelOffsetX() + this.getMapSize().getWidth() < this.getParent().getContainer().getWidth()) {
				this.setLevelOffsetX(this.getParent().getContainer().getWidth() - this.getMapSize().getWidth());
			}

			if (this.getLevelOffsetY() > 0) {
				this.levelOffsetY -= this.levelOffsetY;
			}

			if (this.getLevelOffsetY() + this.getMapSize().getHeight() < this.getParent().getContainer().getHeight()) {
				this.setLevelOffsetY(this.getParent().getContainer().getHeight() - this.getMapSize().getHeight());
			}

			if (this.getLevelOffsetY() > 0) {
				this.setLevelOffsetY(0);
			}

			if (this.getLevelOffsetY() + this.getMapSize().getHeight() < this.getParent().getContainer().getHeight()) {
				this.setLevelOffsetY(levelOffsetY);
			}

		}

		this.reAllignEntites();
	}

	private final void reAllignEntites() {
		// do hoiz
		if (this.getLevelOffsetX() != this.lastX) {
			for (int i = 0; i < getParent().getManager().getEntities().size(); i++) {
				Entity e = getParent().getManager().getEntities().get(i);
				if (complexInstanceOf(e) || Core.hasInterface(e, classObject)) {
					e.getLocation().x += (this.getLevelOffsetX() - this.lastX);
				}
			}
		}

		// do vert
		if (this.getLevelOffsetY() != this.lastY) {
			for (int i = 0; i < getParent().getManager().getEntities().size(); i++) {
				Entity e = getParent().getManager().getEntities().get(i);
				if (complexInstanceOf(e) || Core.hasInterface(e, classObject)) {
					e.getLocation().y += (this.getLevelOffsetY() - this.lastY);
				}
			}
		}
	}

	private final boolean complexInstanceOf(Object e) {
		for (Class<?> c : this.classObject) {
			if (c == e.getClass() || e.getClass().isAssignableFrom(c)) {
				return true;
			}
		}
		return false;
	}

	public final boolean setMap(String path, String res) {
		try {
			map = new TiledMap(path, res);

			this.mapTileSizeX = Integer.parseInt(map.getMapProperty("tilewidth", "16"));
			this.mapTileSizeY = Integer.parseInt(map.getMapProperty("tileheight", "16"));
			this.mapProperties = this.getParseFlags(map.getMapProperty("flags", ""));
			this.applyMapFlags();
			this.getParent().onLevelLoad(this);
			this.refresh();
			return true;
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			LogBot.logData(LogBot.Status.ERROR, "Error Loading Map: " + path);
			LogBot.logDataVerbose(e, Status.ERROR,"Error Loading Map: " + path + " " + e.getLocalizedMessage());
			return false;
		}
	}

	public final boolean setMap(TiledMap map) {
		try {

			this.map = map;

			this.mapTileSizeX = Integer.parseInt(map.getMapProperty("tilewidth", "16"));
			this.mapTileSizeY = Integer.parseInt(map.getMapProperty("tileheight", "16"));
			this.mapProperties = this.getParseFlags(map.getMapProperty("flags", ""));
			this.applyMapFlags();
			this.getParent().onLevelLoad(this);
			this.refresh();
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.ERROR, "Error Loading Map: " + "[TILED MAP] " + map);
		}
		return true;
	}

	private final void applyMapFlags(){
		// for the default scroling
		Set<String> keys = this.getMapFlags().keySet();
		for(String s : keys){
			switch(s.toLowerCase()){
				case "scrollx":
					this.scroll(Integer.parseInt(this.getMapFlags().get(s)), 0);
					break;
				case "scrolly":
					this.scroll(0, Integer.parseInt(this.getMapFlags().get(s)));
					break;
				case "infinite":
					this.isInfinite = Boolean.parseBoolean(this.getMapFlags().get(s));
					break;
				default:
					LogBot.logData(Status.WARNING, "Unknown key entry: " + s + ". Must Be User Defined. Ignoring.");
					break;
			}
		}
	}
	
	public HashMap<String, String> getMapFlags(){
		return this.mapProperties;
	}
	
	public void refresh() {
		this.purgeData();
		this.applyColisionGeometry();
		this.pushDataToEntityManager();
		// reset the list so no refrences are left of the objects
		// so that the garbage collector can pick them up.
		this.tileData = new ArrayList<TileEntity>();
	}

	public void applyColisionGeometry() {
		for (int gi = 0; gi < map.getObjectGroupCount(); gi++) {
			for (int oi = 0; oi < map.getObjectCount(gi); oi++) {

				int x = map.getObjectX(gi, oi);
				int y = map.getObjectY(gi, oi);
				int w = map.getObjectWidth(gi, oi);
				int h = map.getObjectHeight(gi, oi);

				TileEntity e = new TileEntity(x, y, w, h);

				String $objectFlags = map.getObjectProperty(gi, oi, "flags", "$noFlags");

				if (!$objectFlags.equals("$noFlags")) {
					HashMap objectFlags = this.getParseFlags($objectFlags);
					e.addFlags(objectFlags);
				} else {
					LogBot.logData(LogBot.Status.INFO, "Object has no flags, or flags have a syntax error.");
				}

				if (e.hasFlag("solid") && e.getValue("solid").equals("true")) {
					e.setSolid(true);
				}

				this.tileData.add(e);
			}
		}
	}

	public void pushDataToEntityManager() {
		for (TileEntity e : tileData) {
			this.getParent().getManager().addEntity(e);
		}
	}

	public void purgeData() {
		for (TileEntity e : tileData) {
			this.getParent().getManager().removeEntity(e);
		}
		this.tileData = new ArrayList<TileEntity>();
	}

	public ArrayList<TileEntity> getColisionGeometry() {
		ArrayList<TileEntity> rect = new ArrayList<TileEntity>();

		return rect;
	}

	public Dimension getMapSize() {
		if (map != null) {
			Dimension d = new Dimension(map.getWidth() * this.mapTileSizeX , map.getHeight() * this.mapTileSizeY);
			return d;
		}
		return new Dimension(width, height);
	}

	public String getTileProperty(int x, int y, int layer, String property) {
		int tileID = map.getTileId(x, y, layer);
		String prop = map.getTileProperty(tileID, property, "false");
		return prop;
	}

	public boolean getSolid(int x, int y, int layer) {
		boolean blocked = "true".equals(getTileProperty(x, y, layer, "solid"));
		return blocked;
	}

	public String getTypeId(int x, int y, int layer) {
		return getTileProperty(x, y, layer, "flag");
	}

	//public void setScale(int scale){
	//	this.scale = scale;
	//}
	
	//public int getScale(){
	//	return this.scale;
	//}
	
	public void drawMap() {
		if (this.visible == true) {
			//for (int i = 0; i < map.getLayerCount(); i++) {
		    //map.render(levelOffsetX, levelOffsetY, i);
			//}
			//map.render(levelOffsetX, levelOffsetY, (int) scale, (int) scale, map.getWidth() * this.mapTileSizeX, map.getHeight() * this.mapTileSizeY);
			map.render(levelOffsetX, levelOffsetY);
			
		}
	}
	
	public void drawMap(float scalex, float scaley) {
		if (this.visible == true) {
			//for (int i = 0; i < map.getLayerCount(); i++) {
		    //map.render(levelOffsetX, levelOffsetY, i);
			//}
			//map.render(levelOffsetX, levelOffsetY, (int) scale, (int) scale, map.getWidth() * this.mapTileSizeX, map.getHeight() * this.mapTileSizeY);
			Graphics g = this.getParent().getContainer().getGraphics();
			g.scale(scalex, scaley);
			map.render(levelOffsetX, levelOffsetY);
			g.scale(1, 1);
		}
	}

	public final LevelState getParent() {
		return parent;
	}

	public final void setParent(LevelState parent) {
		this.parent = parent;
	}

	public final int getLevelOffsetX() {
		return levelOffsetX;
	}

	public final void setLevelOffsetX(int levelOffsetX) {
		this.levelOffsetX = levelOffsetX;
	}

	public final int getLevelOffsetY() {
		return levelOffsetY;
	}

	public final void setLevelOffsetY(int levelOffsetY) {
		this.levelOffsetY = levelOffsetY;
	}

	public abstract void tick(StateBasedGame game, GameContainer container, int time);

	public abstract void render(StateBasedGame game, GameContainer container, Graphics g);

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public HashMap<String, String> getParseFlags(String flags) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (!flags.equals("")) {
			String flagsData = flags.trim().toLowerCase();
			// Standard input is flag=name;flag=name;
			String[] dataSinglets = flagsData.split(";");
			for (String s : dataSinglets) {
				String[] dataSinglet = s.split("=");
				map.put(dataSinglet[0], dataSinglet[1]);
			}
		}
		return map;
	}
	

	public final int getMapTileSizeX() {
		return mapTileSizeX;
	}

	public final void setMapTileSizeX(int mapTileSizeX) {
		this.mapTileSizeX = mapTileSizeX;
	}

	public final int getMapTileSizeY() {
		return mapTileSizeY;
	}

	public final void setMapTileSizeY(int mapTileSizeY) {
		this.mapTileSizeY = mapTileSizeY;
	}

	public static DefaultGameLevel getDefaultLevel(int w, int h) {
		return new DefaultGameLevel(w, h);
	}

	public boolean isInfinite() {
		return isInfinite;
	}

	public void setInfinite(boolean isInfinite) {
		this.isInfinite = isInfinite;
	}
}