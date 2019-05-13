package com.sevendeleven.testproject.event;

import java.awt.Canvas;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import com.sevendeleven.testproject.main.Main;

public class EventHandler {
	public static final boolean[] keys = new boolean[65535];
	
	private static final KeyHandler kl = new KeyHandler(keys);
	private static final MouseHandler ml = new MouseHandler();
	private static final MouseMotionHandler mml = new MouseMotionHandler();
	private static final MouseWheelHandler mwl = new MouseWheelHandler();
	private static final WindowHandler wl = new WindowHandler();
	
	private static class WindowHandler implements WindowListener, WindowStateListener, ComponentListener {
		
		
		
		@Override
		public void windowActivated(WindowEvent e) {
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			Main.getMain().getState().windowOpened();
		}

		@Override
		public void windowStateChanged(WindowEvent e) {
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			
		}

		@Override
		public void componentResized(ComponentEvent e) {
			Main.getMain().getState().windowResized();
		}

		@Override
		public void componentShown(ComponentEvent e) {
			Main.getMain().player.getInventory().updateButtonLocations();
		}
		
	}
	
	private static class KeyHandler extends KeyAdapter {
		
		private boolean[] keys;
		
		private KeyHandler(boolean[] keys) {
			this.keys = keys;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			boolean keyPressed = keys[e.getKeyCode()];
			keys[e.getKeyCode()] = true;
			if (!keyPressed && Main.getMain().hasGUIOn()) {
				Main.getMain().getGUI().keyDown(e);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
			if (Main.getMain().hasGUIOn())
				Main.getMain().getGUI().keyUp(e);
		}
		
		public void keyTyped(KeyEvent e) {
		}
		
	}
	
	
	
	private static class MouseWheelHandler implements MouseWheelListener {
		
		private MouseWheelHandler() {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			Mouse.scroll(e);
		}
		
	}
	
	private static class MouseMotionHandler extends MouseMotionAdapter {
		
		private MouseMotionHandler() {
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			Mouse.move(e);
			if (Main.getMain().hasGUIOn())
				Main.getMain().getGUI().mouseMove(e);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			Mouse.drag(e);
			if (Main.getMain().hasGUIOn())
				Main.getMain().getGUI().mouseDrag(e);
		}
		
	}
	
	public static void addEventHandlers(Canvas canvas, JFrame frame) {
		canvas.addMouseListener(ml);
		canvas.addKeyListener(kl);
		canvas.addMouseMotionListener(mml);
		canvas.addMouseWheelListener(mwl);
		frame.addWindowListener(wl);
		frame.addWindowStateListener(wl);
		frame.addComponentListener(wl);
	}
	
}
