package io.infinitestrike.entity;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.TemplateGame;
import io.infinitestrike.state.LevelState;

public class EntityManager {

	public static final int UPDATE_ALL_AS_IS = 0;
	public static final int UPDATE_AT_DISTANCE_TO = 1;
	public static final int UPDATE_ON_SCREEN = 2;
	public static final int UPDATE_DRAW_ORDER = 3;
	public static final int UPDATE_PRIORITY_ORDER = 4;

	// related variables
	private int renderDistance = 200;
	private Entity renderTarger = null;

	// update mode
	private int updateMode = 0;
	private int maximumPriority = 10;

	// for logic haulting.
	private boolean paused = false;
	private PauseHandler handle = null;

	private ArrayList<Entity> entityList = new ArrayList<Entity>();
	private ArrayList<Class<?>> excludeList = new ArrayList<Class<?>>();
	private ArrayList<Entity> addcacheList = new ArrayList<Entity>();
	private ArrayList<Entity> remcacheList = new ArrayList<Entity>();

	private GameContainer container = null;
	private StateBasedGame game = null;
	private LevelState state = null;
	private boolean isInterating = false;

	private int updates = 0;

	public EntityManager(GameContainer cont, StateBasedGame game) {
		this.container = cont;
		this.game = game;
	}

	public void addEntity(Entity e) {
		if (this.isInterating == false) {
			e.registerEntityManager(this);
			e.onCreate(container, game);
		} else if (this.isInterating == true) {
			LogBot.logData(LogBot.Status.WARNING, "Loop Iterating, Cached object [For Creation]");
			addcacheList.add(e);
		}
	}

	public void removeEntity(Entity e) {
		if (this.isInterating == false) {
			e.onDestroy(container, game);
			e.deregisterEntityManager(this);
		} else if (this.isInterating == true) {
			LogBot.logData(LogBot.Status.WARNING, "Loop Iterating, Cached object [For Removal]");
			remcacheList.add(e);
		}
	}

	public ArrayList<Entity> getEntities() {
		return entityList;
	}

	public final void addExcludedObject(Class<?> c) {
		this.excludeList.add(c);
	}

	public final void removeExcludedObject(Class<?> c) {
		this.excludeList.remove(c);
	}

	public void setState(LevelState state) {
		this.state = state;
	}

	public LevelState getConnectedLevelState() {
		return this.state;
	}

	public void pause(PauseHandler h) {
		h.attatch(this);
		this.paused = true;
		this.handle = h;
	}

	public void unpause() {
		this.paused = false;
		this.handle = null;
	}

	public boolean isPaused() {
		return this.paused;
	}
	
	public void delegatedObjectUpdate(GameContainer arg0, StateBasedGame arg1, Graphics arg2, int arg3) {
		if (this.paused && this.handle != null) {
			this.handle.haultedUpdate(arg1, arg0, arg2, arg3);
		}

		this.updates = 0;
		if (this.updateMode == EntityManager.UPDATE_ALL_AS_IS) {
			this.isInterating = true;
			for (Entity e : entityList) {
				if (!e.isHidden()) {
					e.onPreDraw(arg0, arg1, arg2);
					e.onDraw(arg0, arg1, arg2);
					e.onPostDraw(arg0, arg1, arg2);
				}
				if(this.paused == false) {e.onPreTick(arg0, arg1, arg3);}
				if(this.paused == false) {e.onTick(arg0, arg1, arg3);}
				if(this.paused == false) {e.onPostTick(arg0, arg1, arg3);}
				e.doEffects();
				e.cooldownMod++;
				this.updates++;
			}
			this.isInterating = false;
		}

		if (this.updateMode == EntityManager.UPDATE_ON_SCREEN) {
			// if the entity is on the screen then we will render it.
			this.isInterating = true;
			for (Entity e : entityList) {
				if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
					if (!e.isHidden()) {
						e.onPreDraw(arg0, arg1, arg2);
						e.onDraw(arg0, arg1, arg2);
						e.onPostDraw(arg0, arg1, arg2);
					}
					if(this.paused == false) {e.onPreTick(arg0, arg1, arg3);}
					if(this.paused == false) {e.onTick(arg0, arg1, arg3);}
					if(this.paused == false) {e.onPostTick(arg0, arg1, arg3);}
					e.doEffects();
					e.cooldownMod++;
					this.updates++;
				}
			}
			this.isInterating = false;
		}

