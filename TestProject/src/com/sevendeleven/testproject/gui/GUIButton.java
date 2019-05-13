package com.sevendeleven.testproject.gui;

import java.awt.event.MouseEvent;

public class GUIButton {
	
	private int x, y, w, h;
	
	public GUIButton(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public GUIButton setX(int x) {
		this.x = x;
		return this;
	}
	
	public GUIButton setY(int y) {
		this.y = y;
		return this;
	}
	
	public GUIButton setWidth(int w) {
		this.w = w;
		return this;
	}
	
	public GUIButton setHeight(int h) {
		this.h = h;
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public boolean click(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		return mx >= this.x && my >= this.y && mx <= this.x+this.w && my <= this.y+this.h;
	}
	
}
