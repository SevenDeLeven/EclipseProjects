package com.sevendeleven.testproject.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.testproject.entities.Entity;
import com.sevendeleven.testproject.entities.EntityBlockItem;
import com.sevendeleven.testproject.entities.EntityItem;
import com.sevendeleven.testproject.entities.Player;
import com.sevendeleven.testproject.item.BlockItemStack;
import com.sevendeleven.testproject.item.ToolItem;
import com.sevendeleven.testproject.main.GameController;
import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.main.Scheduler;
import com.sevendeleven.testproject.util.PerlinNoise;
import com.sevendeleven.testproject.util.Util;
import com.sevendeleven.testproject.util.Vec2f;
import com.sevendeleven.testproject.util.Vec2i;
import com.sevendeleven.testproject.world.block.BlockDestroyer;

public class World {
	
	final long seed;
	
	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	public static final int minX = -200*Chunk.WIDTH;
	public static final int maxX = -minX;
	public static final int width = maxX-minX;
	public static final int height = 256;
	
	PerlinNoise noise;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> players = new ArrayList<Entity>();
	private List<Entity> items = new ArrayList<Entity>();
	private List<Entity> blockItems = new ArrayList<Entity>();
	
	private List<BlockDestroyer> destruction = new ArrayList<BlockDestroyer>();
	
	public World() {
		seed = (long)Math.random()*Long.MAX_VALUE;
		noise = new PerlinNoise(seed);
		for (int i = 0; i < 5; i++) {
			loadChunk(i*Chunk.WIDTH - (Chunk.WIDTH*2));
		}
	}
	
	public World(long seed) {
		this.seed = seed;
	}
	
	public void update() {
		for (Entity e : entities) {
			e.update();
		}
		for (int i = 0; i < destruction.size(); i++) {
			destruction.get(i).update();
			if (destruction.get(i).shouldDestroy()) {
				this.destroyBlock(destruction.get(i).getPosition().getX(), destruction.get(i).getPosition().getY(), destruction.get(i).getDestroyer());
				Scheduler.getScheduler().removeDestruction(destruction.get(i));
			}
			if (destruction.get(i).noProgress()) {
				Scheduler.getScheduler().removeDestruction(destruction.get(i));
			}
		}
	}
	
	public void removeDestruction(BlockDestroyer bd) {
		destruction.remove(bd);
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < Main.getMain().getWidth(); i+= Main.getBlockSize()*2) {
			if (this.outOfLoadedBounds(Util.translateScreenPosToBlockPos(i, 0).x)) {
				this.loadChunk(Util.translateScreenPosToBlockPos(i, 0).x);
			}
		}
		
		if (this.outOfLoadedBounds(Util.translateScreenPosToBlockPos(Main.getMain().getWidth(), 0).x)) {
			this.loadChunk(Util.translateScreenPosToBlockPos(Main.getMain().getWidth(), 0).x);
		}
		
		for (int i = 0; i < chunks.size(); i++) {
			chunks.get(i).render(g);
		}
		
