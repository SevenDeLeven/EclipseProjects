package com.sevendeleven.testproject.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.sevendeleven.testproject.entities.Player;
import com.sevendeleven.testproject.event.EventHandler;
import com.sevendeleven.testproject.gamestate.MenuState;
import com.sevendeleven.testproject.gamestate.State;
import com.sevendeleven.testproject.gui.GUI;
import com.sevendeleven.testproject.renderer.Camera;
import com.sevendeleven.testproject.renderer.Renderer;
import com.sevendeleven.testproject.world.World;
import com.sevendeleven.testproject.world.block.Air;
import com.sevendeleven.testproject.world.block.BlockDestroyer;
public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "What is this game?!";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private static String font;
	
	Thread thread;
	
	public World world;
	
	public Player player;
	
	private static Main main;
	
	private GUI gui = null;
	
	private State state;
	
	private Main() {
//		world = new World();
//		player = new Player(world, 0, 100, GameController.nextEntityId());
//		world.addEntity(player);
		state = new MenuState();
	}
	
	public static void main(String[] args) {
		Loader.loadFiles();
		Register.registerBlock(Air.class);
		BlockDestroyer.initializeImages();

		font = "Arial";
		main = new Main();
		Dimension dim = new Dimension(WIDTH, HEIGHT);
		JFrame frame = new JFrame(NAME);
		frame.setSize(dim);
		frame.setMinimumSize(dim);
		frame.add(main);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		main.requestFocus(); 
		EventHandler.addEventHandlers(main, frame);
		try {
			main.start();
		} catch (ThreadCreatedException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		long now = System.nanoTime();
		long last = System.nanoTime();
		long unow = System.nanoTime();
		long ulast = System.nanoTime();
		double delta = (now-last)*20.0/1000000000.0;
		double renderDelta = (now-last)*60.0/1000000000.0;
		double udelta;
		while (running()) {
			last = now;
			now = System.nanoTime();
			delta += (now-last)*60.0/1000000000.0;
			renderDelta += (now-last)*60.0/1000000000.0;
			if (delta > 1.0) {
				ulast = unow;
				unow = System.nanoTime();
				delta = 0;
				udelta = (unow-ulast)*60.0/1000000000.0;
				update(udelta);
			}
			if (renderDelta > 1.0) {
				renderDelta = 0;
				render();
			}
		}
	}
	
	public static Main getMain() {
		return main;
	}
	
	public static String getDefaultFont() {
		return font;
	}
	
	public State getState() {
		return this.state;
	}
	
	private void update(double delta) {
		this.state.update((float) delta);
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.state.render(g2d);
		g.dispose();
		bs.show();
	}
	
	public boolean running() {
		return thread != null && thread.isAlive();
	}
	
	public synchronized void start() throws ThreadCreatedException{
		if (!running()) {
			thread = new Thread(this, "Main");
			thread.start();
		} else {
			throw new ThreadCreatedException("Main Thread Already Exists");
		}
	}
	
	public Camera getCamera() {
		return Renderer.getCamera();
	}
	
	public synchronized void stop() {
		if (running()) {
			try {
				thread.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public GUI getGUI() {
		return this.gui;
	}
	
	public void exitGUI() {
		this.gui = null;
	}
	
	public void setGUI(GUI gui) {
		this.gui = gui;
	}
	
	public boolean hasGUIOn() {
		return this.gui != null;
	}
	
	public static int getBlockSize() {
		return 16;
	}
	
}
