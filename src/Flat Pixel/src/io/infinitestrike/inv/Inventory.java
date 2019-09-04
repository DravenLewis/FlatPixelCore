package io.infinitestrike.inv;

import java.util.ArrayList;
import java.util.HashMap;

import io.infinitestrike.entity.item.Item;
import io.infinitestrike.entity.item.ItemMetaData;
import io.infinitestrike.entity.item.ItemStack;

public class Inventory {
	private final ArrayList<ItemStack> inventoryItems = new ArrayList<ItemStack>();
	
	public void addItem(Item s, int amount){
		ItemStack is = this.getFirstInstanceOf(s);
		if(is != null){
			is.addCount(amount);
		}else{
			this.inventoryItems.add(new ItemStack(s,amount));
		}
	}
	
	public void removeItem(Item s, int amount){
		ItemStack is = this.getFirstInstanceOf(s);
		if(is != null){
			is.removeCount(amount);
		}
	}
	
	public ItemStack getItemStack(int index){
		return this.inventoryItems.get(index);
	}
	
	public int getInventorySize(){
		return this.inventoryItems.size();
	}
	
	public ItemStack getFirstNonNullItem(){
		for(int i = 0; i < this.inventoryItems.size(); i++){
			ItemStack s =  this.inventoryItems.get(i);
			if(s != null){
				return s;
			}
		}
		return null;
	}
	
	public void cleanUp(){
		for(int i =  this.inventoryItems.size() - 1; i > -1; i--){
			if(this.inventoryItems.get(i).getCount() == 0){
				this.inventoryItems.remove(i);
			}
		}
	}
	
	public ItemStack getFirstInstanceOf(Item i){
		for(int x = 0; x < this.inventoryItems.size(); x++){
			if(this.inventoryItems.get(x).getItemName() == i.getItemName()){
				return this.inventoryItems.get(x);
			}
		}
		return null;
	}
}