package io.infinitestrike.editor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;

import com.sun.scenario.Settings;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.core.util.FPMath;
import io.infinitestrike.core.util.Loader;
import io.infinitestrike.core.util.Loader.LoaderEntry;
import io.infinitestrike.core.util.Loader.LoaderResult;


public class Map{

	private final int cellsH;
	private final int cellsV;
	private final int tileSize;

	private BufferedImage tileSetImage;
	private final BufferedImage drawBuffer;
	private final ArrayList<Layer> layers = new ArrayList<Layer>();
	private final Vector2f offset = new Vector2f(0, 0);
	private LayerManager layerManager = null;

	protected TileMap tileMap = null;

	private boolean isMapReady = false;
	private boolean isInGrid = false;

	private int currentLayerIndex = 0;

	public Map(BufferedImage img, int cellsH, int cellsV, int tileSize) {
		this.cellsH = cellsH;
		this.cellsV = cellsH;
		this.tileSize = tileSize;

		this.tileSetImage = img;
		this.drawBuffer = new BufferedImage(this.getWidth() + 1, this.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);

		// Load our tile set.
		this.tileMap = new TileMap(this);
		this.layerManager = new LayerManager(this);
	}

	public BufferedImage getTileSetImage() {
		return this.tileSetImage;
	}

	public void setCurrentLayerIndex(int i) {
		this.currentLayerIndex = i;
	}

	public int getCurrentLayerIndex() {
		return this.currentLayerIndex;
	}

	public LayerManager getLayerManager() {
		return this.layerManager;
	}

	public Layer getActiveLayer() {
		if (this.layers.size() > 0) {
			Layer l = this.getLayer(this.getCurrentLayerIndex());
			if (l == null) {
				if (getLayer(0) != null) {
					l = this.getLayer(0);
				} else {
					l = new Layer(this);
				}
			}
			return l;
		} else {
			return null;
		}
	}


	public void clear() {
		for (Layer l : this.layers) {
			for (int i = 0; i < l.getMapLinearSize(); i++) {
				l.setTile(i, Layer.TILE_ID_EMPTY);
			}
		}
	}

	public void addLayer(Layer l) {
		this.layers.add(l);
	}

	public Layer getLayer(int id) {
		return this.layers.get(id);
	}

	public void removeLayer(int id) {
		this.layers.remove(id);
	}

	public void removeAllLayers() {
		this.layers.clear();
	}

	public ArrayList<Layer> getLayers() {
		return this.layers;
	}

	public int getHorizontalCellCount() {
		return this.cellsH;
	}

	public int getVerticalCellCount() {
		return this.cellsV;
	}

	public int getWidth() {
		return this.cellsH * this.tileSize;
	}

	public TileMap getTileMap() {
		return this.tileMap;
	}

	public void setTileMap(TileMap t) {
		this.tileMap = t;
	}
	
	public int getHeight() {
		return this.cellsV * this.tileSize;
	}

	public int getTileSize() {
		return this.tileSize;
	}

	public boolean isMapReady() {
		return isMapReady;
	}

	public boolean isMouseOverMap() {
		return this.isInGrid;
	}

	public Vector2f getOffset() {
		return this.offset;
	}

	public void setOffset(int x, int y) {
		this.offset.x = x;
		this.offset.y = y;
	}

