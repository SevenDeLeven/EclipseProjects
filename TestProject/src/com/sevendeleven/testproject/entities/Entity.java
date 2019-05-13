package com.sevendeleven.testproject.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.main.Scheduler;
import com.sevendeleven.testproject.util.AABB;
import com.sevendeleven.testproject.util.Vec2f;
import com.sevendeleven.testproject.world.BlockPos;
import com.sevendeleven.testproject.world.Chunk;
import com.sevendeleven.testproject.world.World;
public class Entity {
	
	float x,y;
	float vx,vy;
	Vec2f gravityVel = new Vec2f(0, 0);
	Vec2f knockbackVel = new Vec2f(0, 0);
	boolean corporeal;
	World world;
	float health;
	float speed = 1;
	AABB bounds;
	Vec2f gravity = new Vec2f(0, -0.98f/30.0f);
	boolean removed = false;
	final long entityID;
	
	protected Entity(World world, float x, float y, long entityID, AABB bounds) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.entityID = entityID;
		this.bounds = bounds;
	}
	
	public void update() {}
	public void render(Graphics2D g) {}
	
	public void move(float dx, float dy) {
		if (dx != 0 && dy != 0) {
			move(dx, 0);
			move(0, dy);
			return;
		}
		if (dx != 0) {
			float rem = dx%1.0f;
			float dir = (dx < 0 ? -1 : 1);
			boolean broke = false;
			for (int i = 0; i < (int)Math.floor(Math.abs(dx)); i++) {
				if (!collidesWithWorld(1.0f*dir, dy)) {
					x += 1.0f*speed*dir;
					y += dy*speed;
				} else {
					broke = true;
					vx = 0;
					break;
				}
			}
			if (!collidesWithWorld(rem, dy) && !broke) {
				x += rem*speed;
				y += dy;
			} else {
				int highest = 1;
				for (int i = 2; i <= 5; i++) {
					if (!collidesWithWorld(dx/((float)i), dy)) {
						highest = i;
					}
				}
				if (!collidesWithWorld(dx/((float)highest), dy)) {
					x += (dx/((float)highest))*speed;
					y += dy*speed;
					this.multiplyAllVelocitiesX(1.0f/highest);
				}
				if (Math.abs(dx/highest) < (1.0f/32.0f)) {
					multiplyAllVelocitiesX(0);
				}
			}
		}
		if (dy != 0) {
			float rem = dy%1.0f;
			float dir = (dy < 0 ? -1 : 1);
			boolean broke = false;
			for (int i = 0; i < (int)Math.floor(Math.abs(dy)); i++) {
				if (!collidesWithWorld(dx, 1.0f*dir)) {
					x += dx*speed;
					y += 1.0f*speed*dir;
				} else {
					broke = true;
					vy = 0;
					break;
				}
			}
			if (!collidesWithWorld(dx, rem) && !broke) {
				x += dx;
				y += rem*speed;
			} else {
				float highest = 1.0f;
				for (float i = 1.3f; i <= 5.0; i+=0.5f) {
					if (!collidesWithWorld(dx, rem/((float)i))) {
						highest = i;
						break;
					}
				}
				if (!collidesWithWorld(dx, rem/highest)) {
					x += dx*speed;
					y += (rem/highest)*speed;
					this.multiplyAllVelocitiesY(1.0f/highest);
				}
				if (Math.abs(rem/highest) < (1.1f/32.0f)) {
					multiplyAllVelocitiesY(0);
				}
			}
		}
		//this.x = (float) world.planetaryPosX(this.x);
	}
	
	public void destroyBlock(BlockPos pos) {
		
	}
	
	public void multiplyAllVelocities(float x) {
		this.knockbackVel.multiply(x);
		this.gravityVel.multiply(x);
		this.vx *= x;
	}

	public void multiplyAllVelocitiesY(float y) {
		this.knockbackVel.y *= y;
		this.gravityVel.y *= y;
		this.vy *= y;
	}
	
	public void multiplyAllVelocitiesX(float x) {
		this.knockbackVel.x *= x;
		this.gravityVel.x *= x;
	}
	
	
	public void applyGravity() {
		this.gravityVel = this.gravityVel.add(gravity);
		this.gravityVel.limit(3.8f);
	}
	
	public boolean collidesWithEntity(Entity ent) {
		return this.bounds.collides(this.getPosition(), ent.getPosition(), ent.bounds);
	}
	
	public boolean collidesWithBlock(float dx, float dy, BlockPos pos) {
		return this.bounds.collides(new Vec2f(x,y).add(new Vec2f(dx,dy)), pos);
	}
	
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
	public void remove() {
		Scheduler.getScheduler().removeEntity(this);
	}
	
	public boolean isRemoved() {
		return this.removed;
	}
	
	public boolean collidesWithWorld(float dx, float dy) {
		float left = x + (dx * speed) + (bounds.bottomLeft().x) - 1.0f;
		float right = x + (dx * speed) + (bounds.bottomRight().x) + 1.0f;
		float top = y + (dy * speed) + (bounds.topLeft().y) - 2.0f;
		float bottom = y + (dy * speed) + (bounds.bottomLeft().y) + 2.0f;
		if (world.outOfLoadedBounds(left) || world.outOfLoadedBounds(right)) {
			return true;
		}
		HashMap<BlockPos, Long> blocks = getSurroundingBlocks(left, right, top, bottom);
		for (Entry<BlockPos, Long> blockInfo : blocks.entrySet()) {
			BlockPos pos = blockInfo.getKey();
			long block = blockInfo.getValue();
			if (Register.getBlock(block) != null && Register.getBlock(block).isSolid()) {
				if (this.collidesWithBlock(dx, dy, pos)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean onGround() {
		return this.collidesWithWorld(0, -0.1f);
	}
	
	public HashMap<BlockPos, Long> getSurroundingBlocks(float left, float right, float top, float bottom) {
		if (left > right) {
			float temp = left;
			left = right;
			right = temp;
		}
		if (bottom > top) {
			float temp = bottom;
			bottom = top;
			top = temp;
		}
		HashMap<BlockPos, Long> blocks = new HashMap<BlockPos, Long>();
		int blocksWidth = (int)Math.ceil(right-left);
		int blocksHeight = (int)Math.ceil(top-bottom);
		if (left%1.0 > right%1.0) {
			blocksWidth++;
		}
		if (bottom%1.0 > top%1.0) {
			blocksHeight++;
		}
//		for (Chunk c : surroundingChunks) {
//			if (c == null) continue;
//			for (int i = 0; i < blocksWidth; i++) {
//				for (int j = 0; j < blocksHeight; j++) {
//					if (c.containsX(left+i)) {
//						int blockX = (int) Math.floor(left+i);
//						int blockY = (int) Math.floor(bottom+j);
//						BlockPos pos = new BlockPos(world,blockX,blockY);
//						int block = world.getBlock(pos);
//						blocks.put(pos, block);
//					}
//				}
//			}
//		}
		for (int i = 0; i < blocksWidth; i++) {
			for (int j = 0; j < blocksHeight; j++) {
				int blockX = (int)Math.floor(left+i);
				int blockY = (int)Math.floor(bottom+j);
				BlockPos pos = new BlockPos(world, blockX, blockY);
				if (this.collidesWithBlock(0, 0, pos)) continue;
				long block = world.getBlock(pos);
				blocks.put(pos, block);
			}
		}
		return blocks;
	}
	
	public List<Chunk> getSurroundingChunks(float left, float right) {
		List<Chunk> ret = new ArrayList<Chunk>();
		float delta = right-left;
		int chunks = (int)Math.ceil(delta/16.0);
		float leftChunkPos = left%16;
		float rightChunkPos = right%16;
		if (leftChunkPos > rightChunkPos) {
			chunks++;
		}
		for (int i = 0; i < chunks; i++) {
			ret.add(world.getChunk(left + i*16));
		}
		return ret;
	}
	
	public void updatePhysics() {
		move(vx,vy);
	}
	
	public float getDamageMultiplier() {
		return 1.0f;
	}
	
	public float getKnockbackMultiplier() {
		return 1.0f;
	}
	
	public void attackEntity(Entity ent) {
		if (ent != this) ent.attackedBy(this);
	}
	
	public void attackedBy(Entity ent) {
		if (ent != this) {
			this.health -= 1.0 * ent.getDamageMultiplier();
			this.applyKnockback(ent);
		}
	}
	
	public void applyKnockback(Entity ent) {
		float dx = this.x - ent.x;
		float dy = this.y - ent.y;
		Vec2f knockback = new Vec2f(dx, dy);
		knockback.setMagnitude(ent.getKnockbackMultiplier());
		this.vx += knockback.x;
		this.vy += knockback.y;
	}
	
	public void setVelocity(Vec2f vel) {
		this.vx = vel.x;
		this.vy = vel.y;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public Vec2f getPosition() {
		return new Vec2f(x,y);
	}
}
