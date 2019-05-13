package com.sevendeleven.testproject.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.sevendeleven.testproject.main.Main;

public class MenuState implements State {

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getMain().getWidth(), Main.getMain().getHeight());
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
		
	}

	@Override
	public void windowResized() {
		
	}
	
	
	
}
