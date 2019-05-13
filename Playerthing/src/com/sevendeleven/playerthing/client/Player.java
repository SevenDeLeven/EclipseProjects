package com.sevendeleven.playerthing.client;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	public int x, y;
	public String username;
	public Color color;
	
	public Player(String username, Color color) {
		this.username = username;
		this.color = color;
	}
	
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(x, y, 10, 10);
		g.drawString(username, x, y+20);
	}
	
}
