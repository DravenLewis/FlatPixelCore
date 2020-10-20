package io.infinitestrike.editor;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.util.FPMath.Vector2i;

public class Layer {
	
	public static final int TILE_ID_EMPTY = -1;
	public static final int TILE_ID_INVALID = -2;
	
	private int[] tileIDMap;
	private final Map parentMap;
	private String layerName = "New Layer";
	
	private Stack<int[]> changes = new Stack<int[]>(); // history
	
	public boolean doRender = true;// for hiding layers in the editor

	public Layer(Map parentMap) {
		this.parentMap = parentMap;
		this.tileIDMap = new int[this.parentMap.getHorizontalCellCount() * this.parentMap.getVerticalCellCount()];
		for(int i = 0; i < this.tileIDMap.length; i++) this.tileIDMap[i] = TILE_ID_EMPTY; // INIT TO ALL -1 (No Tile)
		parentMap.addLayer(this);
	}
	
	public Layer(Map parentMap, boolean addImmediate) {
		if(addImmediate) {
			this.parentMap = parentMap;
			this.tileIDMap = new int[this.parentMap.getHorizontalCellCount() * this.parentMap.getVerticalCellCount()];
			for(int i = 0; i < this.tileIDMap.length; i++) this.tileIDMap[i] = TILE_ID_EMPTY; // INIT TO ALL -1 (No Tile)
			parentMap.addLayer(this);
		}else { 
			this.parentMap = parentMap;
			this.tileIDMap = new int[this.parentMap.getHorizontalCellCount() * this.parentMap.getVerticalCellCount()];
			for(int i = 0; i < this.tileIDMap.length; i++) this.tileIDMap[i] = TILE_ID_EMPTY; // INIT TO ALL -1 (No Tile)
		}
	}
	
	public void setTile(int index, int id) {
		changes.push(tileIDMap);
		if(index >= 0 && index <= this.tileIDMap.length - 1) {
			this.tileIDMap[index] = id;
		}
	}
	
	public void setTile(int x, int y, int id) {
		this.setTile(x + y * this.parentMap.getHorizontalCellCount(), id);
	}
	
	public void setTile(int x, int y, int id, int w, int h) {
		if(this.parentMap.getTileMap() == null) return;
		
		int hCells = this.parentMap.getTileMap().getTotalHorizontalCells();
		int xIndex = Core.MathFunctions.get2DXFromIndex(id, hCells);
		int yIndex = Core.MathFunctions.get2DYFromIndex(id, hCells);
		
		
		for(int yo = y; yo < y + h; yo ++) {
			for(int xo = x; xo < x + w; xo++) {
				
				int absOffsetX = xo - x; // x amout away from the "local" 0,0. Where 0,0 == x,y
				int absOffsetY = yo - y; // y amout away from the "local" 0,0. Where 0,0 == x,y
				int newTileMapPositionX = absOffsetX + xIndex; // position in the tileID map with offset in-mind.
				int newTileMapPositionY = absOffsetY + yIndex; // position in the tileID map with offset in-mind.
				
				int newId = newTileMapPositionX + newTileMapPositionY * (hCells * 2);// issue lies here
				
				
				// dont touch
				this.setTile(xo,yo,newId);
			}
		}
		
		this.setTile(x, y, id);
		this.setTile(x + 1,y, id + 1);
		
	}
	
	public void undo() {
		this.tileIDMap = this.changes.pop();
	}
	
	public int getTile(int id) {
		if(id >= 0 && id <= this.tileIDMap.length - 1) {
			return this.tileIDMap[id];
		}
		return TILE_ID_INVALID;
	}
	
	public int getTile(int x, int y) {
		return this.getTile(x + y * this.parentMap.getHorizontalCellCount());
	}
	
	public ArrayList<Vector2i> getNumberOfValidNeighbors(int x, int y) {

		ArrayList<Vector2i> list = new ArrayList<Vector2i>();
		
		if(getTile(x,y) != TILE_ID_EMPTY) return null; else list.add(new Vector2i(x,y));
		
		if(getTile(x-1,y) != TILE_ID_INVALID && getTile(x-1,y) == TILE_ID_EMPTY) list.add(new Vector2i(x-1,y));
		if(getTile(x+1,y) != TILE_ID_INVALID && getTile(x+1,y) == TILE_ID_EMPTY) list.add(new Vector2i(x+1,y));
		if(getTile(x,y-1) != TILE_ID_INVALID && getTile(x,y-1) == TILE_ID_EMPTY) list.add(new Vector2i(x,y-1));
		if(getTile(x,y+1) != TILE_ID_INVALID && getTile(x,y+1) == TILE_ID_EMPTY) list.add(new Vector2i(x,y+1));
		
		return list;
	}
	
	public String getName() {
		return this.layerName;
	}
	
	public void setName(String name) {
		this.layerName = name;
	}
	
	public int getMapLinearSize() {
		return this.tileIDMap.length;
	}
	
	public void draw(Graphics g) {
		if(!this.doRender) return;
		
		// render the map
		for(int x = 0; x < this.parentMap.getHorizontalCellCount(); x++) {
			for(int y = 0; y < this.parentMap.getVerticalCellCount(); y++) {
				int XrealRenderLoc = x * this.parentMap.getTileSize();
				int YrealRenderLoc = y * this.parentMap.getTileSize();
				int id = this.getTile(x,y); // pull the tile ID from the local list
				if(id >= 0) { // Means its not empty, and valid
					Tile renderTile = this.parentMap.tileMap.getTile(id);
					if(renderTile != null) {
						g.drawImage(renderTile.getTileImage(this.parentMap.getTileSetImage()), XrealRenderLoc, YrealRenderLoc, null);
					}
				}
			}
		}
	}

	public boolean isDoRender() {
		return doRender;
	}

	public void setDoRender(boolean doRender) {
		this.doRender = doRender;
	}
	
	public int[] getTileIDMap() {
		return this.tileIDMap;
	}
}
