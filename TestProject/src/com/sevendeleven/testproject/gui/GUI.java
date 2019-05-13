package com.sevendeleven.testproject.gui;

import java.awt.event.*;
import java.awt.*;

public abstract class GUI {
	
	public abstract void draw(Graphics g);
	
	/** Returns true if the GUI should cancel any other events **/
	public abstract boolean mouseDown(MouseEvent e);
	/** Returns true if the GUI should cancel any other events **/
	public abstract boolean mouseUp(MouseEvent e);
	/** Returns true if the GUI should cancel any other events **/
	public abstract boolean keyDown(KeyEvent e);
	/** Returns true if the GUI should cancel any other events **/
	public abstract boolean keyUp(KeyEvent e);
	/** Returns true if the GUI should cancel any other events **/
	public abstract boolean mouseMove(MouseEvent e);
	/** Returns true if the GUI should cancel any other events **/
	public abstract boolean mouseDrag(MouseEvent e);
	/** Returns true if the GUI should cancel any other events **/
	public abstract boolean mouseClick(MouseEvent e);
	
	public abstract void onDisable();
	public abstract void onEnable();
	
}
