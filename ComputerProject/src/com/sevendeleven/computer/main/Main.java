package com.sevendeleven.computer.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	
	private JFrame frame;
	
	public static volatile String text = "";
	
	public Color BACKGROUND = new Color(100,100,100,100);
	public Font defaultFont;
	
	public static final int WIDTH = 2560;
	public static final int HEIGHT = 1440;
	
	public static void main(String[] args) {
		(new Main()).start();
	}
	
	public void start() {
		defaultFont = new Font("Arial", Font.BOLD, 85);
		JFrame.setDefaultLookAndFeelDecorated(false);
		frame = new JFrame();
		frame.setContentPane(this);
		frame.setSize(new Dimension(WIDTH,HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH,HEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(255,255,255,0));
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.addKeyListener(new Keyboard());
		frame.setVisible(true);
		frame.requestFocus();
		run();
	}
	
	public void run() {
		long now = System.currentTimeMillis();
		long last = now;
		double delta = 1;
		double fps = 165;
		while (true) {
			now = System.currentTimeMillis();
			delta += (now-last)*fps/1000.0;
			if (delta >= 1) {
				delta = 0;
				update();
			}
			last = now;
		}
	}
	
	public void update() {
		frame.repaint();
	}
	
	public void paintComponent(Graphics g) {
		
		//RENDER START
		
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		g.setFont(defaultFont);
		int stringWidth = g.getFontMetrics().stringWidth(text);
		int stringHeight = g.getFontMetrics().getHeight();
		g.drawString(text, (WIDTH-stringWidth)/2, (HEIGHT-stringHeight)/2);
		//RENDER END
	}
	
	public void stop() {
		
	}
	
}
