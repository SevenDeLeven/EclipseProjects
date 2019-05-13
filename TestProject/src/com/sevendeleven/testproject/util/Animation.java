package com.sevendeleven.testproject.util;

import java.awt.image.BufferedImage;

import java.awt.*;

public class Animation {
	
	SpriteSheet sheet;
	
	int index;
	
	int cols, rows;
	
	boolean flipX, flipY;
	
	public Animation(BufferedImage img, int cols, int rows, int width, int height) {
		this.index = 0;
		this.sheet = new SpriteSheet(img, width, height);
		this.cols = cols;
		this.rows = rows;
		this.flipX = false;
		this.flipY = false;
	}
	
	public Image getCurrentImage() {
		return sheet.getSprite(index%cols, (int)Math.floor(index/(double)cols));
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(getCurrentImage(), x, y, null);
	}
	
	public void drawNext(Graphics g, int x, int y) {
		next();
		draw(g,x,y);
	}
	
	public void reset() {
		setIndex(0);
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void flipX(boolean flip) {
		this.flipX = flip;
		updateFlip();
	}
	
	public void flipY(boolean flip) {
		this.flipY = flip;
		updateFlip();
	}
	
	public void updateFlip() {
		this.sheet.setFlip(flipX, flipY);
	}
	
	public void next() {
		index++;
		if (index >= rows*cols) {
			index = 0;
		}
	}
	
}
