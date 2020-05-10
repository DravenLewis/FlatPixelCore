package io.infinitestrike.state;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.core.TemplateGame;
import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.EntityManager;
import io.infinitestrike.entity.EntityManager.PauseHandler;
import io.infinitestrike.entity.InputEvent;
import io.infinitestrike.entity.NeoEntityManager;
import io.infinitestrike.level.TileBasedGameLevel;
import io.infinitestrike.level.TileEntity;


public abstract class LevelState extends BasicGameState {

	public int id;
	public int gameTimer = 0;
	public int frames = 0;
	public int maxFrameCount = 120; // set max delay to 2 frames;

	public float TOP = 0;
	public float LEFT = 0;
	public float BOTTOM = 0;
	public float RIGHT = 0;

	public final int BG_MODE_CENTER = 0;
	public final int BG_MODE_SCALE = 1;

	private TileBasedGameLevel level = null;
	private GameContainer container = null;
	private StateBasedGame game = null;
	private EntityManager manager;
	private Random rand = new Random();
	private float bgScrollOffsetX = 0;
	private float bgScrollOffsetY = 0;

	private int width;
	private int height;
	

	private Image background = null;

	public LevelState(int id, int width, int height) {
		this.id = id;
		this.width = width;
		this.height = height;
	}

	public TileBasedGameLevel getCurrentLevel() {
		return level;
	}

	public StateBasedGame getGame() {
		return this.game;
	}

	public GameContainer getContainer() {
		return this.container;
	}

	public void addNewEntity(Entity e) {
		manager.addEntity(e);
	}

	public void removeEntity(Entity e) {
		manager.removeEntity(e);
	}

	public void setLevel(TileBasedGameLevel level) {
		this.level = level;
		this.level.setParent(this);
	}

	public final void setBackground(Image img) {
		this.background = img;
	}

	public final Image getBackground() {
		return this.background;
	}

	public final void drawBackground(int mode) {

		if (this.background != null) {
			if (mode == BG_MODE_CENTER) {
				float x = (float) ((float) (this.container.getWidth() / 2) - (background.getWidth() * 0.75 / 2));
				float y = (float) ((float) (this.container.getHeight() / 2) - (background.getHeight() * 0.75 / 2));
				background.draw(x + this.bgScrollOffsetX, y + this.bgScrollOffsetY, 0.75f);
			} else if (mode == BG_MODE_SCALE) {
				background.draw(0 + this.bgScrollOffsetX, 0 + this.bgScrollOffsetY, this.width + (this.width * 0.25f),
						this.height + (this.height * 0.25f));
			} else if (mode == (BG_MODE_CENTER | BG_MODE_SCALE)) {
				float x = (float) ((float) (this.container.getWidth() / 2)
						- (background.getWidth() * (this.width * 0.25f) / 2));
				float y = (float) ((float) (this.container.getHeight() / 2)
						- (background.getHeight() * (this.height * 0.25f) / 2));
				background.draw(x + this.bgScrollOffsetX, y + this.bgScrollOffsetY, this.width + (this.width * 0.25f),
						this.height + (this.height * 0.25f));
			} else {
				background.draw();
			}
		}
	}

	public final void setBackgroundScrollOffset(float x, float y) {
		this.bgScrollOffsetX = x;
		this.bgScrollOffsetY = y;
	}

	public final Vector2f getBackgroundScrollOffset() {
		return new Vector2f(this.bgScrollOffsetX, this.bgScrollOffsetY);
	}
	/*
	 * public void applyColisionDataFromLevel() { for (TileEntity r :
	 * this.getCurrentLevel().getColisionGeometry()) { this.addNewEntity(r); } }
	 * 
	 * public void applyFlaggedTilesFromLeve() { for (TileEntity r :
	 * this.getCurrentLevel().getFlaggedTiles()) { this.addNewEntity(r); } }
	 */

	public void cleanTileData() {
		for (Entity e : this.manager.getEntities()) {
			if (e instanceof TileEntity) {
				this.removeEntity(e);
			}
		}
	}


	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub
		this.container = arg0;
		this.game = arg1;

		this.setLevel(TileBasedGameLevel.getDefaultLevel(this.width, this.height));

		manager = new NeoEntityManager(container,game);//new EntityManager(container, game);
		manager.setState(this);

