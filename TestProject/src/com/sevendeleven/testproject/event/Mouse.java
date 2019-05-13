package com.sevendeleven.testproject.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.util.Vec2i;

public class Mouse {
	public static Vec2i position;
	public static boolean[] buttons;
	public static Vec2i pressPos;
	public static Vec2i releasePos;
	public static Vec2i dragPos;
	
	static {
		position = new Vec2i(0,0);
		releasePos = new Vec2i(0,0);
		pressPos = new Vec2i(0,0);
		dragPos = new Vec2i(0,0);
		buttons = new boolean[10]; 		//If the user has more than 10 mouse buttons, we have a problem to solve
	}
	
	static void press(MouseEvent e) {
		int b = e.getButton();
		if (b >= buttons.length || b < 0) {
			System.err.println("There was an error, the Mouse button " + b + " was out of bounds");
			return;
		}
		pressPos = new Vec2i(e.getX(), e.getY());
		buttons[b] = true;
		if (Main.getMain().getGUI() == null || (Main.getMain().getGUI() != null && !Main.getMain().getGUI().mouseDown(e))) {
			Main.getMain().player.beginDestroy();
		}
	}
	
	static void release(MouseEvent e) {
		int b = e.getButton();
		if (b >= buttons.length || b < 0) {
			return;
		}
		releasePos = new Vec2i(e.getX(), e.getY());
		buttons[b] = false;
		if (Main.getMain().getGUI() == null || (Main.getMain().getGUI() != null && !Main.getMain().getGUI().mouseUp(e))) {
			Main.getMain().player.endDestroy();
		}
	}
	
	static void move(MouseEvent e) {
		position = new Vec2i(e.getX(), e.getY());
	}
	
	static void drag(MouseEvent e) {
		Vec2i pos = new Vec2i(e.getX(), e.getY());
		position = pos.copy();
		dragPos = pos.copy();
	}
	
	static void scroll(MouseWheelEvent e) {
		Main.getMain().player.getInventory().scroll(e.getWheelRotation());
	}
	
	static void click(MouseEvent e) {
		
	}
	
}
