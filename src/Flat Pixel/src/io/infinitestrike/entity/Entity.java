package io.infinitestrike.entity;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import io.infinitestrike.core.Core;
import io.infinitestrike.core.anim.Animation;
import io.infinitestrike.core.annote.Broken;
import io.infinitestrike.core.util.ResourceLoader;
import io.infinitestrike.js.Script;
import io.infinitestrike.level.TileBasedGameLevel;
import io.infinitestrike.state.LevelState;

public abstract class Entity implements AbstractGameObject {

	public static final int DRAW_TOP_AS_BOUNDS = 0;
	public static final int DRAW_BOTTOM_AS_BOUNDS = 1;

	// image rotation modes
	public static final int IMAGE_MODE_CENTER = 0;
	public static final int IMAGE_MODE_ORIGIN = 1;
	public static final int IMAGE_MODE_USER = 2;

	private Vector2f location = null;
	private Vector2f locationPrevious = null;
	private Dimension size = null;
	private EntityManager manager = null;
	private Animation entityAnimation = null;

	private boolean allowEvents = true;
	private boolean hidden = false;
	private boolean isSolid = false;
	private boolean checkOOB = false;

	private Color blendColor = Color.white;
	private boolean blendOption = false;

	private Image image = null;
	private int drawMode = 0;
	private float angle = 0;
	private float image_centerX = 0, image_centerY = 0;
	private int image_contstraintMode = 0;
	private int updatePriority = 5;

	private ArrayList<EntityEffect> entityEffects = new ArrayList<EntityEffect>();
	private ArrayList<Script> scripts = new ArrayList<Script>();

	protected int cooldownMod = 0;
	protected int entityCooldown = 0;
	private int maxEntityCooldown = 20;

	/**
	 * Create the entity.
	 * 
	 * @param x
	 *            - x location
	 * @param y
	 *            - y location
	 */
	public Entity(int x, int y) {
		location = new Vector2f(x, y);
		this.locationPrevious = new Vector2f(x, y);
		size = new Dimension(32, 32);
	}

	/**
	 * Create the entity with a specific width and height.
	 * 
	 * @param x
	 *            - x location
	 * @param y
	 *            - y location
	 * @param w
	 *            - w width of the object
	 * @param h
	 *            - h height of the object.
	 */
	public Entity(int x, int y, int w, int h) {
		location = new Vector2f(x, y);
		this.locationPrevious = new Vector2f(x, y);
		size = new Dimension(w, h);
	}

	public final boolean cooldownZero() {
		if (this.entityCooldown == 0) {
			this.entityCooldown = this.maxEntityCooldown;
			return true;
		}
		return false;
	}

	public void setMaxCooldown(int i) {
		this.maxEntityCooldown = i;
	}

	public int getMaxColdown() {
		return this.maxEntityCooldown;
	}

	public final boolean countMod(int value) {
		if (this.cooldownMod % value == 0) {
			return true;
		}
		return false;
	}

	public final boolean isSolid() {
		return isSolid;
	}

	public final void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	public final void setLocation(Vector2f vec2) {
		this.locationPrevious = this.location;
		this.location = vec2;
		this.onDimensionChange(this);
	}

	public final void importScript(Script s) {
		this.scripts.add(s);
	}

	public final void importScript(Script s, int index) {
		this.scripts.add(index, s);
	}

	public final void removeScript(int index) {
		this.scripts.remove(index);
	}

	public final void removeScripts() {
		this.scripts = new ArrayList<Script>();
	}

	public final void setSize(Dimension dim) {
		this.size = dim;
		this.onDimensionChange(this);
	}

	public final Vector2f getLocation() {
		return this.location;
	}

	public final Dimension getSize() {
		return this.size;
	}

	public final ArrayList<Entity> getCollidedObjects() {

		if (manager == null) {
			return null;
		}

		ArrayList<Entity> tempList = manager.getEntities();
		ArrayList<Entity> returnList = new ArrayList<Entity>();
		for (int i = 0; i < tempList.size(); i++) {
			if (collides(tempList.get(i), this)) {
				returnList.add(tempList.get(i));
			}
		}
		return returnList;
	}

	public final boolean collides(Entity e, Entity e2) {
		return e.getBounds().intersects(e2.getBounds());
	}

