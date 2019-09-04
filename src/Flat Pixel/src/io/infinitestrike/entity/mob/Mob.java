package io.infinitestrike.entity.mob;

import org.newdawn.slick.geom.Vector2f;

import io.infinitestrike.entity.Entity;
import io.infinitestrike.inv.Inventory;
import io.infinitestrike.level.TileEntity;

public abstract class Mob extends Entity {
	private Inventory inventory = new Inventory();

	private float gravity = 0;

	private float fallSpeed = 6f; // gravity
	private float jumpSpeed = -8f; // jumping

	private boolean jumping = false;
	private boolean grounded = false;
	
	private int mobIdNumber = -1;

	private int minHP = 0;
	private int HP = 10;
	private int maxHP = 0;
	
	private int jumpCooldown = 10;
	
	public Mob(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	// This area is for objects based on platformers.

	// this will check if 3 pixels below are not occupied
	// by a creature. This method can be overridden for
	// use jumping with diffrent gravity angles.

	public void jump() {
		if (grounded == true) {
			// the gravity code will calculate the jump speed.
			this.jumping = true;
		}
	}

	public void doGravity() {		
		if (!onLadder() || !jumping) {
			if (this.placeFree(0, gravity)) {
				this.getLocation().y += this.gravity;
			} else if (placeFree(0, 1)) {
				this.getLocation().y += 1;
			}

			if (this.jumping == true) {
				this.gravity = this.jumpSpeed;
			} else {
				this.gravity = this.fallSpeed;
			}
		}
		
		// if we dont have 3 pixels of space under us then
		// assume that were on a platform
		if(!this.placeFree(0, 3)){
			this.grounded = true;
		}else{
			this.grounded = false;
		}
		
		if(jumping == true && this.jumpCooldown == 0){
			this.jumping = false;
			this.jumpCooldown = 10;
		}
		
		if(this.jumpCooldown > 0){
			this.jumpCooldown--;
		}
	}

	public boolean onLadder() {
		for (Entity e : this.getCollidedObjects()) {
			if (e instanceof TileEntity) {
				TileEntity t = (TileEntity) e;
				if (t.hasFlag("ladder")) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean inWater() {
		for (Entity e : this.getCollidedObjects()) {
			if (e instanceof TileEntity) {
				TileEntity t = (TileEntity) e;
				if (t.hasFlag("water")) {
					return true;
				}
			}
		}
		return false;
	}

	public final Inventory getInventory() {
		return inventory;
	}

	public final void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public final float getFallSpeed() {
		return fallSpeed;
	}

	public final void setFallSpeed(float fallSpeed) {
		this.fallSpeed = fallSpeed;
	}

	public final float getJumpSpeed() {
		return jumpSpeed;
	}

	public final void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public final boolean isJumping() {
		return jumping;
	}

	public final boolean isGrounded() {
		return grounded;
	}

	public int getMobIdNumber() {
		return mobIdNumber;
	}

	public void setMobIdNumber(int mobIdNumber) {
		this.mobIdNumber = mobIdNumber;
	}

	public final int getMinHP() {
		return minHP;
	}

	public final void setMinHP(int minHP) {
		this.minHP = minHP;
	}

	public final int getHP() {
		return HP;
	}

	public final void setHP(int hP) {
		HP = hP;
	}

	public final int getMaxHP() {
		return maxHP;
	}

	public final void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	// END PLATFORMER
}