		for (int i = 0; i < destruction.size(); i++) {
			BlockDestroyer des = destruction.get(i);
			BlockPos pos = des.getPosition();
			g.drawImage(des.getImage(), pos.getX()*Main.getBlockSize(), -pos.getY()*Main.getBlockSize(), null);
		}
	}
	
	public BlockDestroyer getDestroyer(BlockPos p) {
		for (int i = 0; i < destruction.size(); i++) {
			if (destruction.get(i).getPosition().equals(p)) {
				return destruction.get(i);
			}
		}
		return null;
	}
	
	public void applyDestruction(List<Vec2f> positions, ToolItem item, Entity ent) {
		List<BlockPos> blockPositions = new ArrayList<BlockPos>();
		for (int i = 0; i < positions.size(); i++) {
			BlockPos pos = new BlockPos(this, (int)Math.floor(positions.get(i).x), (int)Math.floor(positions.get(i).y));
			boolean eq = true;
			for (BlockPos p : blockPositions) {
				if (pos.equals(p)) {
					eq = false;
					break;
				}
			}
			if (eq && !this.outOfBounds(pos)) {
				blockPositions.add(pos);
			}
		}
		for (int i = 0; i < blockPositions.size(); i++) {
			BlockDestroyer bd = getDestroyer(blockPositions.get(i));
			if (bd == null) {
				bd = new BlockDestroyer(blockPositions.get(i));
				destruction.add(bd);
			}
			bd.setActive(ent, item);
		}
	}
	
	public long getBlock(BlockPos pos) {
		int px = (int)planetaryPosX(pos.getX());
		px = pos.getX();
		Chunk chunk = getChunk(px);
		if (chunk == null) return -1;
		int blockX = px%Chunk.WIDTH;
		blockX = blockX < 0 ? Chunk.WIDTH+blockX : blockX;
		int blockY = pos.getY();
		if (blockY < 0 || blockY >= Chunk.HEIGHT || blockX < 0 || blockX >= Chunk.WIDTH) {
			return -1;
		}
		return chunk.getBlocks()[blockX][blockY];
	}
	
	public int absolutePosX(double x) {
		return (int)Math.floor(x)+(width/2);
	}
	
	public double planetaryPosX(double x) {
		double nx = x % width;
		nx = nx < 0 ? width + nx : nx;
		return nx;
	}
	
	public void placeBlock(float x, float y, String block, boolean calculated) {
		if (calculated) {
			placeBlockb(x,y,block);
		} else {
			placeBlocka((int)x,(int)y,block);
		}
	}
	
	private void placeBlocka(int screenX, int screenY, String block) {
		Vec2i pos = Util.translateScreenPosToBlockPos(screenX, screenY);
		placeBlockb(pos.x,pos.y,block);
	}
	
	private void placeBlockb(float x, float y, String block) {
		Chunk c;
		float px = (float)planetaryPosX(x);
		px = x;
		try {
			c = getChunk(px);
		} catch (Exception e) {
			System.out.println("out of bounds");
			return;
		}
		int cx = (int) (px%Chunk.WIDTH);
		int cy = (int) (y);
		cx = cx < 0 ? Chunk.WIDTH+cx : cx;
		
		if (cy < 0 || cy > Chunk.HEIGHT-1) {
			System.out.println("Block placement is of bounds");
			return;
		}
		
		c.setBlock(cx, cy, Register.getBlockId(block).getNumber());
		c.updateRenderBlock(cx, cy);
	}
	
	public void destroyBlock(int x, int y, Entity ent) {
		long b = this.getBlock(x,y);
		if (b == 0) {
			return;
		}
		ent.destroyBlock(new BlockPos(this,x,y));
		BlockItemStack i = new BlockItemStack(b, 1, 0);
		EntityBlockItem ebi = new EntityBlockItem(i, this, x+0.5f, y-0.5f, GameController.nextEntityId());
		ebi.setVelocity((new Vec2f(1,1)).setDirection((float)(Math.random()*2*Math.PI)).setMagnitude(1.0f/30.0f));
		Scheduler.getScheduler().addEntityToWorld(ebi);
		Chunk c;
		float px = (float)planetaryPosX(x);
		px = x;
		try {
			c = getChunk(px);
		} catch (Exception e) {
			return;
		}
		int cx = (int) (px%Chunk.WIDTH);
		int cy = (int) (y);
		cx = cx < 0 ? Chunk.WIDTH+cx : cx;
		
		if (cy < 0 || cy > Chunk.HEIGHT-1) {
			return;
		}
		
		c.setBlock(cx, cy, 0);
		c.updateRenderBlock(cx, cy);
	}
	
	public Chunk getChunk(float x) {
		float px = (float)planetaryPosX(x);
		px = x;
		if (outOfLoadedBounds(px)) {
			return loadChunk(px);
		}
		for (Chunk chunk : chunks) {
			if (chunk.containsX(px)) {
				return chunk;
			}
		}
		return null;
	}
	
	public boolean outOfBounds(BlockPos pos) {
		return pos.getY() < 0 || pos.getY() >= Chunk.HEIGHT;
	}
	
	public Chunk generateChunk(float x) {
		if (planetaryPosX(x) < 0 || planetaryPosX(x) > width) return null;
		Chunk chunk = new Chunk(this, x);
		chunk.generatePerlin(noise, 40, 5);
		return chunk;
	}
	
	public Chunk loadChunk(float x) {
		Chunk c = generateChunk(x);
		if (c != null) {
			c.redraw();
			chunks.add(c);
		}
		return c;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public List<Entity> getEntities(Class<? extends Entity> clazz) {
		if (clazz.equals(Entity.class)) {
			return entities;
		}
		if (clazz.equals(Player.class)) {
			return players;
		}
		if (clazz.equals(EntityItem.class)) {
			return items;
		}
		if (clazz.equals(EntityBlockItem.class)) {
			return blockItems;
		}
		return null;
	}
	
	public void addEntity(Entity ent) {
		entities.add(ent);
		Class<? extends Entity> cla = ent.getClass();
		if (cla.equals(Player.class)) {
			players.add((Player)ent);
		} else if (cla.equals(EntityItem.class)) {
			items.add((EntityItem)ent);
		} else if (cla.equals(EntityBlockItem.class)) {
			blockItems.add((EntityBlockItem)ent);
		} else {
			System.err.println("Unknown entity class name : " + cla.getName());
		}
	}
	
	public void removeEntity(Entity ent) {
		entities.remove(ent);
		Class<? extends Entity> cla = ent.getClass();
		if (cla.equals(Player.class)) {
			players.remove(ent);
		} else if (cla.equals(EntityItem.class)) {
			items.remove(ent);
		} else if (cla.equals(EntityBlockItem.class)) {
			blockItems.remove(ent);
		} else {
			System.err.println("Unknown entity class name : " + cla.getName());
		}
	}
	
	public boolean outOfLoadedBounds(float x) {
		for (Chunk chunk : chunks) {
			if (chunk.containsX(x)) {
				return false;
			}
		}
		return true;
	}

	public long getBlock(int blockX, int blockY) {
		Chunk chunk = getChunk(blockX);
		if (chunk == null) return -1;
		blockX %= Chunk.WIDTH;
		blockX = blockX < 0 ? Chunk.WIDTH+blockX : blockX;
		if (blockY < 0 || blockY >= 256 || blockX < 0 || blockX >= Chunk.WIDTH) {
			return -1;
		}
		return chunk.getBlocks()[blockX][blockY];
	}
}