		if (this.updateMode == EntityManager.UPDATE_AT_DISTANCE_TO) {
			// if our target entity is null then we will do it as normal.
			if (this.renderTarger == null) {
				this.isInterating = true;
				for (Entity e : entityList) {
					if (!e.isHidden()) {
						e.onPreDraw(arg0, arg1, arg2);
						e.onDraw(arg0, arg1, arg2);
						e.onPostDraw(arg0, arg1, arg2);
					}
					if(this.paused == false) {e.onPreTick(arg0, arg1, arg3);}
					if(this.paused == false) {e.onTick(arg0, arg1, arg3);}
					if(this.paused == false) {e.onPostTick(arg0, arg1, arg3);}
					e.doEffects();
					e.cooldownMod++;
					this.updates++;
				}
				this.isInterating = false;
			} else if (this.renderTarger != null) {
				this.isInterating = true;
				for (Entity e : entityList) {
					if (EntityManager.getDistance(e, this.renderTarger) < this.renderDistance
							|| Core.complexInstanceOf(e, excludeList)) {
						if (!e.isHidden()) {
							e.onPreDraw(arg0, arg1, arg2);
							e.onDraw(arg0, arg1, arg2);
							e.onPostDraw(arg0, arg1, arg2);
						}
						if(this.paused == false) {e.onPreTick(arg0, arg1, arg3);}
						if(this.paused == false) {e.onTick(arg0, arg1, arg3);}
						if(this.paused == false) {e.onPostTick(arg0, arg1, arg3);}
						e.doEffects();
						e.cooldownMod++;
						arg2.setColor(Color.red);
						arg2.draw(e.getBounds());
						this.updates++;
					}
				}
				this.isInterating = false;
			}
		}

		if (this.updateMode == EntityManager.UPDATE_DRAW_ORDER) {
			// draw each section sperate also, stop off screen updates

			this.isInterating = true;
			for (Entity e : entityList) {
				if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
					if (!e.isHidden()) {
						e.onPreDraw(arg0, arg1, arg2);
					}
					if(this.paused == false) {e.onPreTick(arg0, arg1, arg3);}
				}
			}

