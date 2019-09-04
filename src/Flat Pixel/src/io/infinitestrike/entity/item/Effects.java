package io.infinitestrike.entity.item;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import io.infinitestrike.entity.Entity;
import io.infinitestrike.entity.EntityEffect;

public class Effects {
	public static final ItemEffect SIN_BOUNCE = new ItemEffect(){
		float i = 0;
		
		@Override
		public void onItemUpdate(Item sender, StateBasedGame game) {
			// TODO Auto-generated method stub
			sender.getRenderLocation().y += (Math.sin(i) * 3);
			i+= 0.5;
		}
		
	};
	
	public static final ItemEffect ROTATE = new ItemEffect(){
		@Override
		public void onItemUpdate(Item sender, StateBasedGame game) {
			// TODO Auto-generated method stub
			sender.setRotation(sender.getRotation() + 0.1f);
		}
	};
	
	public static final EntityEffect E_SIN_BOUNCE = new EntityEffect(){
		float i = 0;
		@Override
		public void onEntityUpdate(Entity e, StateBasedGame game, GameContainer cont) {
			// TODO Auto-generated method stub
			e.getLocation().y += (Math.sin(i) * 3);
			i+= 0.5;
		}
	};
	
	public static final EntityEffect E_ROTATE = new EntityEffect(){
		@Override
		public void onEntityUpdate(Entity e, StateBasedGame game, GameContainer cont) {
			// TODO Auto-generated method stub
			e.setRotation(2);
		}
	};
	
	public static final EntityEffect FLICKER = new EntityEffect(){
		boolean hidden = false;
		int cycle = 0;
		@Override
		public void onEntityUpdate(Entity e, StateBasedGame game, GameContainer cont) {
			// TODO Auto-generated method stub
			if(cycle % 5 == 0){
				hidden = !hidden;
				e.setHidden(hidden);
			}
			
			cycle++;
			
			if(cycle >= 80){
				hidden = false;
				cycle = 0;
				e.setHidden(false);
				e.removeEntityEffect(FLICKER);
			}
		}
		
	};
}
