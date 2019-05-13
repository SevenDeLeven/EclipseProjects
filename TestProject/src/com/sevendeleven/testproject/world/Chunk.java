package com.sevendeleven.testproject.world;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.util.PerlinNoise;

public class Chunk {
	
	public static final int WIDTH = 64;
	public static final int HEIGHT = 1024;
	
	public final int chunkX;
	
	private World world;
	
	private BufferedImage renderImage;
	
	private int[] heightMap;
	
	private long[][] blocks;
	
	{
		setBlocks(new long[WIDTH][HEIGHT]);
		renderImage = new BufferedImage(WIDTH*Main.getBlockSize(), HEIGHT*Main.getBlockSize(), BufferedImage.TYPE_INT_ARGB);
	}
	
	public Chunk(World world, int chunkX) {
		this.world = world;
		this.chunkX = chunkX;
	}
	
	public Chunk(World world, float x) {
		this.world = world;
		this.chunkX = (int)Math.floor(x/WIDTH);
	}
	
	public void updateBlock(int x, int y) {
		
	}
	
	public boolean containsX(float x) {
		return (x >= this.chunkX*WIDTH) && (x < (this.chunkX*WIDTH)+WIDTH);
	}
	
	public void generate(int height) {
		for (int x = 0; x < getBlocks().length; x++) {
			for (int y = 0; y < getBlocks()[x].length; y++) {
				getBlocks()[x][y] = y <= height ? 1 : 0;
			}
		}
	}
	
	
	
	public void generatePerlin(PerlinNoise noise, int height, int variety) {
		int x1 = this.world.absolutePosX(this.chunkX);
		int x2 = this.world.absolutePosX(this.chunkX+1);
		float[] h = noise.getNoise(x1, x2, height, variety, WIDTH);
		int[] intH = new int[h.length];
		for (int x = 0; x < getBlocks().length; x++) {
			int hx = (int)h[x];
			intH[x] = hx;
			for (int y = 0; y < getBlocks()[x].length; y++) {
				if (y == hx) {
					getBlocks()[x][y] = 2;
				} else if (y < hx && y >= hx-3) {
					getBlocks()[x][y] = 3;
				} else if (y < hx-3 && y != 0) {
					getBlocks()[x][y] = 1;
				} else if (y == 0) {
					getBlocks()[x][y] = 4;
				}
			}
		}
		this.heightMap = intH;
	}
	
	public void updateRenderBlock(int cx, int cy) {
		Graphics2D g = (Graphics2D)renderImage.getGraphics();
		g.setBackground(new Color(0,0,0,0));
		g.clearRect(cx*Main.getBlockSize(), renderImage.getHeight()-(cy*Main.getBlockSize())-Main.getBlockSize(), Main.getBlockSize(), Main.getBlockSize());
		g.setColor(new Color(0,0,0,0));
		g.fillRect(cx*Main.getBlockSize(), renderImage.getHeight()-(cy*Main.getBlockSize())-Main.getBlockSize(), Main.getBlockSize(), Main.getBlockSize());
		g.drawImage(Register.getBlock(blocks[cx][cy]).getImage(), cx*Main.getBlockSize(), renderImage.getHeight()-(cy*Main.getBlockSize()) - Main.getBlockSize(), null);
		g.dispose();
	}
	
	public void redraw() {
		renderImage = new BufferedImage(WIDTH*Main.getBlockSize(), HEIGHT*Main.getBlockSize(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = renderImage.getGraphics();
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, WIDTH*Main.getBlockSize(), HEIGHT*Main.getBlockSize());
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				g.drawImage(Register.getBlock(blocks[i][j]).getImage(), i*Main.getBlockSize(), renderImage.getHeight()-(j*Main.getBlockSize())-Main.getBlockSize(), null);
			}
		}
		g.dispose();
	}
	
	public BufferedImage getRenderImage() {
		return renderImage;
	}
	
	public void render(Graphics2D g) {
		g.drawImage(renderImage, this.chunkX*Chunk.WIDTH*Main.getBlockSize(), -this.renderImage.getHeight()+Main.getBlockSize(), null);
	}

	public long[][] getBlocks() {
		return blocks;
	}
	
	public void setBlock(int x, int y, long block) {
		if (x > WIDTH-1 || x < 0 || y > HEIGHT-1 || y < 0) return;
		this.blocks[x][y] = block;
	}

	public void setBlocks(long[][] blocks) {
		this.blocks = blocks;
	}
	
	public int[] getHeightMap() {
		return this.heightMap;
	}
	
}
