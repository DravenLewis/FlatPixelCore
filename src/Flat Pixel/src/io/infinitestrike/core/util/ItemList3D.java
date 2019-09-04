package io.infinitestrike.core.util;

import java.util.ArrayList;

public class ItemList3D<T1,T2,T3> {
	
	private final ArrayList<Item> itemlist = new ArrayList<Item>();
	
	public static class Item<T1,T2,T3>{
		public T1 type1;
		public T2 type2;
		public T3 type3;
	}

	public void add(T1 type1, T2 type2, T3 type3){
		Item<T1,T2,T3> item = new Item<T1,T2,T3>();
		item.type1 = type1;
		item.type2 = type2;
		item.type3 = type3;
		itemlist.add(item);
	}
	
	public void add(Item<T1, T2, T3> item){
		itemlist.add(item);
	}
	
	public Item<T1,T2,T3> get(int index){
		return itemlist.get(index);
	}
	
	public int size(){
		return itemlist.size();
	}
	
	public ArrayList<Item> getItems(){
		return itemlist;
	}
}
