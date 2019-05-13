package com.sevendeleven.testproject.entities;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SPACE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sevendeleven.testproject.event.EventHandler;
import com.sevendeleven.testproject.event.Mouse;
import com.sevendeleven.testproject.gui.Inventory;
import com.sevendeleven.testproject.item.BlockItemStack;
import com.sevendeleven.testproject.item.ItemStack;
import com.sevendeleven.testproject.item.ToolItem;
import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.main.ResourceLocator;
import com.sevendeleven.testproject.util.AABB;
import com.sevendeleven.testproject.util.Animation;
import com.sevendeleven.testproject.util.RayCaster;
import com.sevendeleven.testproject.util.Util;
import com.sevendeleven.testproject.util.Vec2f;
import com.sevendeleven.testproject.util.Vec2i;
import com.sevendeleven.testproject.world.BlockPos;
import com.sevendeleven.testproject.world.World;

public class Player extends Entity {
	
	private boolean prevKeyEPressed = false;
	private boolean prevKeyQPressed = false;
	
	private float walkVX;
	
	private float jumpVY;
	
	public int maxJumpTime = 12;
	public float jumpVelocity = 0.45f;
	
	public int jumpTime = 0;
	
	private Animation idle_sprite;
	private Animation walk_sprite;
	
	BufferedImage img;
	
	private int dirX = 0;
	private int dirY = 0;
	
	private float walkIndexIncrement = 0;
	
	Inventory inventory;
	
	private boolean mouseDown = false;
	
	public Player(World world, float x, float y, long id) {
		super(world, x, y, id, new AABB(-0.74f, -1.4f, 0.74f, 1.4f));
		inventory = new Inventory(this, Inventory.InventoryUpgrade.None);
		idle_sprite = new Animation(ResourceLocator.getImage("player_m_idle"), 1, 1, 14, 30);
		walk_sprite = new Animation(ResourceLocator.getImage("player_m_walking"), 3, 1, 14, 30);
		
		img = new BufferedImage(14, 30, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,14,30);
	}
	
	@Override
	public void update() {
		this.inventory.checkItems();
		boolean keyEPressed = EventHandler.keys[KeyEvent.VK_E];
		boolean keyQPressed = EventHandler.keys[KeyEvent.VK_Q];
		if (keyEPressed && !prevKeyEPressed && !Main.getMain().hasGUIOn()) {
			Main.getMain().setGUI(this.inventory);
		} else if (keyEPressed && !prevKeyEPressed) {
			Main.getMain().exitGUI();
		}
		if(keyQPressed && !prevKeyQPressed) {
			this.inventory.dropItemInHand();
		}
		
		boolean onGround = this.onGround();
		boolean jumped = false;
		if (EventHandler.keys[VK_SPACE] && (onGround || (jumpTime > 0 && jumpTime < maxJumpTime)) ) {
			jumpTime++;
			jumpVY = jumpVelocity;
			gravityVel.y = 0;
			jumped = true;
		} else if (!EventHandler.keys[VK_SPACE]) {
			jumpTime = 0;
		}
		
		if (EventHandler.keys[VK_SHIFT]) {
			gravityVel.y = 0;
		}
		
		float aw = 0;
		if (EventHandler.keys[VK_A]) {
			aw -= 0.1f;
		}
		if (EventHandler.keys[VK_D]) {
			aw += 0.1f;
		}
		if (onGround) {
			if (!jumped) {
				jumpVY = 0;
				jumpTime = 0;
			}
		} else {
			aw/=2.0f;
		}
		
		if ((Math.abs(aw) < 0.04f)) {
			if (onGround) {
				walkVX /= 5;
			} else {
				walkVX /= 2;
			}
		} else if ( (aw > 0 && walkVX < 0) || (aw < 0 && walkVX > 0)) {
			aw*=3;
		}
		
		if (onGround) {
			gravityVel.y = 0;
		}
		
		walkVX += aw;
		
		if (Math.abs(walkVX) > 0.5f) {
			walkVX = 0.5f * (walkVX < 0 ? -1 : 1);
		}
		
		if (Math.abs(walkVX) <= 0.01f) {
			walkVX = 0;
		}
		
		Vec2f playerVel = new Vec2f(walkVX, jumpVY);
		Vec2f v = new Vec2f(0,0);
		v = v.add(knockbackVel);
		v = v.add(gravityVel);
		v = v.add(playerVel);
		vx = v.x;
		vy = v.y;
		applyGravity();
		move(vx, vy);
		
		if (mouseDown) {
			ItemStack handItem = this.inventory.getItemInHand();
			if (handItem != null && handItem.getClass().equals(BlockItemStack.class)) {
				Vec2i mpos = Util.translateScreenPosToBlockPos(Mouse.position.x, Mouse.position.y);
				BlockPos pos = new BlockPos(this.world, mpos.x, mpos.y);
				Register.getBlock(this.inventory.getItemInHand().getItem()).getBlockItem().onUse(this.inventory.getItemInHand(), pos, this);;
			} else if (handItem != null && Register.getItem(handItem.getItem()).getClass().equals(ToolItem.class)) {
				ToolItem item = (ToolItem) Register.getItem(handItem.getItem());
				Vec2f position = this.getPosition().copy().add(new Vec2f(0,1.0f));
				Vec2f mouse = Util.translateScreenPosToWorldPos(Mouse.position.x, Mouse.position.y);
				Vec2f difference = mouse.subtract(position);
				difference.limit(item.getReach());
				mouse = position.add(difference);
				List<Vec2f> blocks = RayCaster.rayCast(position.x, position.y, mouse.x, mouse.y, item, world);
				world.applyDestruction(blocks, item, this);
			}
//			Block Destruction mechanics
//			Vec2f pos = this.getPosition();
//			Vec2f worldPos = Util.translateScreenPosToWorldPos(Mouse.position.x, Mouse.position.y);
//			Vec2f difference = worldPos.subtract(pos);
//			difference.limit(4.0f);
//			worldPos = pos.add(difference);
//			List<Vec2f> positions = new ArrayList<Vec2f>();
//			positions.addAll(RayCaster.rayCast(this.x, this.y, worldPos.x, worldPos.y, world));
//			positions.addAll(RayCaster.rayCast(this.x, this.y+2.5f, worldPos.x, worldPos.y, world));
//			positions.addAll(RayCaster.rayCast(this.x, this.y+1.5f, worldPos.x, worldPos.y, world));
//			this.world.applyDestruction(positions);
		}
		
		
		
		//RENDER FRAME UPDATES
		
		walkIndexIncrement += Math.abs(walkVX)/3.0f;
		if (walkIndexIncrement >= 1) {
			walkIndexIncrement -= 1.0f;
			walk_sprite.next();
		}
		
		//END FRAME UPDATES
		
		if (EventHandler.keys[KeyEvent.VK_F]) {
			inventory.addItem(new ItemStack(2, 1, 0));
		}
		
		if (EventHandler.keys[KeyEvent.VK_R]) {
			inventory.addItem(new ItemStack(3, 1, 0));
		}
		
		
		
		dirX = (vx < 0 ? -1 : (vx > 0 ? 1 : dirX));
		dirY = (vy < 0 ? -1 : (vy > 0 ? 1 : dirY));
		prevKeyEPressed = keyEPressed;
		prevKeyQPressed = keyQPressed;
	}
	
