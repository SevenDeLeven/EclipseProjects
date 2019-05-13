package com.sevendeleven.playerthing.client;

public class UpdateInformation {
	
	int id;
	int x,y;
	
	public UpdateInformation(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}
