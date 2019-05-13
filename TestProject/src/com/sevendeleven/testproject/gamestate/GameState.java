package com.sevendeleven.testproject.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.main.Scheduler;
import com.sevendeleven.testproject.renderer.Renderer;
import com.sevendeleven.testproject.world.World;

public class GameState implements State {
	
	GameState(World world) {
		Main.getMain().world = world;
	}
	
	@Override
	public void update(float delta) {
		Main.getMain().world.update();
		Scheduler.getScheduler().enactEntities();
		Scheduler.getScheduler().enactDestruction();
	}

	@Override
	public void render(Graphics2D g) {
		Renderer.render(g, Main.getMain());
	}

	@Override
	public void mouseUp(MouseEvent e) {
		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		
	}

	@Override
	public void mouseMove(MouseEvent e) {
		
	}

	@Override
	public void keyDown(KeyEvent e) {
		
	}

	@Override
	public void keyUp(KeyEvent e) {
		
	}
	
	@Override
	public void windowOpened() {
		Main.getMain().player.getInventory().updateButtonLocations();
	}
	
	@Override
	public void windowResized() {
		Main.getMain().player.getInventory().updateButtonLocations();
	}
}