			for (Entity e : entityList) {
				if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
					if (!e.isHidden()) {
						e.onDraw(arg0, arg1, arg2);
					}
					if(this.paused == false) {e.onTick(arg0, arg1, arg3);}
				}
			}

			for (Entity e : entityList) {
				if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
					if (!e.isHidden()) {
						e.onPostDraw(arg0, arg1, arg2);
					}
					if(this.paused == false) {e.onPostTick(arg0, arg1, arg3);}
				}
			}

			for (Entity e : entityList) {
				if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
					e.doEffects();
					e.cooldownMod++;
					this.updates++;
				}
			}

			this.isInterating = false;

		}

		if (this.updateMode == EntityManager.UPDATE_PRIORITY_ORDER) {
			this.isInterating = true;
			for (int i = 0; i < this.maximumPriority; i++) {
				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
							if (!e.isHidden()) {
								e.onPreDraw(arg0, arg1, arg2);
							}
							if(this.paused == false) {e.onPreTick(arg0, arg1, arg3);}
						}
					}
				}

				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
							if (!e.isHidden()) {
								e.onDraw(arg0, arg1, arg2);
							}
							if(this.paused == false) {e.onTick(arg0, arg1, arg3);}
						}
					}
				}

				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
							if (!e.isHidden()) {
								e.onPostDraw(arg0, arg1, arg2);
							}
							if(this.paused == false) {e.onPostTick(arg0, arg1, arg3);}
						}
					}
				}

				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (this.isOnScreen(e) || Core.complexInstanceOf(e, excludeList)) {
							e.doEffects();
							e.cooldownMod++;
							this.updates++;
						}
					}
				}
			}
			this.isInterating = false;
		}

		if (this.updateMode == (EntityManager.UPDATE_PRIORITY_ORDER | EntityManager.UPDATE_ALL_AS_IS)) {
			this.isInterating = true;
			for (int i = 0; i < this.maximumPriority; i++) {
				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (!Core.complexInstanceOf(e, excludeList)) {
							if (!e.isHidden()) {
								e.onPreDraw(arg0, arg1, arg2);
							}
							if(this.paused == false) {e.onPreTick(arg0, arg1, arg3);}
						}
					}
				}

				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (!Core.complexInstanceOf(e, excludeList)) {
							if (!e.isHidden()) {
								e.onDraw(arg0, arg1, arg2);
							}
							if(this.paused == false) {e.onTick(arg0, arg1, arg3);}
						}
					}
				}

				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (!Core.complexInstanceOf(e, excludeList)) {
							if (!e.isHidden()) {
								e.onPostDraw(arg0, arg1, arg2);
							}
							if(this.paused == false) {e.onPostTick(arg0, arg1, arg3);}
						}
					}
				}

				for (Entity e : entityList) {
					if (e.getUpdatePriority() == i) {
						if (!Core.complexInstanceOf(e, excludeList)) {
							e.doEffects();
							e.cooldownMod++;
							this.updates++;
						}
					}
				}
			}
			this.isInterating = false;
		}

		TemplateGame game = (TemplateGame) arg1;
		if (game.getUpdateTimer() % 5 == 0) {
			doInput();
		}

		// Perform the operations on the cached items and then
		// remove them from the list.
		for (Entity e : this.addcacheList) {
			this.addEntity(e);
		}

		for (Entity e : this.remcacheList) {
			this.removeEntity(e);
		}

		addcacheList = new ArrayList<Entity>();
		remcacheList = new ArrayList<Entity>();
	}

	public void delegatedPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		for (Entity e : entityList) {
			e.onPreDraw(arg0, arg1, arg2);
		}
	}

	public void delagetedDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		for (Entity e : entityList) {
			e.onDraw(arg0, arg1, arg2);
		}
	}

	public void delegatedPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		for (Entity e : entityList) {
			e.onPostDraw(arg0, arg1, arg2);
		}
	}

	public void delagatedPreTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		for (Entity e : entityList) {
			if(this.paused == false) {e.onPreTick(arg0, arg1, arg2);}
		}
	}

	public void delagatedTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		for (Entity e : entityList) {
			if(this.paused == false) {e.onTick(arg0, arg1, arg2);}
		}
	}

	public void delagatedPostTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		for (Entity e : entityList) {
			if(this.paused == false) {e.onPostTick(arg0, arg1, arg2);}
		}
	}

	public void doInput() {
		this.isInterating = true;
		for (Entity e : entityList) {
			if (e != null) {
				if (e.isAllowEvents()) {
					if (checkMouseInside(e)) {
						e.onMouseEvent(new InputEvent());
						if (checkMouseDownInside(e)) {
							e.onMouseEvent(new InputEvent());
						}
					}
				}
			}
		}
		this.isInterating = false;
	}

	public void onKeyDown(int i, char c) {
		for (int x = 0; x < this.entityList.size(); x++) {
			this.entityList.get(x).onKeyEvent(new InputEvent(), i, c);
		}
	}

	public void onKeyUp(int i, char c) {
		for (int x = 0; x < this.entityList.size(); x++) {
			this.entityList.get(x).onKeyEvent(new InputEvent(), i, c);
		}
	}

	public boolean checkMouseInside(Entity e) {
		return e.getBounds().intersects(new Rectangle(Mouse.getX(), container.getHeight() - Mouse.getY(), 2, 2));
	}

	public boolean checkMouseDownInside(Entity e) {
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON) | Mouse.isButtonDown(Input.MOUSE_MIDDLE_BUTTON)
				| Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			return checkMouseInside(e);
		}
		return false;
	}

	public static float getDistance(Entity e1, Entity e2) {
		float x1 = e1.getLocation().x;
		float y1 = e1.getLocation().y;

		float x2 = e2.getLocation().x;
		float y2 = e2.getLocation().y;

		return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public boolean isOnScreen(Entity e) {
		float x = e.getLocation().x;
		float y = e.getLocation().y;
		float w = e.getSize().getWidth();
		float h = e.getSize().getHeight();

		float screenWidth = this.getConnectedLevelState().getContainer().getWidth();
		float screenHeight = this.getConnectedLevelState().getContainer().getHeight();

		if (x < 0 || y < 0 || x > screenWidth || y > screenHeight) {
			return false;
		}

		return true;
	}

	public final void cleanUp() {
		for (int i = 0; i < this.getEntities().size(); i++) {
			this.removeEntity(this.getEntities().get(i));
		}
	}

	public final ArrayList<Entity> getAllEntitesOfType(Class<?> type) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		for (Entity e : this.getEntities()) {
			if (e.getClass().equals(type.getClass())) {
				list.add(e);
			}
		}
		return list;
	}

	public final int getRenderDistance() {
		return renderDistance;
	}

	public final void setRenderDistance(int renderDistance) {
		this.renderDistance = renderDistance;
	}

	public final Entity getRenderTarget() {
		return renderTarger;
	}

	public final void setRenderTarget(Entity renderTarger) {
		this.renderTarger = renderTarger;
	}

	public final int getUpdateMode() {
		return updateMode;
	}

	public final void setUpdateMode(int updateMode) {
		this.updateMode = updateMode;
	}

	public final int getUpdates() {
		return updates;
	}

	public final LevelState getParentLevelState() {
		return this.state;
	}

	protected final void updateMaxPriority(int i) {
		if (i > this.maximumPriority) {
			this.maximumPriority = i;
		}
	}

	public static abstract class PauseHandler {
		private EntityManager s;

		public void unpause() {
			if(s != null) {
				s.unpause();
			}
		}
		
		public void attatch(EntityManager m) {
			this.s = m;
		}

		public abstract void haultedUpdate(StateBasedGame g, GameContainer c, Graphics b, double time);
	}
}