		this.initialize(arg0, arg1);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		// TODO Auto-generated method stub
		arg2.setClip(0, 0, arg0.getWidth(), arg0.getHeight());
		this.preDraw(arg0, arg1, arg2);
		if (level != null) {
			level.render(arg1, arg0, arg2);
		}
		manager.delegatedObjectUpdate(container, arg1, arg2, gameTimer);
		this.draw(arg0, arg1, arg2);
		this.postDraw(arg0, arg1, arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub

		int mouseX = Mouse.getX();
		int mouseY = arg0.getHeight() - Mouse.getY();

		InputEvent.MOUSE_DELTA_X = mouseX - InputEvent.MOUSE_LAST_X;
		InputEvent.MOUSE_DELTA_Y = mouseY - InputEvent.MOUSE_LAST_Y;

		InputEvent.MOUSE_LAST_X = mouseX;
		InputEvent.MOUSE_LAST_Y = mouseY;

		if (!this.isPaused())
			this.preTick(arg0, arg1, arg2);
		if (level != null && !this.isPaused()) {
			level.tick(arg1, arg0, arg2);
		}
		if (!this.isPaused()) {
			this.tick(arg0, arg1, arg2);
		}
		if (!this.isPaused()) {
			this.postTick(arg0, arg1, arg2);
		}

		BOTTOM = this.getBounds().getHeight();
		RIGHT = this.getBounds().getWidth();

		TemplateGame game = (TemplateGame) this.game;
		game.checkGlobalUpdates();
		
		this.frames++;
		if(this.frames > this.maxFrameCount) this.frames = 0;
	}

	public final void cleanup() {
		// Call the clean up event.
		this.terminate(this.container, this.game);
	}

	public final int random() {
		return rand.nextInt(Integer.MAX_VALUE);
	}

	public final int random(int range) {
		return rand.nextInt(range);
	}

	public final float random(float range) {
		return rand.nextFloat() * range;
	}

	public final Rectangle getBounds() {
		return new Rectangle(0, 0, this.container.getWidth(), this.container.getHeight());
	}

	@Override
	public void keyPressed(int i, char c) {
		this.onKeyPress(i, c);
		this.manager.onKeyDown(i, c);
	}

	@Override
	public void keyReleased(int i, char c) {
		this.onKeyRelease(i, c);
		this.manager.onKeyUp(i, c);
	}

	public abstract void initialize(GameContainer arg0, StateBasedGame arg1);

	public abstract void stateChange(LevelState previous);

	public abstract void preDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2);

	public abstract void draw(GameContainer arg0, StateBasedGame arg1, Graphics arg2);

	public abstract void postDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2);

	public abstract void preTick(GameContainer arg0, StateBasedGame arg1, int arg2);

	public abstract void tick(GameContainer arg0, StateBasedGame arg1, int arg2);

	public abstract void postTick(GameContainer arg0, StateBasedGame arg1, int arg2);

	public abstract void onLevelLoad(TileBasedGameLevel level);

	public abstract void onKeyPress(int i, char c);

	public abstract void onKeyRelease(int i, char c);

	public abstract void terminate(GameContainer arg0, StateBasedGame arg1);

	public final void enterState(int id) {
		try {
			TemplateGame game = (TemplateGame) this.getGame();
			this.stateChange(this);
			LevelState s = (LevelState) game.getState(id);
			s.stateChange(this);
			game.enterState(id);
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
	}

	public final void enterState(int id, Transition enter, Transition leave) {
		try {
			TemplateGame game = (TemplateGame) this.getGame();
			this.stateChange(this);
			LevelState s = (LevelState) game.getState(id);
			s.stateChange(this);
			game.enterState(id, enter, leave);
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
	}

	public final void reset() {
		((TemplateGame) this.getGame()).enterStateWithCleanup(getID());
	}

	public final boolean countMod(int mod) {
		if (((TemplateGame) this.getGame()).getElapsedTime() % mod == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public final void alert(String message) {
		Sys.alert("Alert", message);
	}

	public final EntityManager getManager() {
		return manager;
	}

	public final void setUpdateMode(int mode) {
		this.getManager().setUpdateMode(mode);
	}

	public final void pause(PauseHandler h) {
		this.getManager().pause(h);
	}

	public final boolean isPaused() {
		return this.getManager().isPaused();
	}
}
