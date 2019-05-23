package com.sevendeleven.canvasdrawing.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main extends Canvas {
	private static final long serialVersionUID = 1L;

	JFrame frame;
	
	BufferedImage img;
	Font font;
	
	int squareX = 0;
	int squareY = 0;
	
	public BufferedImage loadImage(String path) {
		try {
			BufferedImage ret = ImageIO.read(this.getClass().getResourceAsStream(path));
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
	
	public void start() {
		
		img = loadImage("/com/sevendeleven/canvasdrawing/main/th.jpg");
		font = new Font("Arial", Font.PLAIN, 12);
		
		frame = new JFrame("Test window");
		frame.setSize(new Dimension(800,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setVisible(true);
		
		loop();
	}
	
	public void loop() {
		long now = System.currentTimeMillis();
		long last = now;
		double delta = 0;
		double tps = 20.0;
		while (true) {
			now = System.currentTimeMillis();
			delta += (now-last)*tps/1000.0;
			if (delta >= 1.0) {
				delta = 0;
				update();
				draw();
			}
			last = now;
		}
	}
	
	public void draw() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		//RENDER START
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);
		
		g.drawImage(img, squareX, squareY, null);
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("Hello World!", 10, 10);
		
		//RENDER END
		
		g.dispose();
		bs.show();
	}
	
	public void update() {
		squareX += 1;
		squareY += 1;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		(new Main()).start();
	}
	
}
