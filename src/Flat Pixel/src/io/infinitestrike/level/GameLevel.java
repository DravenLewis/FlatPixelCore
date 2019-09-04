package io.infinitestrike.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import io.infinitestrike.core.annote.Broken;
import io.infinitestrike.state.LevelState;

/**
 * 
 * @author 17dclewis
 * Basic Class Designed to implement tile maps, with collision and 
 * Entity management.
 * 
 * @Broken - This Class has fragmented functionality; Replaced by TileBasedGameLevel
 * @see TileBasedGameLevel
 * 
 */
@Deprecated
@Broken
public abstract class GameLevel{
	
	private TiledMap map;
	private int tileSize = 16;
	private LevelState parent = null;
	private boolean visible = true;
	private boolean[][] solid;
	
	private int levelOffsetX = 0;
	private int levelOffsetY = 0;
	
	private int width = 0;
	private int height = 0;
	
	private int solidLayerIndex = 0;
	private int entityLayerIndex = 0;
	
	public String solidLayerName = "solid";
	public String entityLayerName = "entity";
	
	public GameLevel(int w, int h){
		this.solid = new boolean[w / tileSize][h / tileSize];
		this.width = w;
		this.height = h;
	}
	
	public final boolean setMap(String path,String res){
		try {
			map = new TiledMap(path,res);
			refreshMap();
			return true;
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void refreshMap(){
		solidLayerIndex = map.getLayerIndex(solidLayerName);
		entityLayerIndex = map.getLayerIndex(entityLayerName);
		
		if(solidLayerIndex == -1){solidLayerIndex = 0;}
		if(entityLayerIndex == -1){entityLayerIndex = 0;}
		
		applySolidMask();
		this.getParent().cleanTileData();
		/*
		this.getParent().applyColisionDataFromLevel();
		this.getParent().applyFlaggedTilesFromLeve();
		*/
	}
	
	public final int getSolidLayerIndex() {
		return solidLayerIndex;
	}

	public final void setSolidLayerIndex(int solidLayerIndex) {
		this.solidLayerIndex = solidLayerIndex;
	}

	public final int getEntityLayerIndex() {
		return entityLayerIndex;
	}

	public final void setEntityLayerIndex(int entityLayerIndex) {
		this.entityLayerIndex = entityLayerIndex;
	}

	public void applySolidMask(){
		for(int x = 0; x < map.getWidth()/*width / tileSize*/; x++){
			for(int y = 0; y < map.getHeight()/*height / tileSize*/; y++){
				boolean bool = this.getSolid(x, y, solidLayerIndex);
				this.solid[x][y] = bool;
			}
		}
	}
	
	public boolean[][] getColisionData(){
		return this.solid;
	}
	
	public ArrayList<TileEntity> getColisionGeometry(){
		ArrayList<TileEntity> rect = new ArrayList<TileEntity>();
		for(int x = 0; x < solid.length; x++){
			for(int y = 0; y < solid[0].length; y++){
				if(solid[x][y] == true){
					TileEntity e = new TileEntity(levelOffsetX + (tileSize * x),levelOffsetY + (tileSize * y),tileSize,tileSize);
					e.tile_x = x;
					e.tile_y = y;
					e.setSolid(true);
					rect.add(e);
				}
			}
		}
		return rect;
	}
		
	public ArrayList<TileEntity> getFlaggedTiles(){
		ArrayList<TileEntity> rect = new ArrayList<TileEntity>();
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				// Long way
				int id = map.getTileId(x, y, solidLayerIndex);
				String prop = map.getTileProperty(id, "flag", "false");
				if(prop != "false" && prop != null){
					TileEntity e = new TileEntity(levelOffsetX + (tileSize * x),levelOffsetY + (tileSize * y),tileSize,tileSize);
					e.tile_x = x;
					e.tile_y = y;
					e.addFlag(prop,"true");
					rect.add(e);
				}
			}
		}
		return rect;
	}
	
	public Dimension getMapSize(){
		Dimension d = new Dimension(map.getWidth() / this.tileSize,map.getHeight() / this.tileSize);
		return d;
	}
	
	public String getTileProperty(int x, int y, int layer, String property){
		int tileID = map.getTileId(x, y, layer);
		String prop = map.getTileProperty(tileID, property, "false");
		return prop;
	}
	
	public boolean getSolid(int x, int y, int layer){
		boolean blocked = "true".equals(getTileProperty(x,y,layer,"solid"));
		return blocked;
	}
	
	public String getTypeId(int x, int y, int layer){
		return getTileProperty(x,y,layer,"flag");
	}
	
	public void drawMap(){
		if(this.visible == true){
			for(int i = 0; i < map.getLayerCount(); i++){
				if(i != this.solidLayerIndex){
					map.render(levelOffsetX, levelOffsetY, i);
				}
			}
		}
	}
	
	public final int getTileSize() {
		return tileSize;
	}

	public final void setTileSize(int tileSize) {
		this.tileSize = tileSize;
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
	
	public HashMap<String,String> getParseFlags(String flags){
		HashMap<String,String> map = new HashMap<String,String>();
		// Standard input is flag=name;flag=name;
		String[] dataSinglets = flags.split(";");
		for(String s : dataSinglets){
			String[] dataSinglet = s.split("=");
			map.put(dataSinglet[0], dataSinglet[1]);
		}
		return map;
	}
}