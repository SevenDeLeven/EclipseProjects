package com.sevendeleven.testproject.renderer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.sevendeleven.testproject.util.Vec2f;

public class Camera {
	
	private Vec2f position;
	private Vec2f scale;
	private AffineTransform transform;
	private static final AffineTransform resetTransform;
	
	static {
		resetTransform = new AffineTransform();
		resetTransform.setToIdentity();
	}
	
	{
		transform = new AffineTransform();
		position = new Vec2f(0,0);
		scale = new Vec2f(1, 1);
		retransform();
	}
	
	public void apply(Graphics2D g) {
		//transform.setToIdentity();
		//transform.transform(new Point((int)position.x-(Main.WIDTH/2), (int)position.y-(Main.HEIGHT/2)), new Point((int)position.x+(Main.WIDTH/2), (int)position.y+(Main.HEIGHT/2)));
		g.setTransform(transform);
	}
	
	public void unapply(Graphics2D g) {
		g.setTransform(resetTransform);
	}
	
	public void retransform() {
		transform.setToIdentity();
		transform.translate(-position.x, position.y+500);
		transform.scale(scale.x, scale.y);
	}
	
	public void setPosition(Vec2f pos) {
		this.position = pos;
		this.position.x *= this.scale.x;
		this.position.y *= this.scale.y;
		retransform();
	}
	
	public void setScale(Vec2f sca) {
		this.scale = sca;
		retransform();
	}
	
	public void reset() {
		setPosition(new Vec2f(0,0));
		setScale(new Vec2f(1,1));
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY()  {
		return position.y;
	}
	
}
