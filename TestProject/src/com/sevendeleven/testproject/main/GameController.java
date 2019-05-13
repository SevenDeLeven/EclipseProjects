package com.sevendeleven.testproject.main;

public class GameController {
	
	private static long nextId = 0;
	
	public static long nextEntityId() {
		return nextId++;
	}
	
}
