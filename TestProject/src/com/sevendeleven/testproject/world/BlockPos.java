package com.sevendeleven.testproject.world;

public class BlockPos {
	
	private final World world;
	private final int x, y;
	
	public BlockPos(World world, int x, int y) {
		this.x = x;
		this.y = y;
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		BlockPos p = (BlockPos) o;
		return p.x == this.x && p.y == this.y && p.world == this.world;
	}
	
}
