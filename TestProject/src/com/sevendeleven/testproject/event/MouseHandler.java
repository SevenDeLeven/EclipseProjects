package com.sevendeleven.testproject.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
	
	public MouseHandler() {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.press(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.release(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Mouse.click(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}