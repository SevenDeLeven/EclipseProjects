package com.sevendeleven.eventfulexplosion.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Main extends Canvas {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 600;
	public static final int HEIGHT = 500;
	
	public BufferedImage background = ResourceLocator.getImage("background.png");
	
	public static void main(String[] args) {
		int titleType = (int) (Math.random()*9);
		
		String[] titles = new String[] {
			"Hello World!",
			"Hey Bob, How's that sandwich?",
			"You are looking great today!",
			"Call me my number is ###-###-####",
			"Explain this!",
			"Follow me on Facebook, I run a dog page",
			"$&%*(#&%*(#$)%&@*#($^&*@#($$&*#$(%@#$&^*((",
			"Gadzooks, what happened here!",
			"Explosively fun!",
			"Thanks"
		};
		
		Main main = new Main();
		JFrame frame = new JFrame("Eventful Explosion - " + titles[titleType]);
		
		Dimension dim = new Dimension(WIDTH, HEIGHT);
		frame.setMinimumSize(dim);
		frame.setMaximumSize(dim);
		frame.setPreferredSize(dim);
		frame.add(main);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		main.start();
		
	}
	
	public void start() {
		run();
	}
	
	public void run() {
		long now = System.currentTimeMillis();
		long last = now;
		double tps = 60.0;
		double fps = 60.0;
		double delta = 0;
		double renderDelta = 0;
		while (true) {
			now = System.currentTimeMillis();
			delta += ((now-last)/1000.0)*tps;
			renderDelta += ((now-last)/1000.0)*fps;
			if (delta >= 1.0) {
				delta = 0;
				update();
			}
			if (renderDelta >= 1.0) {
				renderDelta = 0;
				render();
			}
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		//Render START
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(background, 0, 0, null);
		
		//Render END
		
		g.dispose();
		bs.show();
	}
	
}