	public final EntityManager getEntityManager() {
		return this.manager;
	}

	public final void registerEntityManager(EntityManager m) {
		manager = m;
		m.getEntities().add(this);
	}

	public final void deregisterEntityManager(EntityManager m) {
		m.getEntities().remove(this);
	}

	public final void setEntityManager(EntityManager m) {
		this.manager = m;
	}

	public void destroy() {
		this.setAllowEvents(false);
		if (this.manager != null) {
			manager.removeEntity(this);
		}
	}

	/**
	 * Get the bounding box of the entity
	 * 
	 * @return - the objects bounding box
	 */
	public Rectangle getBounds() {
		return new Rectangle(location.x, location.y, size.getWidth(), size.getHeight());
	}

	/**
	 * Move the object
	 * 
	 * @param xf
	 *            - x factor to add to the location
	 * @param yf
	 *            - y factor to add to the location
	 */
	public final void move(float xf, float yf) {
		this.locationPrevious = this.location;
		location.x += xf;
		location.y += yf;
		this.onDimensionChange(this);
	}

	public void moveCollisionDetected(float x, float y) {

		if (this.manager == null) {
			return;
		}

		boolean intercect = false;

		for (Entity e : this.getEntityManager().getEntities()) {
			Rectangle playerNext = new Rectangle(this.getLocation().x + x, this.getLocation().y + y,
					this.getSize().getWidth(), this.getSize().getHeight());
			Rectangle entityBoundingBox = e.getBounds();

			if (playerNext.intersects(entityBoundingBox) && e != this && e.isSolid()) {

				intercect = true;
				break;
			} else if (playerNext.getX() < 0 || playerNext.getY() < 0
					|| (playerNext.getX() + playerNext.getWidth()) > this.getEntityManager().getParentLevelState()
							.getContainer().getWidth()
					|| (playerNext.getY() + playerNext.getHeight()) > this.getEntityManager().getParentLevelState()
							.getContainer().getHeight()) {
				// if were out of bounds
				if (this.checkOOB == true) {
					intercect = true;
					break;
				}
			}
		}

		if (intercect) {
			this.move(0, 0);
		} else {
			this.locationPrevious = this.location;
			this.move(x, y);
		}
	}

	public void moveCollisionDetected(float x, float y, ArrayList<? extends Entity> additionalList) {

		if (this.manager == null) {
			return;
		}

		boolean intercect = false;

		for (Entity e : this.getEntityManager().getEntities()) {
			Rectangle playerNext = new Rectangle(this.getLocation().x + x, this.getLocation().y + y,
					this.getSize().getWidth(), this.getSize().getHeight());
			Rectangle entityBoundingBox = e.getBounds();

			if (playerNext.intersects(entityBoundingBox) && e != this && e.isSolid()) {

				intercect = true;
				break;
			} else if (playerNext.getX() < 0 || playerNext.getY() < 0
					|| (playerNext.getX() + playerNext.getWidth()) > this.getEntityManager().getParentLevelState()
							.getContainer().getWidth()
					|| (playerNext.getY() + playerNext.getHeight()) > this.getEntityManager().getParentLevelState()
							.getContainer().getHeight()) {
				// if were out of bounds
				if (this.checkOOB == true) {
					intercect = true;
					break;
				}
			}
		}

		for (Entity e : additionalList) {
			Rectangle playerNext = new Rectangle(this.getLocation().x + x, this.getLocation().y + y,
					this.getSize().getWidth(), this.getSize().getHeight());
			Rectangle entityBoundingBox = e.getBounds();

			if (playerNext.intersects(entityBoundingBox) && e != this && e.isSolid()) {
				intercect = true;
				break;
			}
		}

		if (intercect) {
			this.move(0, 0);
		} else {
			this.locationPrevious = this.location;
			this.move(x, y);
		}
	}

	public void back() {
		float xn = this.locationPrevious.x;
		float yn = this.locationPrevious.y;

		this.locationPrevious = this.location;
		this.location = new Vector2f(xn, yn);
	}

