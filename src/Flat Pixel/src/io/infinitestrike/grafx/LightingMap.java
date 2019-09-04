package io.infinitestrike.grafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import io.infinitestrike.core.util.ImageLoader;
import io.infinitestrike.level.MapElement;
import io.infinitestrike.level.TileEntity;
import io.infinitestrike.state.LevelState;

public class LightingMap {

	private int sizeX = 0;
	private int sizeY = 0;
	private Image lightMap = null;
	private Image lightImage = null;
	private Image offScreenImage = null;
	private Graphics offscreenGraphics;
	private Graphics lightsGraphics;
	private Image alphaMap = null;

	private Random rand = new Random();

	// our lights
	public List<Light> lights = new ArrayList<Light>();
	private long elapsed;
	private Color sharedColor = new Color(1f, 1f, 1f, 1f);

	private LevelState state = null;
	
	public LightingMap() {
		try {
			this.lightImage = ImageLoader.getSlickImageFromStream(LightingMap.class.getResourceAsStream("shdow.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	// make sure our light source is also a item in the manager, and a item that can be scaled.
	public static class Light extends TileEntity implements MapElement{
		Color tint;
		float alpha;
		private float scale;
		private float scaleOrig;

		public Light(float x, float y, float scale, Color tint) {
			super((int)x,(int)y,32,32);
			this.scale = scaleOrig = scale;
			this.alpha = 1f;
			this.tint = tint;
		}

		public Light(float x, float y, float scale) {
			this(x, y, scale, Color.white);
		}

		public void update(float time) {
			// effect: scale the light slowly using a sin func
			//scale = scaleOrig + 1f + .5f * (float) Math.sin(time);
		}
	}

	public void addLight(Light l) {
		if(this.state != null) {
			state.addNewEntity(l);
		}
		
		this.lights.add(l);
	}
	
	public void removeAllLights() {
		for(Light l : this.lights) {
			if(this.state != null) {
				state.removeEntity(l);
			}
		}
		this.lights.clear();
	}
	
	public void create(GameContainer container, LevelState s) {
		
		this.state = s;
		
		try {
			// our offscreen image
			this.offScreenImage = new Image(container.getWidth(), container.getHeight());
			offscreenGraphics = this.offScreenImage.getGraphics();

			// our lights image
			this.lightMap = new Image(container.getWidth(), container.getHeight());
			lightsGraphics = lightMap.getGraphics();

			this.alphaMap = this.lightImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer c, Graphics g) {
		Graphics.setCurrent(lightsGraphics);

		// 1. clear the light image background to black
		lightsGraphics.setBackground(Color.black);
		lightsGraphics.clear();

		// 2. set up GL blending to avoid any transparency loss
		GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE,
				GL11.GL_ONE_MINUS_SRC_ALPHA);

		// 3. draw our lights... a feathered white circle on a transparent background
		alphaMap.startUse();
		for (int i = 0; i < lights.size(); i++) {
			Light light = lights.get(i);
			light.tint.bind();
			alphaMap.drawEmbedded(light.getLocation().x - (light.scale * alphaMap.getWidth() / 2f),
					light.getLocation().y - (light.scale * alphaMap.getHeight() / 2f), light.scale * alphaMap.getWidth(),
					light.scale * alphaMap.getHeight());
		}
		alphaMap.endUse();

		// 4. reset the draw mode
		lightsGraphics.setDrawMode(Graphics.MODE_NORMAL);

		// 5. flush the light image graphics!!
		lightsGraphics.flush();

		// ---- Now we can start rendering to the screen
		Graphics.setCurrent(g);

		// 2. Set up blend mode for masking
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_SRC_COLOR);

		// 3. Draw our light map
		this.lightMap.draw();

		// ---- Reset the mode to normal before continuing!
		g.setDrawMode(Graphics.MODE_NORMAL);
	}

	public void clear() {
		for (int i = 0; i < lights.size(); i++) {
			lights.remove(i);
		}
	}
	
	public void update(GameContainer container, int delta) {

		elapsed += delta;

		// update all lights to have them smoothly scale
		for (int i = 0; i < lights.size(); i++) {
			//lights.get(i).update(elapsed / 1000f);
		}

		// the last-added light will be the one under the mouse
		//if (lights.size() > 0) {
		//	Light l = lights.get(lights.size() - 1);
		//	l.x = container.getInput().getMouseX();
		//	l.y = container.getInput().getMouseY();
		//}

	}
}
