package com.cgcult.cggame.game;

public class Updater {
	
	Thread tickThread;
	Thread renderThread;
	
	Ticker ticker;
	Renderer renderer;
	
	public Updater() {
		ticker = new Ticker();
		renderer = new Renderer();
	}
	
	private class Ticker implements Runnable {
		public void run() {
			while (true) {
				
			}
		}
	}
	
	private class Renderer implements Runnable {
		public void run() {
			while (true) {
				
			}
		}
	}
	
	public void runUpdater() {
		tickThread = new Thread(ticker);
		tickThread.start();
		
		renderThread = new Thread(renderer);
		renderThread.start();
	}
	
}