	public BufferedImage renderMap() {
		BufferedImage img = new BufferedImage(this.drawBuffer.getWidth(),this.drawBuffer.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = (Graphics2D) img.getGraphics();
		if (this.tileMap != null) {
			for (int i = 0; i < this.getLayers().size(); i++) {
				this.getLayers().get(i).draw(imgGraphics);
			}
		}
		imgGraphics.dispose();
		return img;
	}
	
	/*// Not needed, we are running the map renderer headless to 
	 	 render to image.
	 	 
	 	 
		public void OnRender(Graphics g, RenderPane c) {
		// TODO Auto-generated method stub

		g.drawImage(ImageTools.TRASN_CHECKER_D, 0, 0, null);

		Graphics2D bufferedGraphics = (Graphics2D) this.drawBuffer.getGraphics();
		bufferedGraphics.setBackground(new Color(255, 255, 255, 0));
		bufferedGraphics.clearRect(0, 0, this.getWidth() + 1, this.getHeight() + 1);
		
		BufferedImage img = renderMap();
		bufferedGraphics.drawImage(img, 0, 0, null);
		

		if (Settings.drawGridBox) {

			bufferedGraphics.setColor(new Color(Loader.getValueHex(Settings.getSettings().valueOf("$GRID_COLOR", "16777215"))));

			for (int x = 0; x < this.getHorizontalCellCount() + 1; x++) {
				int rx = x * this.tileSize;
				bufferedGraphics.drawLine(rx, 0, rx, this.getHeight());
			}

			for (int y = 0; y < this.getVerticalCellCount() + 1; y++) {
				int ry = y * this.tileSize;
				bufferedGraphics.drawLine(0, ry, this.getWidth(), ry);
			}
		}
		g.drawImage(drawBuffer, offset.x, offset.y, null);
		bufferedGraphics.dispose();
	}

	@Override
	public void OnTick(double delta, RenderPane c) {
		// TODO Auto-generated method stub

		InputManager input = c.getInputManager();

		if (input != null) {
			this.isInGrid = (input.MOUSE_X >= this.offset.x && input.MOUSE_X <= this.offset.x + getWidth())
					&& (input.MOUSE_Y >= this.offset.y && input.MOUSE_Y <= this.offset.y + getHeight());
		}

	}*/

	// ==================================================================================================
	public Vector2f getCell(int screenSpaceX, int screenSpaceY) {
		return FPMath.getCell(
				this.offset, 
				new Vector2f(screenSpaceX, screenSpaceY),
				new Vector2f(getWidth(), getHeight()), 
				this.tileSize
		);
	
	}

	public void floodFillTile(int x, int y, int tile) {
		Layer l = this.getActiveLayer();
		if (l.getTile(x, y) != Layer.TILE_ID_EMPTY)
			return;
		l.setTile(x, y, tile);

		floodFillTile(x + 1, y, tile);
		floodFillTile(x - 1, y, tile);
		floodFillTile(x, y + 1, tile);
		floodFillTile(x, y - 1, tile);
	}

	
	public String toString() {
		String map = "";
		map+="Map@"+this.hashCode()+" Layers: " + this.getLayers().size() + "Tiles: " + this.getTileMap().getTiles().length + "\n\n";
		for(Layer l : this.getLayers()) {
			map += "Layer# " + this.getLayers().indexOf(l) + " Name: " + l.getName() + "\n";
		}
		return map;
	}
	
	public static Map openMap(File location) {

		Map m = null;
		LoaderResult r = null;
		TileMap t = null;
		
		LogBot.logData(Status.INFO,"=====================================================");
		LogBot.logData(Status.INFO,"Map Import Started");
		LogBot.logData(Status.INFO,"=====================================================");
		
		try {
			r = Loader.readFile(location);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, "Cannot Load Map");
			e.printStackTrace();
		}

		try {
			String tilesetFilePath = location.getPath().replace(location.getName(), "")
					+ Loader.getValueString(r.valueOf("$TILE_SET", "tileset.png"));
			
			BufferedImage tileSet = ImageIO.read(new File(tilesetFilePath));
			LogBot.logData(Status.INFO,"[Map::OpenMap] Tileset Image: " + tilesetFilePath);
			
			//if(tilesetFilePath.equals("null")) {
			//	LogBot.logData(Status.INFO,"Cannot Read Map: Cannot load value of key $TILE_SET");
			//	return null;
			//}
			
			// create map
			int tileSize = Loader.getValueInt(r.valueOf("$TILE_SIZE", "32"));
			int cellsX = Loader.getValueInt(r.valueOf("$TILE_CELLS_X", "5"));
			int cellsY = Loader.getValueInt(r.valueOf("$TILE_CELLS_Y", "5"));
			m = new Map(tileSet, cellsX, cellsY, tileSize);
			t = new TileMap(m);
			
			
			// create tile map
			int tileAmmount = 0;
			if ((tileAmmount = Loader.getValueInt(r.valueOf("$TILE_AMMOUNT", "0"))) == m.getTileMap()
					.getTiles().length) {
				for (int i = 0; i < tileAmmount; i++) {
					String[] tileData = Loader.getValueArray(r.valueOf("$TILE_ID_" + i, "null"));
					if (!tileData[0].equals("null")) {
						int id = Integer.parseInt(tileData[0]);
						int xi = Integer.parseInt(tileData[1]);
						int yi = Integer.parseInt(tileData[2]);

						m.getTileMap().setTile(id, xi, yi, tileSize);
					} else {
						LogBot.logData(Status.INFO,"Cannot Read Entry: $TILE_ID_" + i + ".");
						return null;
					}
				}
				
				m.setTileMap(t);
			} else {
				LogBot.logData(Status.INFO,"Cannot Create Tile Map, Size Was: " + tileAmmount + " Expected: "
						+ m.getTileMap().getTiles().length);
				return null;
			}
			// create layer map
			int layerCount = Loader.getValueInt(r.valueOf("$LAYER_COUNT", "-1"));
			LogBot.logData(Status.INFO,"[Map::OpenMap] Layer Count: " + layerCount);
			if (layerCount != -1) {
				for (int i = 0; i < layerCount; i++) {
					String name = Loader.getValueString(r.valueOf("$LAYER_ID_" + (i) + "_NAME", "New Layer"));
					LogBot.logData(Status.INFO,"[Map::OpenMap] Layer "+i+" Name: " + name);
					Layer l = new Layer(m,false);
					l.setName(name);
					String[] layerValues = Loader.getValueArray(r.valueOf("$LAYER_ID_" + (i), "null"));
					if (!layerValues[0].equals("null")) {
						for (int j = 0; j < layerValues.length; j++) {
							l.setTile(j, Integer.parseInt(layerValues[j]));
						}
					} else {
						LogBot.logData(Status.INFO,"Cannot Read Layer Entry: $LAYER_ID_" + i);
						return null;
					}
					m.addLayer(l);
				}
			}

			LogBot.logData(Status.INFO,"=====================================================");
			LogBot.logData(Status.INFO,"Map Import Finished");
			LogBot.logData(Status.INFO,"Tiles: " + m.getTileMap().getTiles().length);
			LogBot.logData(Status.INFO,"Layers: " + m.getLayers().size());
			LogBot.logData(Status.INFO,"Tile Set Image: " + tilesetFilePath);
			LogBot.logData(Status.INFO,"=====================================================");
			
			//Settings.setCurrentTilesetFile(new File(tilesetFilePath)); // only set if we get this far
			return m;
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.ERROR, "Error loading Map.");
			e.printStackTrace();
			return null;
		}
	}

	// ====================================================================================================

}
