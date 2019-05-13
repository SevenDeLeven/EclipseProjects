package com.sevendeleven.curtain.main;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Main implements Runnable {
	
	private class KeyEvents extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
	}
	
	private class MouseEvents extends MouseAdapter {
		public boolean move = false;
		public boolean size = false;
		
		public int pressX = 0;
		public int pressY = 0;
		
		private Main instance;
		
		public MouseEvents(Main instance) {
			this.instance = instance;
		}
		
		@Override
		public void mousePressed(MouseEvent event) {
			if (!(move || size)) {
				pressX = event.getXOnScreen();
				pressY = event.getYOnScreen();
				if (event.getButton() == MouseEvent.BUTTON1) {
					move = true;
					instance.start();
				} else if (event.getButton() == MouseEvent.BUTTON3) {
					size = true;
					instance.start();
				}
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1) {
				move = false;
			} else if (event.getButton() == MouseEvent.BUTTON3) {
				size = false;
			}
		}
	}
	
	private KeyEvents keyEvents;
	private MouseEvents mouseEvents;
	private JFrame frame;
	
	public Main(JFrame frame) {
		this.frame = frame;
		this.keyEvents = new KeyEvents();
		this.mouseEvents = new MouseEvents(this);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Curtain");
		Main instance = new Main(frame);
		frame.setUndecorated(true);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(instance.getKeyAdapter());
		frame.addMouseListener(instance.getMouseAdapter());
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setBackground(Color.BLACK);
		
		frame.setVisible(true);
	}
	
	public KeyEvents getKeyAdapter() {
		return this.keyEvents;
	}
	
	public MouseEvents getMouseAdapter() {
		return this.mouseEvents;
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while (mouseEvents.move || mouseEvents.size) {
			PointerInfo info = MouseInfo.getPointerInfo();
			if (info != null) {
				Point m = info.getLocation();
				
				if (mouseEvents.move && (mouseEvents.pressX != m.x || mouseEvents.pressY != m.y)) {
					frame.setLocation(m.x, m.y);
				}
				if (mouseEvents.size && (mouseEvents.pressX != m.x || mouseEvents.pressY != m.y)) {
					int sizeX = m.x - frame.getLocation().x + 1;
					int sizeY = m.y - frame.getLocation().y + 1;
					sizeX = sizeX < 100 ? 100 : sizeX;
					sizeY = sizeY < 100 ? 100 : sizeY;
					
					frame.setSize(sizeX, sizeY);
				}
			}
		}
	}
	
}
