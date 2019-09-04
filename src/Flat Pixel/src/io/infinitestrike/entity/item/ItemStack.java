package io.infinitestrike.entity.item;

import java.util.ArrayList;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.Image;

public class ItemStack{
	private int count = 0;
	private Item item = null;

	private String itemName = "";
	
	public ItemStack(Item i, int count){
		this.count = count;
		this.itemName = i.getItemName();
		this.item = i;
	}
	
	public boolean addItem(Item i){
		if(i.getItemName() == this.itemName){
			this.count += 1;
			return true;
		}
		return false;
	}
	
	public boolean removeItem(Item i){
		if(i.getItemName() == this.itemName){
			this.count -= 1;
			return true;
		}
		return false;
	}
	
	public boolean isStackable(){
		return item.isStackable();
	}
	
	public ItemStack duplicateStack(){
		return new ItemStack(this.item,this.count);
	}
	
	public Image getItemImage(){
		return item.getItemImage();
	}
	
	public void addCount(int amount){
		this.count += amount;
	}
	
	public void removeCount(int amount){
		this.count -= amount;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public String getItemName(){
		return this.itemName;
	}
	
	public Dimension getSize(){
		return this.item.getSize();
	}
	
	public ArrayList<ItemEffect> getItemEffect(){
		return item.getItemEffects();
	}
	
	public void setItemEffect(ArrayList<ItemEffect> e){
		this.item.setItemEffects(e);
	}
	
	@Override
	public String toString(){
		return "ItemStack@["+this.itemName+":"+this.count+"]";
		
	}
	
}