	public boolean placeFree(float x, float y) {

		if (this.manager == null) {
			return false;
		}

		for (Entity e : this.getEntityManager().getEntities()) {
			Rectangle playerNext = new Rectangle(this.getLocation().x + x, this.getLocation().y + y,
					this.getSize().getWidth(), this.getSize().getHeight());
			Rectangle entityBoundingBox = e.getBounds();

			if (playerNext.intersects(entityBoundingBox) && e != this && e.isSolid() == true) {
				return false;
			} else if (playerNext.getX() < 0 || playerNext.getY() < 0
					|| (playerNext.getX() + playerNext.getWidth()) > this.getEntityManager().getParentLevelState()
							.getContainer().getWidth()
					|| (playerNext.getY() + playerNext.getHeight()) > this.getEntityManager().getParentLevelState()
							.getContainer().getHeight()) {
				// if were out of bounds
				if (this.checkOOB == true) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean placeFree(float x, float y, float w, float h) {

		if (this.manager == null) {
			return false;
		}

		for (Entity e : this.getEntityManager().getEntities()) {
			Rectangle playerNext = new Rectangle((this.getLocation().x + x), (this.getLocation().y + y), w, h);
			Rectangle entityBoundingBox = e.getBounds();

			if (playerNext.intersects(entityBoundingBox) && e != this && e.isSolid() == true) {
				return false;
			} else if (playerNext.getX() < 0 || playerNext.getY() < 0
					|| (playerNext.getX() + playerNext.getWidth()) > this.getEntityManager().getParentLevelState()
							.getContainer().getWidth()
					|| (playerNext.getY() + playerNext.getHeight()) > this.getEntityManager().getParentLevelState()
							.getContainer().getHeight()) {
				// if were out of bounds
				if (this.checkOOB == true) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean placeMeeting(float x, float y) {

		if (this.manager == null) {
			return false;
		}

		for (Entity e : this.getEntityManager().getEntities()) {
			Rectangle playerNext = new Rectangle(this.getLocation().x + x, this.getLocation().y + y,
					this.getSize().getWidth(), this.getSize().getHeight());
			Rectangle entityBoundingBox = e.getBounds();
		}
		return true;
	}

	public boolean placeMeeting(float x, float y, float w, float h) {

		if (this.manager == null) {
			return false;
		}

		for (Entity e : this.getEntityManager().getEntities()) {
			Rectangle playerNext = new Rectangle((this.getLocation().x + x), (this.getLocation().y + y), w, h);
			Rectangle entityBoundingBox = e.getBounds();
		}
		return true;
	}

	public boolean placeMeeting(float x, float y, float w, float h, Class<? extends Entity> type) {

		if (this.manager == null) {
			return false;
		}

		for (Entity e : this.getEntityManager().getEntities()) {
			Rectangle playerNext = new Rectangle((this.getLocation().x + x), (this.getLocation().y + y), w, h);
			Rectangle entityBoundingBox = e.getBounds();
		}
		return true;
	}

	public boolean placeOnScreen(float x, float y) {
		if (x < 0 || x > getLevel().RIGHT || y < 0 || y > getLevel().BOTTOM) {
			return false;
		}
		return true;
	}

	public boolean placeOnScreen(Vector2f location) {
		return this.placeOnScreen(location.x, location.y);
	}
	
	public void draw() {
		if (this.image != null) {
			switch (this.drawMode) {
			case Entity.DRAW_TOP_AS_BOUNDS:
				switch (this.image_contstraintMode) {
				case IMAGE_MODE_CENTER:
					// REPLACE THE SWITCH WITH THIS LINE IF ERROR
					image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);
					break;
				case IMAGE_MODE_ORIGIN:
					image.setCenterOfRotation(0, 0);
					break;
				case IMAGE_MODE_USER:
					image.setCenterOfRotation(image_centerX, image_centerY);
					break;
				}
				// image.rotate(angle);
				image.setRotation(angle);
				if (this.blendOption == true) {
					image.draw(this.getLocation().x, this.getLocation().y);
				} else {
					image.draw(this.getLocation().x, this.getLocation().y, this.blendColor);
				}
				break;

			case Entity.DRAW_BOTTOM_AS_BOUNDS:
				// float calc_x = (this.getLocation().x +
				// this.getSize().getHeight()) - (this.image.getHeight());
				// float calc_y = (this.getLocation().y +
				// this.getSize().getWidth()) - (this.image.getWidth());

				float calc_x = (this.getLocation().x + this.getSize().getWidth()) - (this.image.getWidth());
				float calc_y = (this.getLocation().y + this.getSize().getHeight()) - (this.image.getHeight());
				switch (this.image_contstraintMode) {
				case IMAGE_MODE_CENTER:
					// REPLACE THE SWITCH WITH THIS LINE IF ERROR
					image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);
					break;
				case IMAGE_MODE_ORIGIN:
					image.setCenterOfRotation(0, 0);
					break;
				case IMAGE_MODE_USER:
					image.setCenterOfRotation(image_centerX, image_centerY);
					break;
				}
				image.setRotation(angle);
				// image.rotate(angle);
				if (this.blendOption == true) {
					image.draw(calc_x, calc_y, this.blendColor);
				} else {
					image.draw(calc_x, calc_y);
				}
				break;
			}
		}
	}

	public void lockToAngle(float angle) {
		this.getImage().setRotation(angle);
	}

	public void draw(float x, float y, float scale, boolean xf, boolean yf) {
		if (this.image != null) {
			switch (this.drawMode) {
			case Entity.DRAW_TOP_AS_BOUNDS:
				Image img = image.copy();
				img = img.getFlippedCopy(xf, yf);
				switch (this.image_contstraintMode) {
				case IMAGE_MODE_CENTER:
					// REPLACE THE SWITCH WITH THIS LINE IF ERROR
					img.setCenterOfRotation((img.getWidth() * scale) / 2, (img.getHeight() * scale) / 2);
					break;
				case IMAGE_MODE_ORIGIN:
					img.setCenterOfRotation(0 - (scale), 0 - (scale));
					break;
				case IMAGE_MODE_USER:
					img.setCenterOfRotation(image_centerX, image_centerY);
					break;
				}

				img.setRotation(angle);
				// img.rotate(angle);
				if (this.blendOption == true) {
					img.draw(this.getLocation().x + x, this.getLocation().y + y, scale, this.blendColor);
				} else {
					img.draw(this.getLocation().x + x, this.getLocation().y + y, scale);
				}
				break;

			case Entity.DRAW_BOTTOM_AS_BOUNDS:
				// float calc_x = (this.getLocation().x +
				// this.getSize().getHeight()) - (this.image.getHeight());
				// float calc_y = (this.getLocation().y +
				// this.getSize().getWidth()) - (this.image.getWidth());

				float calc_x = (this.getLocation().x + this.getSize().getWidth()) - (this.image.getWidth());
				float calc_y = (this.getLocation().y + this.getSize().getHeight()) - (this.image.getHeight());

				Image img2 = image.copy();
				img2 = img2.getFlippedCopy(xf, yf);
				switch (this.image_contstraintMode) {
				case IMAGE_MODE_CENTER:
					// REPLACE THE SWITCH WITH THIS LINE IF ERROR
					img2.setCenterOfRotation((img2.getWidth() * scale) / 2, (img2.getHeight() * scale) / 2);
					break;
				case IMAGE_MODE_ORIGIN:
					img2.setCenterOfRotation(0 - (scale), 0 - (scale));
					break;
				case IMAGE_MODE_USER:
					img2.setCenterOfRotation(image_centerX, image_centerY);
					break;
				}
				// !! New Code May cause errors
				img2.setRotation(angle);
				// img2.rotate(angle);
				if (this.blendOption == true) {
					img2.draw(calc_x, calc_y, scale, blendColor);
				} else {
					img2.draw(calc_x + x, calc_y + y, scale);
				}
				break;
			}
		}
	}

	public boolean isAllowEvents() {
		return allowEvents;
	}

	public void setAllowEvents(boolean allowEvents) {
		this.allowEvents = allowEvents;
	}

	public abstract void onDimensionChange(Entity e);

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setDrawMode(int mode) {
		this.drawMode = mode;
	}

	public void doMouseGrab() {

		if (manager == null) {
			return;
		}

		float x = Mouse.getX();
		float y = this.getEntityManager().getConnectedLevelState().getContainer().getHeight() - Mouse.getY();

		if (getEntityManager().checkMouseDownInside(this)) {
			this.setLocation(new Vector2f(getLocation().x + InputEvent.MOUSE_DELTA_X,
					getLocation().y + InputEvent.MOUSE_DELTA_Y));
		}
	}

	public void addEntityEffect(EntityEffect e) {
		this.entityEffects.add(e);
	}

	public void removeEntityEffect(EntityEffect e) {
		this.entityEffects.remove(e);
	}

	public boolean hasEffect(EntityEffect e) {
		for (EntityEffect ef : this.entityEffects) {
			if (ef.equals(e)) {
				return true;
			}
		}
		return false;
	}

	public void doEffects() {
		for (int i = 0; i < this.entityEffects.size(); i++) {
			EntityEffect e = this.entityEffects.get(i);
			e.onEntityUpdate(this, this.getEntityManager().getConnectedLevelState().getGame(),
					this.getEntityManager().getConnectedLevelState().getContainer());
		}
	}

	@Broken
	@Deprecated
	/**
	 * Returns the current connected level.
	 * 
	 * Depricated, this method has a tendancey to throw a null pointer exception
	 * when a user defined level state is not used.
	 * 
	 * use instead getGameLevel()
	 * 
	 * @return the current level.
	 */
	public final TileBasedGameLevel getCurrentLevel() {
		return this.getEntityManager().getConnectedLevelState().getCurrentLevel();
	}

	public final TileBasedGameLevel getGameLevel() {
		return this.getLevel().getCurrentLevel();
	}

	public Animation getEntityAnimation() {
		return entityAnimation;
	}

	public void setEntityAnimation(Animation entityAnimation) {
		this.entityAnimation = entityAnimation;
	}

	public final Color getBlendColor() {
		return blendColor;
	}

	public final void setBlendColor(Color blendColor) {
		this.blendColor = blendColor;
	}

	public final boolean isBlendOption() {
		return blendOption;
	}

	public final void setBlendOption(boolean blendOption) {
		this.blendOption = blendOption;
	}

	public final LevelState getLevel() {
		if (this.getEntityManager() == null) {
			return null;
		}
		return this.getEntityManager().getConnectedLevelState();
	}

	public ArrayList<Entity> getCollidedEntitiesAt(int x, int y) {

		Rectangle r = new Rectangle(this.getLocation().x + x, this.getLocation().y + y, this.getSize().getWidth(),
				this.getSize().getHeight());
		ArrayList<Entity> ea = new ArrayList<Entity>();

		if (this.getEntityManager() != null) {
			for (Entity e : this.getEntityManager().getEntities()) {
				if (r.intersects(e.getBounds()) && e != this) {
					ea.add(e);
				}
			}
		}

		return ea;
	}

	public final boolean isCollidingWith(Class<?> obj) {
		for (int i = 0; i < this.getCollidedObjects().size(); i++) {
			Entity e = this.getCollidedObjects().get(i);
			if (e.getClass().equals(obj.getClass())) {
				return true;
			}
		}
		return false;
	}

	public Vector2f getRandomPointOnEntity() {
		float xRandom = (float) Core.MathFunctions.randomRange(this.getLocation().x,
				this.getLocation().x + this.getSize().getWidth());
		float yRandom = (float) Core.MathFunctions.randomRange(this.getLocation().y,
				this.getLocation().y + this.getSize().getHeight());

		return new Vector2f(xRandom, yRandom);
	}

	public float getRotation() {
		return angle;
	}

	public void setRotation(float angle) {
		this.angle = angle;
	}

	public void setRotationConstraint(int mode) {
		this.setRotationConstraint(mode, 0, 0);
	}

	public void setRotationConstraint(int mode, float x, float y) {
		this.image_contstraintMode = mode;
		switch (this.image_contstraintMode) {
		case Entity.IMAGE_MODE_USER:
			this.image_centerX = x;
			this.image_centerY = y;
			break;
		}
	}

	public int getRotationConstraint() {
		return this.image_contstraintMode;
	}

	public void resetRotation() {
		this.angle = 0;
	}

	public final boolean isCheckOOB() {
		return checkOOB;
	}

	public final void setCheckOOB(boolean checkOOB) {
		this.checkOOB = checkOOB;
	}

	public final void setUpdatePriority(int i) {
		this.updatePriority = i;
		this.getEntityManager().updateMaxPriority(i);
	}

	public final int getUpdatePriority() {
		return this.updatePriority;
	}
}