	public int getDirX() {
		return dirX;
	}
	
	public int getDirY() {
		return dirY;
	}
	
	@Override
	public void destroyBlock(BlockPos pos) {
		ItemStack handItem = this.inventory.getItemInHand();
		if (handItem != null && Register.getItem(handItem.getItem()).getClass().equals(ToolItem.class)) {
			ToolItem item = (ToolItem) Register.getItem(handItem.getItem());
			item.onDestroyBlock(handItem, pos, this);
		}
	}
	
	@Override
	public void multiplyAllVelocities(float x) {
		super.multiplyAllVelocities(x);
	}
	
	@Override
	public void multiplyAllVelocitiesX(float x) {
		super.multiplyAllVelocitiesX(x);
		walkVX*=x;
	}
	@Override
	public void multiplyAllVelocitiesY(float x) {
		super.multiplyAllVelocitiesX(x);
		jumpVY*=x;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void beginDestroy() {
		mouseDown = true;
	}
	
	public void endDestroy() {
		mouseDown = false;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(0, 0, 0));
		int dx = (int)Math.floor(((x+bounds.bottomLeft().x)*Main.getBlockSize()));
		int dy = (int)Math.floor(((y+bounds.bottomLeft().y)*Main.getBlockSize()));
		int dw = (int)(bounds.getWidth()*Main.getBlockSize());
		int dh = (int)(bounds.getHeight()*Main.getBlockSize());
		//g.fillRect(dx, -(dy), dw, dh);
		if (walkVX == 0) {
			idle_sprite.next();
			idle_sprite.flipX(dirX < 0 ? true : false);
			g.drawImage(idle_sprite.getCurrentImage(), dx, -dy + 2, dw, dh, null);
		} else {
			walk_sprite.flipX(dirX < 0 ? true : false);
			g.drawImage(walk_sprite.getCurrentImage(), dx, -dy + 2, dw, dh, null);
		}
		
		
		
		Main.getMain().getCamera().unapply(g);
		if (Main.getMain().getGUI() != this.inventory) {
			this.inventory.drawHotbar(g);
		}
		Main.getMain().getCamera().apply(g);
	}
	
}
