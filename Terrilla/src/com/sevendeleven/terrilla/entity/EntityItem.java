package com.sevendeleven.terrilla.entity;

import com.sevendeleven.terrilla.util.ConcurrentHandler;
import com.sevendeleven.terrilla.util.ResourcesManager;

public class EntityItem extends Entity {
	
	public EntityItem(String itemName, long uniqueID, float x, float y) {
		super(ResourcesManager.getSprite("item_"+itemName), uniqueID, x, y);
	}
	
	@Override
	public void update(long milliTime, long deltaTick, ConcurrentHandler concurrentHandler) {
//		System.out.println(this.x + " " + this.y + " " + this.vx + " " + this.vy);
		this.applyGravity();
		this.move();
		this.updateEntity(milliTime, deltaTick, concurrentHandler);
	}
	
}
