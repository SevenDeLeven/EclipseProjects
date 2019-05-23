package com.sevendeleven.terrilla.entity;

import com.sevendeleven.terrilla.render.Sprite;
import com.sevendeleven.terrilla.util.ConcurrentHandler;
import com.sevendeleven.terrilla.world.Physics;

public abstract class Entity {
	
	private final long uniqueID;
	
	private Sprite sprite;
	
	public float x, y, vx, vy;
	public float angle;
	
	protected Entity(Sprite sprite, long uniqueID, float x, float y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.angle = 0;
		this.uniqueID = uniqueID;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public final long getUniqueID() {
		return this.uniqueID;
	}
	
	public void move() {
		this.x += vx;
		this.y += vy;
	}
	
	public void applyGravity() {
		vy += Physics.GRAVITY;
	}
	
	public void updateEntity(long milliTime, long deltaTick, ConcurrentHandler handler) {
		handler.updateRenderer(milliTime, deltaTick, this);
	}
	
	public abstract void update(long milliTime, long deltaTick, ConcurrentHandler handler);
	
	
}
