package com.sevendeleven.testproject.util;

import java.awt.image.*;
import java.awt.*;

public class SpriteSheet {
	
	int width, height;
	boolean flipX, flipY;
	
	BufferedImage img;
	BufferedImage flippedImg;
	
	public SpriteSheet(BufferedImage img, int spritewidth, int spriteheight) {
		this.img = img;
		this.width = spritewidth;
		this.height = spriteheight;
		this.flipX = false;
		this.flipY = false;
		this.genFlipped();
	}
	
	private void genImage() {
		int iwidth = this.width*Math.floorDiv(img.getWidth(), this.width);
		int iheight = this.height*Math.floorDiv(img.getHeight(), this.height);
		BufferedImage imgCopy = new BufferedImage(iwidth, iheight, BufferedImage.TYPE_INT_ARGB);
		int cols = iwidth/width;
		int rows = iheight/height;
		Graphics g = imgCopy.getGraphics();
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				g.drawImage(this.img.getSubimage(i*width, j*height, width, height), i*width, j*height, null);
			}
		}
		this.img = imgCopy;
	}
	
	private void genFlipped() {
		genImage();
		BufferedImage fimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = fimg.getGraphics();
		int cols = (int)Math.floor(img.getWidth() / (double) width);
		int rows = (int)Math.floor(img.getHeight() / (double) height);
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				g.drawImage(this.img.getSubimage(i*width, j*height, width, height), (i*width)+width, j*height, -width, height, null);
			}
		}
		this.flippedImg = fimg;
	}
	
	public Image getSprite(int col, int row) {
		return (flipX ? flippedImg : img).getSubimage(col*width, row*height, width, height);
	}
	
	public void setFlip(boolean flipX, boolean flipY) {
		this.flipX = flipX;
		this.flipY = flipY;
	}
	
}
