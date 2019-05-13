package com.sevendeleven.testproject.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface State {
	
	public void update(float delta);
	public void render(Graphics2D g);

	public void mouseUp(MouseEvent e);
	public void mouseDown(MouseEvent e);
	public void mouseMove(MouseEvent e);
	public void keyDown(KeyEvent e);
	public void keyUp(KeyEvent e);
	
	public void windowOpened();
	public void windowResized();
	
}
