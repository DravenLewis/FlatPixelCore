package io.infinitestrike.entity.item;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.InputEvent;

public final class Item extends Entity{

	private Image itemImage = null;
	private float rotation = 0.0f;
	private String itemName = "";
	private boolean stackable = true;
	private boolean hasGravity = true;
	
	private int cooldown = 0;
	private int animationTimer = 20;
	private float gravity = 1f;
	
	
	
	public final boolean isStackable() {
		return stackable;
	}

	public final void setStackable(boolean stackable) {
		this.stackable = stackable;
	}

	private Vector2f renderLocation;
	
	private ArrayList<ItemEffect> itemEffects = new ArrayList<ItemEffect>();
	
	public final ArrayList<ItemEffect> getItemEffects() {
		return itemEffects;
	}

	public final void setItemEffects(ArrayList<ItemEffect> itemEffects) {
		this.itemEffects = itemEffects;
	}

	public Item(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public void addEffect(ItemEffect i){
		itemEffects.add(i);
	}

	public void removeEffect(ItemEffect i){
		itemEffects.remove(i);
	}
	
	public final Image getItemImage() {
		return itemImage;
	}

	public final void setItemImage(Image itemImage) {
		this.itemImage = itemImage;
	}

	public final float getRotation() {
		return rotation;
	}

	public final void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public final String getItemName() {
		return itemName;
	}

	public final void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Override
	public void onCreate(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		this.renderLocation = this.getLocation();
		if(this.getEntityAnimation() != null){
			this.setItemImage(this.getEntityAnimation().first());
		}
	}

	@Override
	public void onPreDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	public final Vector2f getRenderLocation() {
		return renderLocation;
	}

	public final void setRenderLocation(Vector2f renderLocation) {
		this.renderLocation = renderLocation;
	}
	
	public final boolean hasEntityManager(){
		return (this.getEntityManager() != null);
	}
	
	@Override
	public void onDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		if(this.itemImage != null){
			itemImage.rotate(rotation);
			if(this.isBlendOption()){
				itemImage.draw(getRenderLocation().x, getRenderLocation().y, getSize().getWidth(),getSize().getHeight(),this.getBlendColor());
			}else{
				itemImage.draw(getRenderLocation().x, getRenderLocation().y, getSize().getWidth(),getSize().getHeight());
			}
		}else{
			arg2.setColor(Color.red);
			arg2.rotate(this.getBounds().getCenterX(), this.getBounds().getCenterX(), this.rotation);
			arg2.fillRect(getRenderLocation().x, getRenderLocation().y, getSize().getWidth(),getSize().getHeight());
		}
	}

	@Override
	public void onPostDraw(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		ArrayList<ItemEffect> itemEffects2 = this.itemEffects;
		for (int j = 0; j < itemEffects2.size(); j++) {
			ItemEffect i = itemEffects2.get(j);
			i.onItemUpdate(this,arg1);
		}
		
		if(this.getEntityAnimation() != null){
			if(this.cooldown % this.animationTimer == 0){
				this.setItemImage(this.getEntityAnimation().next());
			}
		}
		
		this.cooldown++;
	}

	@Override
	public void onPostTick(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		if(this.hasGravity == true){
			if(placeFree(0,this.gravity)){
				this.moveCollisionDetected(0, this.gravity);
			}
		}
	}

	@Override
	public void onMouseEvent(InputEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy(GameContainer arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDimensionChange(Entity e) {
		// TODO Auto-generated method stub
		this.renderLocation = this.getLocation();
	}

	@Override
	public void onKeyEvent(InputEvent e, int keyCode, char keyChar) {
		// TODO Auto-generated method stub
		
	}

	public int getAnimationTimer() {
		return animationTimer;
	}

	public void setAnimationTimer(int animationTimer) {
		this.animationTimer = animationTimer;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public final boolean isHasGravity() {
		return hasGravity;
	}

	public final void setHasGravity(boolean hasGravity) {
		this.hasGravity = hasGravity;
	}
}
