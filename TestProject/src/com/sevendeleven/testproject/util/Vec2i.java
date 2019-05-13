package com.sevendeleven.testproject.util;

import com.sevendeleven.testproject.world.BlockPos;

public class Vec2i {
	public int x,y;
	public Vec2i(Vec2i v) {
		this.x = v.x;
		this.y = v.y;
	}
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Vec2i(BlockPos pos) {
		this.x = pos.getX();
		this.y = pos.getY();
	}
	public int magnitude() {
		return (int)Math.sqrt(x*x+y*y);
	}
	public int magnitudeSq() {
		return x*x+y*y;
	}
	public Vec2i difference(Vec2i vec) {
		return new Vec2i(this.x-vec.x, this.y-vec.x);
	}
	public int distanceFrom(Vec2i vec) {
		return this.difference(vec).magnitude();
	}
	public int distanceFromSq(Vec2i vec) {
		return this.difference(vec).magnitudeSq();
	}
	public void setMagnitude(int magnitude) {
		int currentMagnitude = this.magnitude();
		if (currentMagnitude == 0) {
			int mult = magnitude/currentMagnitude;
			this.x *= mult;
			this.y *= mult;
		}
	}
	public void limit(int mag) {
		if (this.magnitudeSq() > mag*mag) {
			this.setMagnitude(mag);
		}
	}
	public Vec2i add(Vec2i a) {
		return new Vec2i(x+a.x, y+a.y);
	}
	public Vec2i subtract(Vec2i a) {
		return add(a.multiply(-1));
	}
	public Vec2i multiply(int a) {
		return new Vec2i(this.x*a, this.y*a);
	}
	public Vec2i divide(int a) {
		return multiply(1/a);
	}
	public Vec2i copy() {
		return new Vec2i(this);
	}
}
