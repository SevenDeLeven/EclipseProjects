package com.sevendeleven.testproject.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sevendeleven.testproject.item.BlockItem;
import com.sevendeleven.testproject.item.BlockItemStack;
import com.sevendeleven.testproject.item.ItemStack;
import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.main.Scheduler;
import com.sevendeleven.testproject.util.AABB;
import com.sevendeleven.testproject.util.Vec2f;
import com.sevendeleven.testproject.world.World;

public class EntityBlockItem extends Entity {

	private BlockItemStack stack;
	private BufferedImage img;
	
	int pickupTime = 60;
	
	private Player target;
	
	public EntityBlockItem(BlockItemStack stack, World world, float x, float y, long id) {
		super(world, x, y, id, new AABB(-0.4f, -0.4f, 0.4f, 0.4f));
		this.stack = stack;
		this.img = Register.getBlock(stack.getItem()).getImage();
	}
	
	@Override
	public void update() {
		BlockItem item = Register.getBlock(stack.getItem()).getBlockItem();
		if (stack.getSize() != item.getMaxStackSize() && !this.isRemoved()) {
			List<Entity> items = world.getEntities(EntityBlockItem.class);
			for (int i = 0; i < items.size(); i++) {
				Entity e = items.get(i);
				if (e.isRemoved() || e == this) {
					continue;
				}
				if (e.getPosition().distanceFromSq(this.getPosition()) <= 1) {
					EntityBlockItem other = (EntityBlockItem) e;
					if (other.getStack().equals(this.stack)) {
						if (other.getStack().getSize() + this.getStack().getSize() > item.getMaxStackSize()) {
							int otherStackSize = other.getStack().getSize();
							int thisStackSize = this.getStack().getSize();
							int newStackSize;
							int newOtherStackSize;
							
							if (other.getStack().getSize() > this.getStack().getSize()) {
								newStackSize = (otherStackSize+thisStackSize)%item.getMaxStackSize();
								newOtherStackSize = item.getMaxStackSize();
							} else {
								newStackSize = item.getMaxStackSize();
								newOtherStackSize = (otherStackSize+thisStackSize)%item.getMaxStackSize();
							}

							this.stack.setSize(newStackSize);
							other.getStack().setSize(newOtherStackSize);
						} else {
							if (other.getStack().getSize() >= this.getStack().getSize()) {
								other.getStack().setSize(other.getStack().getSize()+this.stack.getSize());
								Scheduler.getScheduler().removeEntity(this);
							} else {
								this.getStack().setSize(other.getStack().getSize()+this.stack.getSize());
								Scheduler.getScheduler().removeEntity(other);
							}
						}
					}
				}
			}
		}
		pickupTime -= 1;
		if (pickupTime <= 0) {
			if (target == null) {
				List<Entity> players = this.world.getEntities(Player.class);
				float minDist = 16;
				for (Entity player : players) {
					if (player.getPosition().distanceFromSq(this.getPosition()) <= minDist) {
						target = (Player) player;
						minDist = player.getPosition().distanceFromSq(this.getPosition());
					}
				}
			} else {
				if (target.getPosition().distanceFromSq(getPosition()) <= 5*5) {
					Vec2f vel = new Vec2f(vx,vy);
					Vec2f acc = target.getPosition().subtract(this.getPosition());
					acc.setMagnitude(0.5f/30.0f);
					vel = vel.add(acc);
					if (vel.magnitude() > 0.5f) {
						vel.setMagnitude(0.5f/30.0f);
					}
					this.vx = vel.x;
					this.vy = vel.y;
				} else {
					target = null;
				}
			}
		}
		
		if (target == null) {
			applyGravity();
		} else {
			if (this.collidesWithEntity(target)) {
				if (target.inventory.addItem(stack)) {
					Scheduler.getScheduler().removeEntity(this);
				}
			}
		}
		
		if (this.onGround() && this.vy <= 0) {
			this.vx *= 0.87;
		} else {
			this.vx *= 0.97;
		}
		
		move(vx+this.gravityVel.x, vy+this.gravityVel.y);
	}
	
	@Override
	public void render(Graphics2D g) {
		int dx = (int)Math.floor(((x+bounds.bottomLeft().x)*Main.getBlockSize()));
		int dy = (int)Math.floor(((y+bounds.bottomLeft().y)*Main.getBlockSize()));
		int dw = (int)(bounds.getWidth()*Main.getBlockSize());
		int dh = (int)(bounds.getHeight()*Main.getBlockSize());
		//-2 713 3 3
		g.drawImage(img, dx, -dy, dw, dh, null);
	}
	
	public ItemStack getStack() {
		return stack;
	}
}
